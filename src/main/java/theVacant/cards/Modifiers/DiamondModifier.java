package theVacant.cards.Modifiers;

import basemod.abstracts.AbstractCardModifier;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.BufferPower;

import java.util.ArrayList;

import static basemod.helpers.CardModifierManager.addModifier;
import static basemod.helpers.CardModifierManager.getModifiers;

public class DiamondModifier extends AbstractCardModifier
{
    private int amount;
    public static String ID = "VacantDiamondModifier";

    public DiamondModifier(int amount)
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
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(player, player, new BufferPower(player, amount), amount));
    }

    @Override
    public String modifyDescription(String rawDescription, AbstractCard card)
    {
        if(this.amount > 0)
            rawDescription += " NL Gain " + amount + " Buffer.";
        return rawDescription;
    }

    public static void Enhance(AbstractCard card, int amount)
    {
        ArrayList<AbstractCardModifier> Diamond = getModifiers(card, ID);
        if(Diamond.size() > 0 && Diamond.get(0) instanceof DiamondModifier)
        {
            DiamondModifier DiamondModifier = (DiamondModifier)Diamond.get(0);
            DiamondModifier.Increase(amount);
        }
        else
            addModifier(card, new DiamondModifier(amount));
    }

    @Override
    public String identifier(AbstractCard card)
    {
        return ID;
    }

    @Override
    public AbstractCardModifier makeCopy()
    {
        return new DiamondModifier(amount);
    }
}