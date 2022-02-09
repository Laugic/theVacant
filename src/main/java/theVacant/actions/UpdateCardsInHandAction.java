package theVacant.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import theVacant.cards.AbstractDynamicCard;

public class UpdateCardsInHandAction extends AbstractGameAction
{
    public UpdateCardsInHandAction()
    {

    }

    @Override
    public void update()
    {
        AbstractPlayer player = AbstractDungeon.player;
        if(player == null || player.hand.size() < 1)
        {
            isDone = true;
            return;
        }
        player.hand.applyPowers();
        isDone = true;
    }
}
