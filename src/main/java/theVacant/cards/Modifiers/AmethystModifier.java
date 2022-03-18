package theVacant.cards.Modifiers;

import basemod.abstracts.AbstractCardModifier;
import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import theVacant.actions.VacantMillAction;

import java.util.ArrayList;

import static basemod.helpers.CardModifierManager.addModifier;
import static basemod.helpers.CardModifierManager.getModifiers;

public class AmethystModifier extends AbstractCardModifier
{
    private int amount;
    public static String ID = "VacantAmethystModifier";

    public AmethystModifier(int amount)
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
            AbstractDungeon.actionManager.addToBottom(new VacantMillAction(amount, card));
    }

    @Override
    public String modifyDescription(String rawDescription, AbstractCard card)
    {
        if(this.amount > 0)
            rawDescription += " NL Mill " + amount + " .";
        return rawDescription;
    }

    public static void Enhance(AbstractCard card, int amount)
    {
        ArrayList<AbstractCardModifier> Amethyst = getModifiers(card, ID);
        if(Amethyst.size() > 0 && Amethyst.get(0) instanceof AmethystModifier)
        {
            AmethystModifier AmethystModifier = (AmethystModifier)Amethyst.get(0);
            AmethystModifier.Increase(amount);
        }
        else
            addModifier(card, new AmethystModifier(amount));
    }

    @Override
    public float modifyBlock(float block, AbstractCard card)
    {
        if(CheckDrawEmpty() && block > 0)
        {
            card.glowColor = Color.PURPLE;
            return block + amount;
        }
        return block;
    }

    @Override
    public float modifyDamage(float damage, DamageInfo.DamageType type, AbstractCard card, AbstractMonster target)
    {
        if(CheckDrawEmpty() && damage > 0)
        {
            card.glowColor = Color.PURPLE;
            return damage + amount;
        }
        return damage;
    }

    @Override
    public String identifier(AbstractCard card)
    {
        return ID;
    }

    public boolean CheckDrawEmpty()
    {
        if (AbstractDungeon.player.drawPile.isEmpty() && !AbstractDungeon.actionManager.turnHasEnded
                && !AbstractDungeon.isScreenUp && (AbstractDungeon.getCurrRoom()).phase == AbstractRoom.RoomPhase.COMBAT)
            return true;
        return false;
    }
    @Override
    public AbstractCardModifier makeCopy()
    {
        return new AmethystModifier(amount);
    }
}