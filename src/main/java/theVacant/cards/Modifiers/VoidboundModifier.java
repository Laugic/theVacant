package theVacant.cards.Modifiers;

import basemod.abstracts.AbstractCardModifier;
import basemod.helpers.CardModifierManager;
import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardQueueItem;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import java.util.ArrayList;

import static basemod.helpers.CardModifierManager.addModifier;
import static basemod.helpers.CardModifierManager.getModifiers;

public class VoidboundModifier extends AbstractCardModifier
{
    private int amount;
    public static String ID = "VacantVoidboundModifier";

    public VoidboundModifier(int amount)
    {
        this.amount = amount;
    }

    public void Increase(int num)
    {
        this.amount += num;
    }

    @Override
    public void onInitialApplication(AbstractCard card)
    {
        card.glowColor = Color.PURPLE;
    }

    @Override
    public void onUse(AbstractCard card, AbstractCreature target, UseCardAction action)
    {
        if (this.amount > 0)
        {
            for(int i = 0; i < this.amount; i++)
            {
                AbstractMonster m = null;
                if (action.target != null)
                    m = (AbstractMonster)action.target;
                AbstractCard tmp = card.makeSameInstanceOf();
                AbstractDungeon.player.limbo.addToBottom(tmp);
                tmp.current_x = card.current_x;
                tmp.current_y = card.current_y;
                tmp.target_x = Settings.WIDTH / 2.0F - 300.0F * Settings.scale;
                tmp.target_y = Settings.HEIGHT / 2.0F;
                if (m != null)
                    tmp.calculateCardDamage(m);
                tmp.purgeOnUse = true;
                CardModifierManager.removeModifiersById(tmp, ID, true);
                AbstractDungeon.actionManager.addCardQueueItem(new CardQueueItem(tmp, m, card.energyOnUse, true, true), true);
            }
        }
    }

    @Override
    public String modifyDescription(String rawDescription, AbstractCard card)
    {
        if(this.amount > 0)
        {
            rawDescription += " NL thevacant:Voidbound " + this.amount + ".";
            card.glowColor = Color.PURPLE;
        }
        return rawDescription;
    }

    public static void Enhance(AbstractCard card, int amount)
    {
        ArrayList<AbstractCardModifier> voidbound = getModifiers(card, ID);
        if(voidbound.size() > 0 && voidbound.get(0) instanceof VoidboundModifier)
        {
            VoidboundModifier voidmod = (VoidboundModifier)voidbound.get(0);
            voidmod.Increase(amount);
        }
        else
        {
            addModifier(card, new VoidboundModifier(amount));
            card.glowColor = Color.PURPLE;
        }
    }

    @Override
    public String identifier(AbstractCard card)
    {
        return ID;
    }

    @Override
    public AbstractCardModifier makeCopy()
    {
        return new VoidboundModifier(amount);
    }
}