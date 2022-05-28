package theVacant.cards.Modifiers;

import basemod.abstracts.AbstractCardModifier;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theVacant.powers.DoomPower;

import java.util.ArrayList;

import static basemod.helpers.CardModifierManager.addModifier;
import static basemod.helpers.CardModifierManager.getModifiers;

public class OnyxModifier extends AbstractCardModifier
{
    private int amount;
    public static String ID = "VacantOnyxModifier";

    public OnyxModifier(int amount)
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
        {
            for (AbstractMonster mo : (AbstractDungeon.getCurrRoom()).monsters.monsters)
                AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(mo, player, new DoomPower(mo, player, amount), amount, true, AbstractGameAction.AttackEffect.NONE));
        }
    }

    @Override
    public String modifyDescription(String rawDescription, AbstractCard card)
    {
        if(this.amount > 0)
            rawDescription += " NL Apply " + amount + " thevacant:Doom to ALL enemies.";
        return rawDescription;
    }

    public static void Enhance(AbstractCard card, int amount)
    {
        ArrayList<AbstractCardModifier> Onyx = getModifiers(card, ID);
        if(Onyx.size() > 0 && Onyx.get(0) instanceof OnyxModifier)
        {
            OnyxModifier OnyxModifier = (OnyxModifier)Onyx.get(0);
            OnyxModifier.Increase(amount);
        }
        else
            addModifier(card, new OnyxModifier(amount));
    }

    @Override
    public String identifier(AbstractCard card)
    {
        return ID;
    }

    @Override
    public AbstractCardModifier makeCopy()
    {
        return new OnyxModifier(amount);
    }
}