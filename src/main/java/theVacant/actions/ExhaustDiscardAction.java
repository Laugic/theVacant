package theVacant.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class ExhaustDiscardAction  extends AbstractGameAction
{
    private float startingDuration;

    public ExhaustDiscardAction(int numCards)
    {
        this.amount = numCards;

        this.actionType = ActionType.CARD_MANIPULATION;
        this.startingDuration = Settings.ACTION_DUR_FAST;
        this.duration = this.startingDuration;
    }

    public void update()
    {
        AbstractPlayer player = AbstractDungeon.player;
        if(player != null)
        {
            while((this.amount > 0 || this.amount == -1) && player.discardPile.size() > 0)
            {
                AbstractCard card = player.discardPile.getTopCard();
                player.exhaustPile.addToTop(card);
                player.discardPile.removeCard(card);
                if(this.amount > 0)
                    this.amount--;
            }
            this.isDone = true;
        }
    }
}
