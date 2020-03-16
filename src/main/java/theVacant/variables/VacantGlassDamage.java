package theVacant.variables;

import basemod.abstracts.DynamicVariable;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.ui.panels.EnergyPanel;

import static theVacant.VacantMod.makeID;

public class VacantGlassDamage extends DynamicVariable {

    @Override
    public String key() {
        return makeID("GLASS_DAMAGE");
    }

    @Override
    public boolean isModified(AbstractCard card) { return card.isDamageModified; }

    @Override
    public int value(AbstractCard card)
    {
        AbstractPlayer player = AbstractDungeon.player;
        if(player.currentHealth > player.maxHealth / 2)
            return card.damage + player.currentHealth;
        return card.damage + player.maxHealth - player.currentHealth;
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