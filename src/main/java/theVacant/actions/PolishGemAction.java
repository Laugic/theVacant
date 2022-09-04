package theVacant.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.orbs.AbstractOrb;
import theVacant.orbs.AbstractGemOrb;

public class PolishGemAction extends AbstractGameAction
{
    AbstractGemOrb gem;


    public PolishGemAction(AbstractGemOrb orb)
    {
        this(orb, 1);
    }

    public PolishGemAction(AbstractGemOrb orb, int polishes)
    {
        this.gem = orb;
        this.amount = polishes;
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
        gem.onPolish(amount);
        this.isDone = true;
    }
}