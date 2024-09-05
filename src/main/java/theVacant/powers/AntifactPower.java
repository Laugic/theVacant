package theVacant.powers;

import basemod.BaseMod;
import basemod.ReflectionHacks;
import basemod.interfaces.CloneablePowerInterface;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.evacipated.cardcrawl.mod.stslib.powers.interfaces.BetterOnApplyPowerPower;
import com.evacipated.cardcrawl.mod.stslib.powers.interfaces.OnReceivePowerPower;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.actions.utility.TextAboveCreatureAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;
import com.megacrit.cardcrawl.vfx.cardManip.ExhaustCardEffect;
import com.megacrit.cardcrawl.vfx.combat.FlashPowerEffect;
import com.megacrit.cardcrawl.vfx.combat.GainPowerEffect;
import theVacant.VacantMod;
import theVacant.util.TextureLoader;

import java.util.ArrayList;

public class AntifactPower extends AbstractPower implements CloneablePowerInterface , OnReceivePowerPower//, BetterOnApplyPowerPower
{
    public AbstractCreature source;

    public static final String POWER_ID = VacantMod.makeID(AntifactPower.class.getSimpleName());
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    private static final Texture tex84 = TextureLoader.getTexture("theVacantResources/images/powers/antifact84.png");
    private static final Texture tex32 = TextureLoader.getTexture("theVacantResources/images/powers/antifact32.png");

    public AntifactPower(final AbstractCreature owner, final AbstractCreature source, final int amount)
    {
        name = NAME;
        ID = POWER_ID;

        this.owner = owner;
        this.amount = amount;
        this.source = source;

        type = PowerType.DEBUFF;
        isTurnBased = false;

        this.region128 = new TextureAtlas.AtlasRegion(tex84, 0, 0, 84, 84);
        this.region48 = new TextureAtlas.AtlasRegion(tex32, 0, 0, 32, 32);

        updateDescription();
    }

//    @Override
//    public void onApplyPower(AbstractPower power, AbstractCreature target, AbstractCreature source)
//    {
//        if(power.amount > 0 && target == owner && power.type == PowerType.BUFF)
//        {
//            flash();
//            power.amount = 0;
//            power.updateDescription();
//            addToTop(new RemoveSpecificPowerAction(owner, owner, power));
//            amount--;
//            if(amount <= 0)
//                addToTop(new RemoveSpecificPowerAction(owner, owner, this));
//        }
//    }
    @Override
    public boolean onReceivePower(AbstractPower power, AbstractCreature target, AbstractCreature source)
    {
        if(target == owner && power.type == PowerType.BUFF && !VacantMod.IMMUNE_POWERS.contains(power.ID))
        {
            //addToBot(new TextAboveCreatureAction(owner, ApplyPowerAction.TEXT[0]));
            CardCrawlGame.sound.play("NULLIFY_SFX");
            flashWithoutSound();
            if (amount == 1)
                addToTop(new RemoveSpecificPowerAction(owner, owner, this));
            else
                addToTop(new ReducePowerAction(owner, owner, this, 1));

            flashWithoutSound();
//            power.amount = 0;
//            power.updateDescription();
//            //ClearVisuals(power);
//            //addToTop(new RemoveSpecificPowerAction(owner, owner, power));
//            amount--;
//            if(amount <= 0)
//                addToTop(new RemoveSpecificPowerAction(owner, owner, this));
//            flash();
            return false;
        }
        return true;
    }


    @Override
    public int onReceivePowerStacks(AbstractPower power, AbstractCreature target, AbstractCreature source, int stackAmount)
    {
//        if(power.amount > 0 && target == owner && source != owner && power.type == PowerType.BUFF)
//        {
//            flash();
//            power.amount = 0;
//            power.updateDescription();
//            ClearVisuals(power);
//            //addToTop(new RemoveSpecificPowerAction(owner, owner, power));
//            amount--;
//            if(amount <= 0)
//                addToTop(new RemoveSpecificPowerAction(owner, owner, this));
//            flash();
//            return 0;
//        }
        return stackAmount;
    }

/*
    private void ClearVisuals(AbstractPower power)
    {
        AbstractGameEffect e = null;
        for (AbstractGameEffect effect: AbstractDungeon.effectList)
        {
            if(effect instanceof FlashPowerEffect)
            {
                Texture t = ReflectionHacks.getPrivate(effect, FlashPowerEffect.class, "img");
                if (t == power.img)
                    e = effect;
            }
        }
        AbstractDungeon.effectList.remove(e);

        e = null;
        ArrayList<AbstractGameEffect> effects = ReflectionHacks.getPrivate(power, AbstractPower.class, "effect");
        for (AbstractGameEffect effect: effects)
        {
            if(effect instanceof GainPowerEffect)
                    e = effect;
        }
        effects.remove(e);
    }

    @Override
    public boolean betterOnApplyPower(AbstractPower power, AbstractCreature target, AbstractCreature source)
    {
        if(power.amount > 0 && target == owner && power.type == PowerType.BUFF)
        {
            flash();
            power.amount = 0;
            power.updateDescription();
            ClearVisuals(power);
            //addToTop(new RemoveSpecificPowerAction(owner, owner, power));
            amount--;
            if(amount <= 0)
                addToTop(new RemoveSpecificPowerAction(owner, owner, this));
            flash();
            return false;
        }
        return true;
    }

    @Override
    public int betterOnApplyPowerStacks(AbstractPower power, AbstractCreature target, AbstractCreature source, int stackAmount)
    {
        if(power.amount > 0 && target == owner && power.type == PowerType.BUFF)
        {
            flash();
            power.amount = 0;
            power.updateDescription();
            ClearVisuals(power);
            //addToTop(new RemoveSpecificPowerAction(owner, owner, power));
            amount--;
            if(amount <= 0)
                addToTop(new RemoveSpecificPowerAction(owner, owner, this));
            flash();
            return 0;
        }
        return stackAmount;
    }*/

    @Override
    public void updateDescription()
    {
        description = DESCRIPTIONS[0] + amount + (amount == 1?DESCRIPTIONS[1]:DESCRIPTIONS[2]);
    }

    @Override
    public AbstractPower makeCopy()
    {
        return new AntifactPower(owner, source, amount);
    }
}
