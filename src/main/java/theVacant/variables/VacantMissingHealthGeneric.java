package theVacant.variables;

import basemod.abstracts.DynamicVariable;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.ui.panels.EnergyPanel;

import static theVacant.VacantMod.makeID;

public class VacantMissingHealthGeneric extends DynamicVariable {

    @Override
    public String key() {
        return makeID("HP_GONE");
    }

    @Override
    public boolean isModified(AbstractCard card) { return card.isDamageModified; }

    @Override
    public int value(AbstractCard card)
    {
        return AbstractDungeon.player.maxHealth - AbstractDungeon.player.currentHealth;
    }

    @Override
    public int baseValue(AbstractCard card)
    {
        return 0;
    }

    @Override
    public boolean upgraded(AbstractCard card)
    {
        return card.upgraded;
    }
}