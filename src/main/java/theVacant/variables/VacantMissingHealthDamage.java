package theVacant.variables;

import basemod.abstracts.DynamicVariable;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.ui.panels.EnergyPanel;

import static theVacant.VacantMod.makeID;

public class VacantMissingHealthDamage extends DynamicVariable {

    @Override
    public String key() {
        return makeID("HP_GONE_DAMAGE");
    }

    @Override
    public boolean isModified(AbstractCard card) { return card.isDamageModified; }

    @Override
    public int value(AbstractCard card)
    {
        return card.damage + AbstractDungeon.player.maxHealth - AbstractDungeon.player.currentHealth;
    }

    @Override
    public int baseValue(AbstractCard card)
    {
        return card.baseDamage;
    }

    @Override
    public boolean upgraded(AbstractCard card)
    {
        return card.upgradedDamage;
    }
}