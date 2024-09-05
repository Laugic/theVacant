package theVacant.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.TalkAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import theVacant.cards.Skills.Memoria;

public class AddCopyOfLastExhaustedCardToHandAction extends AbstractGameAction {
    @Override
    public void update() {
        /*AbstractCard newCard = Memoria.getLastExhaustedCard();
        if(newCard != null)
        {
            newCard.unfadeOut();
            newCard.unhover();
            AbstractDungeon.player.exhaustPile.moveToHand(newCard);
            newCard.costForTurn = 0;
            newCard.applyPowers();
            AbstractDungeon.player.hand.refreshHandLayout();
        }
        else
            addToBot(new TalkAction(true, Memoria.cardStrings.EXTENDED_DESCRIPTION[0], 2, 2));*/
        isDone = true;
    }
}
