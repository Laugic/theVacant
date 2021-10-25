package theVacant.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.defect.DecreaseMaxOrbAction;
import com.megacrit.cardcrawl.actions.defect.RemoveNextOrbAction;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.orbs.AbstractOrb;
import theVacant.orbs.AbstractGemOrb;

public class ReduceOrbSizeAction extends AbstractGameAction
{
    private AbstractOrb orb;
    public ReduceOrbSizeAction(AbstractOrb orbToReduce)
    {
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
        gem.ReduceSize();
        gem.applyFocus();
        if(orb.passiveAmount == 0 || orb.evokeAmount == 0)
        {
            gem.onRemove();
            AbstractDungeon.player.removeNextOrb();
            AbstractDungeon.actionManager.addToBottom(new DecreaseMaxOrbAction(1));
        }
        isDone = true;
    }
}
