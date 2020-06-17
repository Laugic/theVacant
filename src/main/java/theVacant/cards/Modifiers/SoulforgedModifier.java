package theVacant.cards.Modifiers;

import basemod.abstracts.AbstractCardModifier;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import java.util.ArrayList;

import static basemod.helpers.CardModifierManager.addModifier;
import static basemod.helpers.CardModifierManager.getModifiers;

public class SoulforgedModifier extends AbstractCardModifier
{
    private int amount;
    public static String ID = "VacantSoulforgedModifier";

    public SoulforgedModifier(int amount)
    {
        this.amount = amount;
    }

    public void Increase(int num)
    {
        this.amount += num;
    }

    @Override
    public void onUse(AbstractCard card, AbstractCreature target, UseCardAction action)
    {
        AbstractPlayer player = AbstractDungeon.player;
        if (this.amount > 0 && player != null)
        {
            AbstractDungeon.actionManager.addToBottom(new DrawCardAction(player, this.amount));
            player.gainEnergy(this.amount);
        }
    }

    @Override
    public String modifyDescription(String rawDescription, AbstractCard card)
    {
        if(this.amount > 0)
            rawDescription = "thevacant:Soulforged " + this.amount + ". NL " + rawDescription;
        return rawDescription;
    }

    public static void Enhance(AbstractCard card, int amount)
    {
        ArrayList<AbstractCardModifier> soulforged = getModifiers(card, ID);
        if(soulforged.size() > 0 && soulforged.get(0) instanceof SoulforgedModifier)
        {
            SoulforgedModifier soulmod = (SoulforgedModifier)soulforged.get(0);
            soulmod.Increase(amount);
        }
        else
            addModifier(card, new SoulforgedModifier(amount));
    }

    @Override
    public String identifier(AbstractCard card)
    {
        return ID;
    }

    @Override
    public AbstractCardModifier makeCopy()
    {
        return new SoulforgedModifier(amount);
    }
}