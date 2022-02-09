package theVacant.cards.Modifiers;

import basemod.BaseMod;
import basemod.abstracts.AbstractCardModifier;
import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import theVacant.util.KeywordManager;

import java.util.ArrayList;

import static basemod.helpers.CardModifierManager.addModifier;
import static basemod.helpers.CardModifierManager.getModifiers;

public class EmeraldModifier extends AbstractCardModifier
{
    private int amount;
    public static String ID = "VacantEmeraldModifier";

    public EmeraldModifier(int amount)
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
            AbstractDungeon.actionManager.addToBottom(new DrawCardAction(player, amount));
    }

    @Override
    public String modifyDescription(String rawDescription, AbstractCard card)
    {
        if(this.amount > 0)
        rawDescription += " NL Draw " + amount + (amount == 1 ? " card." : " cards.");
        return rawDescription;
    }

    public static void Enhance(AbstractCard card, int amount)
    {
        ArrayList<AbstractCardModifier> Emerald = getModifiers(card, ID);
        if(Emerald.size() > 0 && Emerald.get(0) instanceof EmeraldModifier)
        {
            EmeraldModifier EmeraldModifier = (EmeraldModifier)Emerald.get(0);
            EmeraldModifier.Increase(amount);
        }
        else
            addModifier(card, new EmeraldModifier(amount));
    }

    @Override
    public String identifier(AbstractCard card)
    {
        return ID;
    }

    @Override
    public AbstractCardModifier makeCopy()
    {
        return new EmeraldModifier(amount);
    }
}