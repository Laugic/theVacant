package theVacant.powers;

import basemod.interfaces.CloneablePowerInterface;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import theVacant.VacantMod;
import theVacant.util.TextureLoader;

public class GreaterMindUpgradedPower extends AbstractPower implements CloneablePowerInterface
{
    public AbstractCreature source;

    public static final String POWER_ID = VacantMod.makeID(GreaterMindUpgradedPower.class.getSimpleName());
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    private static final Texture tex84 = TextureLoader.getTexture("theVacantResources/images/powers/placeholder_power84.png");
    private static final Texture tex32 = TextureLoader.getTexture("theVacantResources/images/powers/placeholder_power32.png");

    public GreaterMindUpgradedPower(final AbstractCreature owner, final AbstractCreature source, final int amount)
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
    public void onUseCard(AbstractCard card, UseCardAction action)
    {
        if(card.type == AbstractCard.CardType.POWER && card.costForTurn > 0)
        {
            AbstractCard newPower = AbstractDungeon.returnTrulyRandomCardInCombat(AbstractCard.CardType.POWER).makeCopy();
            newPower.setCostForTurn(1);
            addToBot(new MakeTempCardInHandAction(newPower, true));
        }
    }

    @Override
    public void updateDescription()
    {
        description = DESCRIPTIONS[0];
    }
    @Override
    public AbstractPower makeCopy()
    {
        return new GreaterMindPower(owner, source, amount);
    }
}
