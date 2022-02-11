package theVacant.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.orbs.AbstractOrb;
import theVacant.orbs.AbstractGemOrb;

public class ChipOrbAction extends AbstractGameAction
{
    AbstractGemOrb gem;
    boolean limited;

    public ChipOrbAction(AbstractOrb orb, int chips)
    {
        this(orb, chips, true);
    }

    public ChipOrbAction(int chips)
    {
        this(getFirstGem(), chips, false);
    }

    public ChipOrbAction(AbstractOrb orb, int chips, boolean limited)
    {
        if (orb instanceof AbstractGemOrb)
        {
            this.gem = (AbstractGemOrb) orb;
        }
        this.amount = chips;
        this.limited = limited;
        duration = startDuration = Settings.ACTION_DUR_FAST;
    }

    @Override
    public void update()
    {
        if (duration == startDuration)
        {
            if (gem == null || amount == 0)
            {
                this.isDone = true;
                return;
            }
            int hits = Math.min(amount, gem.passiveAmount);
            //The proposed solution
            gem.onChip(hits);
            amount -= hits;
            if (amount > 0 && hasAnotherGem() && !limited)
            {
                AbstractGemOrb nextGem = getNextGem();
                if(nextGem != null)
                    addToBot(new ChipOrbAction(nextGem, amount, false));
                isDone = true;
                return;
            }
        }
        tickDuration();
    }

    private AbstractGemOrb getNextGem()
    {
        for (AbstractOrb o : AbstractDungeon.player.orbs)
        {
            if (o instanceof AbstractGemOrb && o.passiveAmount > 0 && !o.equals(gem))
                return (AbstractGemOrb)o;
        }
        return null;
    }

    private static AbstractOrb getFirstGem()
    {
        for (AbstractOrb o : AbstractDungeon.player.orbs)
        {
            if (o instanceof AbstractGemOrb && o.passiveAmount > 0)
                return o;
        }
        return null;
    }

    private static boolean hasAnotherGem()
    {
        for (AbstractOrb o : AbstractDungeon.player.orbs)
        {
            if (o instanceof AbstractGemOrb && o.passiveAmount > 0)
                return true;
        }
        return false;
    }
}