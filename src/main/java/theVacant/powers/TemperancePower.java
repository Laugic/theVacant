package theVacant.powers;

import basemod.interfaces.CloneablePowerInterface;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageRandomEnemyAction;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import theVacant.VacantMod;
import theVacant.actions.VacantMillAction;
import theVacant.util.TextureLoader;

public class TemperancePower extends AbstractPower implements CloneablePowerInterface
{
    public AbstractCreature source;

    public static final String POWER_ID = VacantMod.makeID(TemperancePower.class.getSimpleName());
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    private static final Texture tex84 = TextureLoader.getTexture("theVacantResources/images/powers/temperance_power84.png");
    private static final Texture tex32 = TextureLoader.getTexture("theVacantResources/images/powers/temperance_power32.png");

    public TemperancePower(final AbstractCreature owner, final AbstractCreature source, final int amount)
    {
        name = NAME;
        ID = POWER_ID;

        this.owner = owner;
        this.amount = amount;
        this.source = source;

        type = PowerType.BUFF;
        if(this.amount < 0)
            type = PowerType.DEBUFF;
        isTurnBased = true;

        this.region128 = new TextureAtlas.AtlasRegion(tex84, 0, 0, 84, 84);
        this.region48 = new TextureAtlas.AtlasRegion(tex32, 0, 0, 32, 32);

        this.canGoNegative = true;
        updateDescription();
    }

    @Override
    public void onUseCard(AbstractCard card, UseCardAction action)
    {
        if (card.baseBlock >= 0)
        {
            flash();
            addToTop(new ReducePowerAction(owner, owner, ID, amount));
        }
    }

    @Override
    public float modifyBlock(float blockAmount)
    {
        if(blockAmount < 1)
            return blockAmount;
        return Math.max(blockAmount + amount, 0);
    }

    @Override
    public void stackPower(int stackAmount)
    {
        fontScale = 8.0F;
        amount += stackAmount;
        if (amount == 0)
            addToTop(new RemoveSpecificPowerAction(owner, owner, ID));
        if (amount >= 999)
            amount = 999;
        if (amount <= -999)
            amount = -999;
        if(amount < 0)
            type = PowerType.DEBUFF;
        else
            type = PowerType.BUFF;
    }

    @Override
    public void reducePower(int reduceAmount)
    {
        fontScale = 8.0F;
        amount -= reduceAmount;
        if (amount == 0)
            addToTop(new RemoveSpecificPowerAction(owner, owner, ID));
        if (amount >= 999)
            amount = 999;
        if (amount <= -999)
            amount = -999;
        if(amount < 0)
            type = PowerType.DEBUFF;
        else
            type = PowerType.BUFF;
    }

    @Override
    public void updateDescription()
    {
        if(amount >=0)
        {
            description = DESCRIPTIONS[0];
            type = PowerType.BUFF;
        }
        else
        {
            description = DESCRIPTIONS[1];
            type = PowerType.DEBUFF;
        }
    }

    @Override
    public AbstractPower makeCopy()
    {
        return new TemperancePower(owner, source, amount);
    }
}
