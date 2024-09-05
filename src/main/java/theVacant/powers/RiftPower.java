package theVacant.powers;

import basemod.interfaces.CloneablePowerInterface;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.evacipated.cardcrawl.mod.stslib.patches.NeutralPowertypePatch;
import com.evacipated.cardcrawl.mod.stslib.powers.interfaces.NonStackablePower;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import theVacant.VacantMod;
import theVacant.util.TextureLoader;

import java.util.ArrayList;

public class RiftPower extends AbstractPower implements CloneablePowerInterface, NonStackablePower
{
    public AbstractCreature source;

    public static final String POWER_ID = VacantMod.makeID(RiftPower.class.getSimpleName());
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    private static final Texture tex84 = TextureLoader.getTexture("theVacantResources/images/powers/rift_power84.png");
    private static final Texture tex32 = TextureLoader.getTexture("theVacantResources/images/powers/rift_power32.png");

    public ArrayList<AbstractPower> debuffs;

    public RiftPower(final AbstractCreature owner, final AbstractCreature source, final int amount)
    {
        name = NAME;
        ID = POWER_ID;

        this.owner = owner;
        this.amount = amount;
        this.source = source;

        type = NeutralPowertypePatch.NEUTRAL;
        isTurnBased = true;

        this.region128 = new TextureAtlas.AtlasRegion(tex84, 0, 0, 84, 84);
        this.region48 = new TextureAtlas.AtlasRegion(tex32, 0, 0, 32, 32);
        debuffs = new ArrayList<>();
        updateDescription();
    }

    @Override
    public void onInitialApplication() {
        for (AbstractPower p: owner.powers) {
            if(p.type == PowerType.DEBUFF && p instanceof CloneablePowerInterface)
            {
                addToBot(new RemoveSpecificPowerAction(owner, owner, p));
                debuffs.add(((CloneablePowerInterface) p).makeCopy());
            }
        }
        updateDescription();
        for (AbstractPower p: owner.powers)
        {
            if(p instanceof RiftPower && p != this){
                ((RiftPower)p).debuffs.addAll(debuffs);
                p.amount += amount;
                addToTop(new RemoveSpecificPowerAction(owner, owner, this));
                return;
            }
        }
        if(debuffs.size() == 0)
            addToTop(new RemoveSpecificPowerAction(owner, owner, this));
    }

    @Override
    public void atStartOfTurnPostDraw()
    {
        if(amount <= 1)
        {
            flash();
            returnDebuffs();
            AbstractDungeon.actionManager.addToBottom(new RemoveSpecificPowerAction(owner, owner, this));
        }
        else
            addToBot(new ReducePowerAction(owner, owner, this, 1));
    }

    private void returnDebuffs()
    {
        for (AbstractPower p: debuffs){
            AbstractPower newPower = ((CloneablePowerInterface) p).makeCopy();
            newPower.owner = owner;
            addToBot(new ApplyPowerAction(owner, owner, newPower));
        }
    }

    @Override
    public void updateDescription()
    {
        if(amount == 1)
            description = DESCRIPTIONS[0];
        else
            description = DESCRIPTIONS[1] + amount + DESCRIPTIONS[2];
        if(debuffs.size() == 0)
            return;
        if(debuffs.size() == 1)
            description += "#y" + debuffs.get(0).name + ".";
        else if (debuffs.size() == 2){
            description += "#y" + debuffs.get(0).name + DESCRIPTIONS[3];
            description += "#y" + debuffs.get(1).name + ".";
        }
        else {
            for (AbstractPower p: debuffs) {
                if(debuffs.get(debuffs.size() - 1) == p)
                    description += "#y" + p.name + ".";
                else
                    description += "#y" + p.name + ", ";
                if(debuffs.get(debuffs.size() - 2) == p)
                    description += DESCRIPTIONS[3];
            }
        }
        /*if(debuffs.size() >= 2)
            description += DESCRIPTIONS[4];*/
    }

    @Override
    public AbstractPower makeCopy()
    {
        return new RiftPower(owner, source, amount);
    }
}
