package theVacant.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class ShuffleDiscardToBottomOfDrawAction extends AbstractGameAction
{

    public ShuffleDiscardToBottomOfDrawAction()
    {
        this.duration = Settings.ACTION_DUR_FAST;
        this.actionType = ActionType.CARD_MANIPULATION;
    }

    public void update()
    {
        AbstractPlayer player = AbstractDungeon.player;
        if(player.discardPile.size() > 0)
        {
            player.discardPile.shuffle();
            int num = player.discardPile.size();
            for(int i = 0; i < num; i++)
                player.discardPile.moveToBottomOfDeck(player.discardPile.getTopCard());
        }
        this.isDone = true;
    }
}
