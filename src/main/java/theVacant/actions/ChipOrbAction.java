package theVacant.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.orbs.AbstractOrb;
import theVacant.orbs.AbstractGemOrb;
import theVacant.powers.ReflectionPower;

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
        source = AbstractDungeon.player;
    }

    @Override
    public void update()
    {
        if (gem == null || amount == 0)
        {
            this.isDone = true;
            return;
        }
        int hits = Math.min(amount, gem.passiveAmount);
/*        if(source.hasPower(ReflectionPower.POWER_ID))
            hits *= (1 + source.getPower(ReflectionPower.POWER_ID).amount);*/
        gem.onChip(hits);
        amount -= hits;
        if (amount > 0 && hasAnotherGem() && !limited)
        {
            AbstractGemOrb nextGem = getNextGem();
            if(nextGem != null)
                addToBot(new ChipOrbAction(nextGem, amount, false));
        }
        this.isDone = true;
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