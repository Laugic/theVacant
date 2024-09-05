package theVacant.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import theVacant.powers.DoomPower;
import theVacant.powers.RemoveDoomPower;

public class AddReduceDoomAction extends AbstractGameAction
{
    int previousExpected;

    public AddReduceDoomAction(AbstractCreature target, int previousExpected, int expectedReduce)
    {
        this.previousExpected = previousExpected;
        amount  = expectedReduce;
        this.target = target;
    }

    public void update()
    {
        if(!target.hasPower(DoomPower.POWER_ID))
        {
            isDone = true;
            return;
        }
        if(target.getPower(DoomPower.POWER_ID).amount <= previousExpected)
        {
            isDone = true;
            return;
        }
        int amountToApply = Math.min(target.getPower(DoomPower.POWER_ID).amount - previousExpected, amount);
        addToTop(new ApplyPowerAction(target, AbstractDungeon.player, new RemoveDoomPower(target, AbstractDungeon.player, amountToApply), amountToApply));
        isDone = true;
    }
}
