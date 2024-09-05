package theVacant.actions;

import basemod.BaseMod;
import basemod.helpers.CardModifierManager;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.EmptyDeckShuffleAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import theVacant.cards.Modifiers.FaceDownMod;

public class ScrambleAction  extends AbstractGameAction {
    public ScrambleAction(int chance) {
        amount = chance;
        this.actionType = ActionType.WAIT;
    }

    public void update() {
        if(AbstractDungeon.player.hand.size() >= BaseMod.MAX_HAND_SIZE)
        {
            isDone = true;
            return;
        }
        if(AbstractDungeon.player.drawPile.size() == 0) {
            if(AbstractDungeon.player.discardPile.size() > 0)
                addToTop(new ScrambleAction(amount));
            addToTop(new EmptyDeckShuffleAction());
            isDone = true;
            return;
        }
        if(AbstractDungeon.cardRandomRng.random(amount) == 0)
            CardModifierManager.addModifier(AbstractDungeon.player.drawPile.getTopCard(), new FaceDownMod());
        addToTop(new ScrambleAction(amount));
        addToTop(new DrawCardAction(1));

        this.isDone = true;
    }
}
