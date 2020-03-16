package theVacant.powers;

import basemod.interfaces.CloneablePowerInterface;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import theVacant.VacantMod;
import theVacant.util.TextureLoader;

public class EmptyCardDrawPower extends AbstractPower implements CloneablePowerInterface
{
    public AbstractCreature source;

    public static final String POWER_ID = VacantMod.makeID("EmptyCardDrawPower");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    private static final Texture tex84 = TextureLoader.getTexture("theVacantResources/images/powers/placeholder_power84.png");
    private static final Texture tex32 = TextureLoader.getTexture("theVacantResources/images/powers/placeholder_power32.png");

    public EmptyCardDrawPower(final AbstractCreature owner, final AbstractCreature source, final int amount)
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
    public void onCardDraw(AbstractCard card)
    {
        CheckToDraw();
    }

    @Override
    public void onPlayCard(AbstractCard card, AbstractMonster m)
    {
        CheckToDraw();
    }

    @Override
    public void onUseCard(AbstractCard card, UseCardAction action)
    {
        CheckToDraw();
    }

    @Override
    public void atStartOfTurnPostDraw()
    {
        CheckToDraw();
    }

    @Override
    public void onDrawOrDiscard()
    {
        CheckToDraw();
    }

    public void CheckToDraw()
    {
        AbstractPlayer player = AbstractDungeon.player;
        if(player != null)
        {
            if(player.drawPile.size() == 0 && player.discardPile.size() > 0)
            {
                player.draw(Math.min(this.amount, player.discardPile.size()));
            }
        }
    }

    @Override
    public void updateDescription()
    {
        if (this.amount == 1)
        {
            description = DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[1];
        }
        else if (this.amount > 1)
        {
            description = DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[2];
        }
    }

    @Override
    public AbstractPower makeCopy()
    {
        return new EmptyCardDrawPower(owner, source, amount);
    }
}
