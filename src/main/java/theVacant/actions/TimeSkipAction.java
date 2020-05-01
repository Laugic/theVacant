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

public class TimeSkipAction  extends AbstractGameAction
{
    private float startingDuration;

    public TimeSkipAction()
    {
        this.actionType = ActionType.CARD_MANIPULATION;
        this.startingDuration = Settings.ACTION_DUR_FAST;
        this.duration = this.startingDuration;
    }

    public void update()
    {
        AbstractPlayer player = AbstractDungeon.player;
        for (AbstractCard card:player.hand.group)
        {
            if(card.cost > 0)
                card.modifyCostForCombat(-card.cost);
            if(card.cost == -1)
                card.freeToPlayOnce = true;
            if(!card.exhaust)
            {
                card.exhaust = true;
                card.rawDescription += " NL Exhaust.";
                card.initializeDescription();
            }
        }
        this.isDone = true;
    }
}
