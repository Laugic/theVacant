package theVacant.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import theVacant.relics.BoundSoulOld;

public class UpdateFormAction extends AbstractGameAction
{
    private float startingDuration;
    private boolean turnStart;
    public UpdateFormAction(boolean isTurnStart)
    {
        turnStart = isTurnStart;
        actionType = ActionType.CARD_MANIPULATION;
        startingDuration = Settings.ACTION_DUR_FAST;
        duration = startingDuration;
    }

    public void update()
    {
        AbstractPlayer player = AbstractDungeon.player;
        if(player == null)
        {
            isDone = true;
            return;
        }
        if(player.drawPile.size() == 0)
        {
            addToBot(new SwitchFormAction(BoundSoulOld.VACANT_FORM));
            isDone = true;
            return;
        }
        if(turnStart && !player.isBloodied)
        {
            addToBot(new SwitchFormAction(BoundSoulOld.SOLEMN_FORM));
            isDone = true;
            return;
        }
        isDone = true;
    }
}
