package theVacant.powers;

import basemod.interfaces.CloneablePowerInterface;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.evacipated.cardcrawl.mod.stslib.patches.NeutralPowertypePatch;
import com.evacipated.cardcrawl.mod.stslib.powers.interfaces.BetterOnApplyPowerPower;
import com.evacipated.cardcrawl.mod.stslib.powers.interfaces.InvisiblePower;
import com.evacipated.cardcrawl.mod.stslib.powers.interfaces.OnReceivePowerPower;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.LoseHPAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.ArtifactPower;
import theVacant.VacantMod;
import theVacant.util.TextureLoader;

public class RequiemBuddyPower extends AbstractPower implements CloneablePowerInterface, OnReceivePowerPower, InvisiblePower
{
    public AbstractCreature source;

    public static final String POWER_ID = VacantMod.makeID(RequiemBuddyPower.class.getSimpleName());
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    private static final Texture tex84 = TextureLoader.getTexture("theVacantResources/images/powers/arrow84.png");
    private static final Texture tex32 = TextureLoader.getTexture("theVacantResources/images/powers/arrow32.png");

    public RequiemBuddyPower(final AbstractCreature owner, final AbstractCreature source, final int amount)
    {
        name = NAME;
        ID = POWER_ID;

        this.owner = owner;
        this.amount = amount;
        this.source = source;

        type = NeutralPowertypePatch.NEUTRAL;
        isTurnBased = false;

        this.region128 = new TextureAtlas.AtlasRegion(tex84, 0, 0, 84, 84);
        this.region48 = new TextureAtlas.AtlasRegion(tex32, 0, 0, 32, 32);

        updateDescription();
    }

    @Override
    public int onLoseHp(int damageAmount) {
        for (AbstractMonster monster: AbstractDungeon.getMonsters().monsters) {
            if(monster.hasPower(RequiemPower.POWER_ID)){
                monster.getPower(RequiemPower.POWER_ID).flash();
                addToTop(new LoseHPAction(monster, monster, damageAmount * monster.getPower(RequiemPower.POWER_ID).amount));
            }
        }
        return super.onLoseHp(damageAmount);
    }

    @Override
    public boolean onReceivePower(AbstractPower abstractPower, AbstractCreature abstractCreature, AbstractCreature abstractCreature1)
    {
        return true;
    }

    @Override
    public int onReceivePowerStacks(AbstractPower power, AbstractCreature target, AbstractCreature source, int stackAmount)
    {
        if(target == owner && power.type == PowerType.DEBUFF && !owner.hasPower(ArtifactPower.POWER_ID) && !VacantMod.IMMUNE_POWERS.contains(power.ID))
        {
            for (AbstractMonster monster: AbstractDungeon.getMonsters().monsters) {
                if(monster.hasPower(RequiemPower.POWER_ID)){
                    monster.getPower(RequiemPower.POWER_ID).flash();
                    if(power instanceof CloneablePowerInterface){
                        AbstractPower newPower = ((CloneablePowerInterface) power).makeCopy();
                        newPower.amount *= monster.getPower(RequiemPower.POWER_ID).amount;
                        newPower.owner = monster;
                        addToTop(new ApplyPowerAction(monster, monster, newPower));
                    }
                }
            }
        }
        return stackAmount;
    }

    @Override
    public void updateDescription()
    {
        description = DESCRIPTIONS[0] + amount + DESCRIPTIONS[1];
    }

    @Override
    public AbstractPower makeCopy()
    {
        return new RequiemBuddyPower(owner, source, amount);
    }

}
