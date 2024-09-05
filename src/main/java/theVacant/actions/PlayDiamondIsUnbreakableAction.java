package theVacant.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.ExhaustAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.ui.panels.EnergyPanel;
import theVacant.orbs.DiamondOrb;
import theVacant.powers.DelayedExhumePower;

public class PlayDiamondIsUnbreakableAction extends AbstractGameAction
{
    private boolean freeToPlayOnce = false;

    //private boolean upgraded = false;
    private int energyOnUse = -1;
    private int bonusEnergy = 0;

    public PlayDiamondIsUnbreakableAction(boolean freeToPlayOnce, int energyOnUse, int bonusEnergy)
    {
        this.freeToPlayOnce = freeToPlayOnce;
        this.bonusEnergy = bonusEnergy;
        this.duration = Settings.ACTION_DUR_XFAST;
        this.actionType = AbstractGameAction.ActionType.SPECIAL;
        this.energyOnUse = energyOnUse;
        //this.upgraded = upgraded;
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
            //for(int i = 0; i < energyUsed && i < 11; i++)
                addToBot(new MineGemAction(new DiamondOrb(energyUsed)));
            if (!this.freeToPlayOnce)
                player.energy.use(EnergyPanel.totalCount);
        }
        this.isDone = true;
    }
}
