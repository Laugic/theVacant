package theVacant.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import theVacant.powers.DoomPower;
import theVacant.powers.RemoveDoomPower;

public class AddReduceDoomAction extends AbstractGameAction
{
    int previousExpected;
    public AddReduceDoomAction(int previousExpected, int expectedReduce)
    {
        this.previousExpected = previousExpected;
        amount  = expectedReduce;
    }

    public void update()
    {
        AbstractPlayer player = AbstractDungeon.player;
        if(!player.hasPower(DoomPower.POWER_ID))
        {
            isDone = true;
            return;
        }
        if(player.getPower(DoomPower.POWER_ID).amount <= previousExpected)
        {
            isDone = true;
            return;
        }
        int amountToApply = Math.min(player.getPower(DoomPower.POWER_ID).amount - previousExpected, amount);
        addToTop(new ApplyPowerAction(player, player, new RemoveDoomPower(player, player, amountToApply), amountToApply));
        isDone = true;
    }
}
