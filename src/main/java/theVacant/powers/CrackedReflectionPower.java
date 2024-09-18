package theVacant.powers;

import basemod.interfaces.CloneablePowerInterface;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.evacipated.cardcrawl.mod.stslib.powers.interfaces.OnReceivePowerPower;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.orbs.AbstractOrb;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.watcher.VigorPower;
import theVacant.VacantMod;
import theVacant.actions.MineGemAction;
import theVacant.orbs.*;
import theVacant.util.TextureLoader;

import java.util.ArrayList;

import static theVacant.cards.AbstractVacantCard.getHollow;

public class CrackedReflectionPower extends AbstractPower implements CloneablePowerInterface, OnReceivePowerPower
{
    public AbstractCreature source;

    public static final String POWER_ID = VacantMod.makeID(CrackedReflectionPower.class.getSimpleName());
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    private static final Texture tex84 = TextureLoader.getTexture("theVacantResources/images/powers/reflection84.png");
    private static final Texture tex32 = TextureLoader.getTexture("theVacantResources/images/powers/reflection32.png");

    public CrackedReflectionPower(final AbstractCreature owner, final AbstractCreature source, final int amount)
    {
        name = NAME;
        ID = POWER_ID;

        this.owner = owner;
        this.amount = amount;
        this.source = source;

        type = PowerType.BUFF;
        isTurnBased = false;

        this.region128 = new TextureAtlas.AtlasRegion(tex84, 0, 0, 84, 84);
        this.region48 = new TextureAtlas.AtlasRegion(tex32, 0, 0, 32, 32);

        updateDescription();
    }

    @Override
    public void atEndOfTurn(boolean isPlayer) {
        for (int i = 0; i < amount; i++){
            AbstractGemOrb gem = MineRandomGem();
            if(gem != null)
                addToBot(new MineGemAction(gem));
        }
        if(AbstractDungeon.player.orbs.size() > 0)
            flash();
    }

    @Override
    public boolean onReceivePower(AbstractPower power, AbstractCreature target, AbstractCreature source) {
        if (power.type == AbstractPower.PowerType.DEBUFF && !power.ID.equals("Shackled") && target == owner && !target.hasPower("Artifact")) {
            for (int i = 0; i < amount; i++){
                AbstractGemOrb gem = MineRandomGem();
                if(gem != null)
                    addToTop(new MineGemAction(gem));
            }
            if(AbstractDungeon.player.orbs.size() > 0)
                flash();
        }
        return true;
    }

    private AbstractGemOrb MineRandomGem() {
        if(AbstractDungeon.player.orbs.size() == 0)
            return null;


        ArrayList<AbstractGemOrb> gems = new ArrayList<>();
        for (AbstractOrb orb: AbstractDungeon.player.orbs) {
            if(orb instanceof AbstractGemOrb)
                gems.add((AbstractGemOrb) orb);
        }
        int i = AbstractDungeon.cardRandomRng.random(AbstractDungeon.player.orbs.size() - 1);
        if(gems.get(i) instanceof RubyOrb)
            return new RubyOrb(1);
        if(gems.get(i) instanceof SapphireOrb)
            return new SapphireOrb(1);
        if(gems.get(i) instanceof OpalOrb)
            return new OpalOrb(1);
        if(gems.get(i) instanceof EmeraldOrb)
            return new EmeraldOrb(1);
        if(gems.get(i) instanceof OnyxOrb)
            return new OnyxOrb(1);
        if(gems.get(i) instanceof AmethystOrb)
            return new AmethystOrb(1);
        if(gems.get(i) instanceof TopazOrb)
            return new TopazOrb(1);
        if(gems.get(i) instanceof DiamondOrb)
            return new DiamondOrb(1);
        return null;
        /*int rand = AbstractDungeon.cardRandomRng.random(73);
        if(rand < 10)
                return new RubyOrb(1);
        if(rand < 20)
            return new SapphireOrb(1);
        if(rand < 30)
            return new OpalOrb(1);
        if(rand < 40)
            return new EmeraldOrb(1);
        if(rand < 50)
            return new OnyxOrb(1);
        if(rand < 60)
            return new AmethystOrb(1);
        if(rand < 70)
            return new TopazOrb(1);
        return new DiamondOrb(1);*/
    }

    @Override
    public void updateDescription()
    {
        if(amount == 1)
            description = DESCRIPTIONS[0];
        else
            description = DESCRIPTIONS[1] + amount + DESCRIPTIONS[2];
    }

    @Override
    public AbstractPower makeCopy()
    {
        return new CrackedReflectionPower(owner, source, amount);
    }
}
