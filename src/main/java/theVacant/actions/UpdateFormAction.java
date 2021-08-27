package theVacant.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.vfx.combat.InflameEffect;
import theVacant.cards.AbstractDynamicCard;
import theVacant.characters.TheVacant;
import theVacant.powers.*;
import theVacant.relics.BoundSoul;

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
            addToBot(new SwitchFormAction(BoundSoul.VACANT_FORM));
            isDone = true;
            return;
        }
        if(turnStart && !player.isBloodied)
        {
            addToBot(new SwitchFormAction(BoundSoul.SOLEMN_FORM));
            isDone = true;
            return;
        }
        isDone = true;
    }
}
