package theVacant.actions;

import basemod.BaseMod;
import basemod.patches.com.megacrit.cardcrawl.characters.AbstractPlayer.MaxHandSizePatch;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import static com.megacrit.cardcrawl.characters.AbstractPlayer.uiStrings;

public class DrawFromBottom extends AbstractGameAction
{

    public static final String[] TEXT = uiStrings.TEXT;

    private float startingDuration;
    public int amount;
    public boolean random;

    public DrawFromBottom(int amount)
    {
        this.actionType = ActionType.CARD_MANIPULATION;
        this.startingDuration = Settings.ACTION_DUR_FAST;
        this.duration = this.startingDuration;
        this.amount = amount;
    }

    public void update()
    {
        if(AbstractDungeon.player != null)
        {
            while(AbstractDungeon.player.hand.size() < BaseMod.MAX_HAND_SIZE && AbstractDungeon.player.drawPile.size() > 0 && this.amount > 0)
            {
                AbstractCard card = AbstractDungeon.player.drawPile.getBottomCard();
                card.unhover();
                card.lighten(true);
                card.setAngle(0.0F);
                card.drawScale = 0.12F;
                card.targetDrawScale = 0.75F;
                card.current_x = CardGroup.DRAW_PILE_X;
                card.current_y = CardGroup.DRAW_PILE_Y;
                AbstractDungeon.player.drawPile.removeCard(card);
                AbstractDungeon.player.hand.addToTop(card);
                AbstractDungeon.player.hand.refreshHandLayout();
                AbstractDungeon.player.hand.applyPowers();
                this.amount--;
            }
        }
        this.isDone = true;
    }
}
