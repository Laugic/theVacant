package theVacant.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.orbs.AbstractOrb;
import theVacant.orbs.AbstractGemOrb;
import theVacant.powers.PickaxePower;

public class AddPickaxeToBottomAction extends AbstractGameAction
{
    public AddPickaxeToBottomAction()
    {
        actionType = ActionType.SPECIAL;
        duration = Settings.ACTION_DUR_FAST;
    }

    public void update()
    {
        AbstractPlayer player = AbstractDungeon.player;
        if(player != null && player.hasPower(PickaxePower.POWER_ID) && player.getPower(PickaxePower.POWER_ID).amount > 0)
            addToBot(new ChipOrbAction(player.getPower(PickaxePower.POWER_ID).amount));
        isDone = true;
    }
}
