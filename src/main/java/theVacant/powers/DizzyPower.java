package theVacant.powers;

import basemod.helpers.CardModifierManager;
import basemod.interfaces.CloneablePowerInterface;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.PutOnDeckAction;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.watcher.VigorPower;
import theVacant.VacantMod;
import theVacant.cards.Modifiers.FaceDownMod;
import theVacant.util.TextureLoader;

public class DizzyPower extends AbstractPower implements CloneablePowerInterface
{
    public AbstractCreature source;

    public static final String POWER_ID = VacantMod.makeID(DizzyPower.class.getSimpleName());
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    private static final Texture tex84 = TextureLoader.getTexture("theVacantResources/images/powers/dizzy_power84.png");
    private static final Texture tex32 = TextureLoader.getTexture("theVacantResources/images/powers/dizzy_power32.png");

    int cardsDrawnThisTurn = 0;
    boolean thisTurn = true;
    public DizzyPower(final AbstractCreature owner, final AbstractCreature source, final int amount)
    {
        name = NAME;
        ID = POWER_ID;

        this.owner = owner;
        this.amount = amount;
        this.source = source;

        type = PowerType.DEBUFF;
        isTurnBased = true;
        cardsDrawnThisTurn = 0;
        thisTurn = true;
        this.region128 = new TextureAtlas.AtlasRegion(tex84, 0, 0, 84, 84);
        this.region48 = new TextureAtlas.AtlasRegion(tex32, 0, 0, 32, 32);

        updateDescription();
    }

    @Override
    public void atStartOfTurn(){
        cardsDrawnThisTurn = 0;
    }

    @Override
    public void onCardDraw(AbstractCard card) {
        super.onCardDraw(card);
        if(cardsDrawnThisTurn < 3 && !thisTurn)
        {
            cardsDrawnThisTurn++;
            CardModifierManager.addModifier(card, new FaceDownMod());
        }
    }

    @Override
    public void atStartOfTurnPostDraw()
    {
        if(amount > 0)
        {
            //flash();
            //addToBot(new PutOnDeckAction(AbstractDungeon.player, AbstractDungeon.player, 1, true));
            addToBot(new ReducePowerAction(owner, owner, this, 1));
            addToBot(new DrawCardAction(1));
        }
    }

    @Override
    public void atEndOfRound() {
        thisTurn = false;
    }

    @Override
    public void updateDescription()
    {
        description = DESCRIPTIONS[0];
    }

    @Override
    public AbstractPower makeCopy()
    {
        return new DizzyPower(owner, source, amount);
    }
}
