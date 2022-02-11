package theVacant.cards.Modifiers;

import basemod.abstracts.AbstractCardModifier;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.watcher.VigorPower;
import theVacant.powers.TemperancePower;

import java.util.ArrayList;

import static basemod.helpers.CardModifierManager.addModifier;
import static basemod.helpers.CardModifierManager.getModifiers;

public class SapphireModifier extends AbstractCardModifier
{
    private int amount;
    public static String ID = "VacantSapphireModifier";

    public SapphireModifier(int amount)
    {
        this.amount = amount;
    }

    public void Increase(int num)
    {
        amount += num;
    }

    @Override
    public void onUse(AbstractCard card, AbstractCreature target, UseCardAction action)
    {
        AbstractPlayer player = AbstractDungeon.player;
        if (this.amount > 0 && player != null)
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(player, player, new TemperancePower(player, player, amount), amount));
    }

    @Override
    public String modifyDescription(String rawDescription, AbstractCard card)
    {
        if(this.amount > 0)
            rawDescription += " NL Gain " + amount + " thevacant:Temperance.";
        return rawDescription;
    }

    public static void Enhance(AbstractCard card, int amount)
    {
        ArrayList<AbstractCardModifier> Sapphire = getModifiers(card, ID);
        if(Sapphire.size() > 0 && Sapphire.get(0) instanceof SapphireModifier)
        {
            SapphireModifier SapphireModifier = (SapphireModifier)Sapphire.get(0);
            SapphireModifier.Increase(amount);
        }
        else
            addModifier(card, new SapphireModifier(amount));
    }

    @Override
    public String identifier(AbstractCard card)
    {
        return ID;
    }

    @Override
    public AbstractCardModifier makeCopy()
    {
        return new SapphireModifier(amount);
    }
}