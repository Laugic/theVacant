package theVacant.powers;

import basemod.interfaces.CloneablePowerInterface;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
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

public class IronWillPower extends AbstractPower implements CloneablePowerInterface
{
    public AbstractCreature source;

    public static final String POWER_ID = VacantMod.makeID(IronWillPower.class.getSimpleName());
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    private static final Texture tex84 = TextureLoader.getTexture("theVacantResources/images/powers/placeholder_power84.png");
    private static final Texture tex32 = TextureLoader.getTexture("theVacantResources/images/powers/placeholder_power32.png");

    public static int ironWillIDOffset;

    public IronWillPower(final AbstractCreature owner, final AbstractCreature source)
    {
        name = NAME;
        ID = POWER_ID + ironWillIDOffset;
        ironWillIDOffset++;

        this.owner = owner;
        if(owner instanceof AbstractPlayer)
            this.amount = ((AbstractPlayer)this.owner).damagedThisCombat;
        this.source = source;

        type = PowerType.BUFF;
        isTurnBased = false;

        this.region128 = new TextureAtlas.AtlasRegion(tex84, 0, 0, 84, 84);
        this.region48 = new TextureAtlas.AtlasRegion(tex32, 0, 0, 32, 32);

        updateDescription();
    }

    @Override
    public void wasHPLost(DamageInfo info, int damageAmount)
    {
        super.wasHPLost(info, damageAmount);
        if(owner instanceof AbstractPlayer)
            updateAmount();
    }

    @Override
    public void onAfterUseCard(AbstractCard card, UseCardAction action)
    {
        super.onAfterUseCard(card, action);
        updateAmount();
    }

    @Override
    public void atStartOfTurn()
    {
        super.atStartOfTurn();
        updateAmount();
    }

    @Override
    public void atEndOfTurn(boolean isPlayer)
    {
        super.atEndOfTurn(isPlayer);
        updateAmount();
    }

    @Override
    public int onAttacked(DamageInfo info, int damageAmount)
    {
        if(owner instanceof AbstractPlayer)
            updateAmount();
        return super.onAttacked(info, damageAmount);
    }

    @Override
    public void atEndOfTurnPreEndTurnCards(boolean isPlayer)
    {
        updateAmount();
        if(this.amount > 0)
        {
            flash();
            addToBot(new GainBlockAction(this.owner, this.owner, this.amount));
        }
    }

    private void updateAmount()
    {
        this.amount = ((AbstractPlayer)this.owner).damagedThisCombat;
        this.updateDescription();
    }

    @Override
    public void updateDescription()
    {
        description = DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[1];
    }

    @Override
    public AbstractPower makeCopy()
    {
        return new IronWillPower(owner, source);
    }
}
