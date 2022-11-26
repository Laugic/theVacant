package theVacant.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.watcher.ChooseOneAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import theVacant.cards.AbstractDynamicCard;
import theVacant.cards.Special.*;
import theVacant.relics.BrassGoblet;
import theVacant.relics.OverflowingGobletRelic;
import theVacant.relics.TombstoneRelic;

import java.util.ArrayList;

public class DelayedTombstoneAction extends AbstractGameAction
{
    public DelayedTombstoneAction(int millNum)
    {
        amount = millNum;
    }

    public void update()
    {
        addToBot(new VacantMillAction(amount));

        if(AbstractDungeon.player.hasRelic(BrassGoblet.ID))
            AbstractDungeon.player.getRelic(BrassGoblet.ID).atBattleStart();

        if(AbstractDungeon.player.hasRelic(OverflowingGobletRelic.ID))
            AbstractDungeon.player.getRelic(OverflowingGobletRelic.ID).atBattleStart();

        if(AbstractDungeon.player.hasRelic(TombstoneRelic.ID)){
            AbstractDungeon.player.getRelic(TombstoneRelic.ID).flash();
            AbstractDungeon.player.getRelic(TombstoneRelic.ID).grayscale = true;
        }

        isDone = true;
    }
}
