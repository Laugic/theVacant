package theVacant.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.core.Settings;
import theVacant.orbs.AbstractGemOrb;

public class IncreaseOrbSizeAction extends AbstractGameAction
{
    private AbstractGemOrb gem;
    public IncreaseOrbSizeAction(AbstractGemOrb orbToIncrease)
    {
        amount = 1;
        actionType = ActionType.SPECIAL;
        duration = Settings.ACTION_DUR_FAST;
        gem = orbToIncrease;
    }

    public IncreaseOrbSizeAction(AbstractGemOrb orbToIncrease, int amount)
    {
        this.amount = amount;
        actionType = ActionType.SPECIAL;
        duration = Settings.ACTION_DUR_FAST;
        gem = orbToIncrease;
    }

    public void update()
    {
        gem.applyFocus();
        gem.increaseSize(amount);
        isDone = true;
    }
}
