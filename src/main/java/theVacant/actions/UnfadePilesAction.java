package theVacant.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class UnfadePilesAction extends AbstractGameAction
{
    public UnfadePilesAction()
    {
        this.actionType = ActionType.SPECIAL;
        this.duration = this.startDuration = Settings.ACTION_DUR_LONG;
    }

    @Override
    public void update()
    {
        for (AbstractCard card : AbstractDungeon.player.exhaustPile.group)
        {
            card.unhover();
            card.unfadeOut();
            card.lighten(true);
        }
        for (AbstractCard card : AbstractDungeon.player.drawPile.group)
        {
            card.unhover();
            card.unfadeOut();
            card.lighten(true);
        }
        for (AbstractCard card : AbstractDungeon.player.hand.group)
        {
            card.unhover();
            card.unfadeOut();
            card.lighten(true);
        }
        isDone = true;
    }
}
