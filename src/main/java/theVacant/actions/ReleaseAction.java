package theVacant.actions;

import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.vfx.BorderLongFlashEffect;
import com.megacrit.cardcrawl.vfx.combat.InflameEffect;
import theVacant.cards.AbstractDynamicCard;
import theVacant.characters.TheVacant;
import theVacant.powers.*;

public class ReleaseAction  extends AbstractGameAction
{
    private float startingDuration;

    public ReleaseAction()
    {
        this.actionType = ActionType.CARD_MANIPULATION;
        this.startingDuration = Settings.ACTION_DUR_FAST;
        this.duration = this.startingDuration;
    }

    public void update()
    {
        AbstractPlayer player = AbstractDungeon.player;
        if(player != null && player.powers.size() > 0)
        {
            for(AbstractPower power: player.powers)
            {
                if(power instanceof EtchPower)
                {
                    ((EtchPower)power).Release();
                    addToBot(new RemoveSpecificPowerAction(player, player, power));
                }
            }
        }
        if(player.hasPower(VoidEmbracePower.POWER_ID))
            AbstractDungeon.actionManager.addToTop(new ApplyPowerAction(player, player, new VoidPower(player, player, player.getPower(VoidEmbracePower.POWER_ID).amount), player.getPower(VoidEmbracePower.POWER_ID).amount));
        this.isDone = true;
    }
}
