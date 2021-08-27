package theVacant.cards.Modifiers;

import basemod.BaseMod;
import basemod.abstracts.AbstractCardModifier;
import basemod.helpers.CardModifierManager;
import basemod.helpers.TooltipInfo;
import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import theVacant.actions.MoveFromExhaustToDrawAction;
import theVacant.util.KeywordManager;

import java.util.ArrayList;
import java.util.List;

import static basemod.helpers.CardModifierManager.*;

public class MaterializeModifier extends AbstractCardModifier
{
    private int amount;
    public static String ID = "VacantMaterializeModifier";

    public MaterializeModifier(int turns)
    {
        amount = turns;
    }

    public void Increase(int num)
    {
        amount += num;
    }

    @Override
    public void onInitialApplication(AbstractCard card)
    {
        if(!card.keywords.toString().contains(KeywordManager.MATERIALIZE_ID))
            card.keywords.add(KeywordManager.MATERIALIZE_ID);
    }

    @Override
    public void atEndOfTurn(AbstractCard card, CardGroup group)
    {
        AbstractPlayer player = AbstractDungeon.player;
        if (player != null && player.exhaustPile.contains(card))
        {
            amount--;
            if(amount == 0)
                AbstractDungeon.actionManager.addToBottom(new MoveFromExhaustToDrawAction(card));
        }
    }

    @Override
    public String modifyDescription(String rawDescription, AbstractCard card)
    {
        if(amount <= 0)
            return rawDescription;
        rawDescription += " [#C687EE]Materialize[] " + amount + ".";
        if(!card.rawDescription.toLowerCase().contains(BaseMod.getKeywordProper(KeywordManager.MATERIALIZE_ID).toLowerCase()))
            card.keywords.add(0, KeywordManager.MATERIALIZE_ID);
        return rawDescription;
    }

    public static void Enhance(AbstractCard card, int turns)
    {
        MaterializeModifier materializeModifier = null;
        ArrayList<AbstractCardModifier> materialize = getModifiers(card, ID);
        for (AbstractCardModifier mod : materialize)
        {
            if(mod instanceof MaterializeModifier)
                materializeModifier = (MaterializeModifier) mod;
        }
        if(materializeModifier != null)
            materializeModifier.Increase(turns);
        else
            addModifier(card, new MaterializeModifier(turns));
    }

    @Override
    public String identifier(AbstractCard card)
    {
        return ID;
    }

    @Override
    public AbstractCardModifier makeCopy()
    {
        return new MaterializeModifier(amount);
    }
}