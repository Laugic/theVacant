package theVacant.cards.Modifiers;

import basemod.BaseMod;
import basemod.abstracts.AbstractCardModifier;
import basemod.helpers.TooltipInfo;
import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import theVacant.util.KeywordManager;

import java.util.ArrayList;
import java.util.List;

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
/*
    @Override
    public void onInitialApplication(AbstractCard card)
    {
        card.glowColor = Color.CYAN;
        if(!card.keywords.toString().contains(KeywordManager.SOULFORGED_ID))
            card.keywords.add(KeywordManager.SOULFORGED_ID);
    }*/

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
/*
    @Override
    public String modifyDescription(String rawDescription, AbstractCard card)
    {
        if(this.amount > 0)
        {
            rawDescription = "[#82eeee]Soulforged[] " + this.amount + ". NL " + rawDescription;
            card.glowColor = Color.CYAN;
            if(!card.rawDescription.toLowerCase().contains(BaseMod.getKeywordProper(KeywordManager.SOULFORGED_ID).toLowerCase()))
                card.keywords.add(0, KeywordManager.SOULFORGED_ID);
        }
        return rawDescription;
    }*/

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