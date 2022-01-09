package theVacant.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.orbs.AbstractOrb;
import theVacant.orbs.AbstractGemOrb;

public class ReduceOrbSizeAction extends AbstractGameAction
{
    private AbstractOrb orb;
    public ReduceOrbSizeAction(AbstractOrb orbToReduce)
    {
        amount = 1;
        actionType = ActionType.SPECIAL;
        duration = Settings.ACTION_DUR_FAST;
        orb = orbToReduce;
    }

    public ReduceOrbSizeAction(AbstractOrb orbToReduce, int amount)
    {
        this.amount = amount;
        actionType = ActionType.SPECIAL;
        duration = Settings.ACTION_DUR_FAST;
        orb = orbToReduce;
    }

    public void update()
    {
        if(!(orb instanceof AbstractGemOrb))
        {
            isDone = true;
            return;
        }
        AbstractGemOrb gem = (AbstractGemOrb)orb;
        gem.applyFocus();
        gem.ReduceSize(amount);
        isDone = true;
    }
}
