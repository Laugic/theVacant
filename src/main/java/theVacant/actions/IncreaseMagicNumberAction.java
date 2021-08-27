package theVacant.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class IncreaseMagicNumberAction extends AbstractGameAction
{
    private AbstractCard card;

    public IncreaseMagicNumberAction(AbstractCard card, int amount)
    {
        this.card = card;
        this.amount = amount;
    }

    public void update()
    {
        for (AbstractCard c : AbstractDungeon.player.discardPile.group)
            CheckApply(c);
        for (AbstractCard c : AbstractDungeon.player.drawPile.group)
            CheckApply(c);
        for (AbstractCard c : AbstractDungeon.player.hand.group)
            CheckApply(c);
        for (AbstractCard c : AbstractDungeon.player.exhaustPile.group)
            CheckApply(c);
        this.isDone = true;
    }

    private void CheckApply(AbstractCard c)
    {
        if (c.cardID == this.card.cardID)
        {
            c.baseMagicNumber += amount;
            c.magicNumber = c.baseMagicNumber;
            c.isMagicNumberModified = true;
            c.applyPowers();
            c.initializeDescription();
        }
    }
}
