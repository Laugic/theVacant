package theVacant.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.ExhaustAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.ui.panels.EnergyPanel;
import theVacant.powers.DelayedExhumePower;

public class PlayExhumationAction extends AbstractGameAction
{
    private boolean freeToPlayOnce = false;

    private boolean upgraded = false;
    private int energyOnUse = -1;
    private int bonusEnergy = 0;

    public PlayExhumationAction(boolean freeToPlayOnce, int energyOnUse, int bonusEnergy)
    {
        this.freeToPlayOnce = freeToPlayOnce;
        this.bonusEnergy = bonusEnergy;
        this.duration = Settings.ACTION_DUR_XFAST;
        this.actionType = AbstractGameAction.ActionType.SPECIAL;
        this.energyOnUse = energyOnUse;
    }

    public void update()
    {
        AbstractPlayer player = AbstractDungeon.player;
        int energyUsed = EnergyPanel.totalCount;
        if (this.energyOnUse != -1)
            energyUsed = this.energyOnUse;
        if (player.hasRelic("Chemical X"))
        {
            energyUsed += 2;
            player.getRelic("Chemical X").flash();
        }
        energyUsed += this.bonusEnergy;
        if (energyUsed > 0)
        {
            AbstractDungeon.actionManager.addToBottom(new ExhaustAction(energyUsed, false, false, true));
            if (!this.freeToPlayOnce)
                player.energy.use(EnergyPanel.totalCount);
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(player, player, new DelayedExhumePower(player, player, energyUsed)));
        }
        this.isDone = true;
    }
}
