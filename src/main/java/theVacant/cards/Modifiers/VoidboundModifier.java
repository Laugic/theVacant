package theVacant.cards.Modifiers;

import basemod.BaseMod;
import basemod.abstracts.AbstractCardModifier;
import basemod.helpers.CardModifierManager;
import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardQueueItem;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import theVacant.powers.VoidPower;
import theVacant.util.KeywordManager;

import java.util.ArrayList;

import static basemod.helpers.CardModifierManager.addModifier;
import static basemod.helpers.CardModifierManager.getModifiers;

public class VoidboundModifier extends AbstractCardModifier
{
    private int amount;
    private boolean initialPlay;
    private int counter;
    public static String ID = "VacantVoidboundModifier";

    public VoidboundModifier(int amount)
    {
        this.amount = amount;
        this.initialPlay = false;
        this.counter = 0;
    }

    public void Increase(int num)
    {
        this.amount += num;
    }

    @Override
    public void onInitialApplication(AbstractCard card)
    {
        card.glowColor = Color.PURPLE;
        if(!card.keywords.toString().contains(KeywordManager.VOIDBOUND_ID))
            card.keywords.add(KeywordManager.VOIDBOUND_ID);
    }
/*
    @Override
    public void onUse(AbstractCard card, AbstractCreature target, UseCardAction action)
    {
        AbstractPlayer player = AbstractDungeon.player;
        for(int i = 0; i < this.amount; i++)
        {
            card.use(player, (target instanceof AbstractMonster)?((AbstractMonster) target):(null));
        }
    }*/
    @Override
    public float modifyBlock(float block, AbstractCard card)
    {
        if(!CheckDrawEmpty() && block > 0)
        {
            int voidAmount = 0;

            if(AbstractDungeon.player != null) {
                if (AbstractDungeon.player.hasPower(VoidPower.POWER_ID))
                    voidAmount = AbstractDungeon.player.getPower(VoidPower.POWER_ID).amount;
            }
            return block + voidAmount;
        }
        return block;
    }

    @Override
    public float modifyDamage(float damage, DamageInfo.DamageType type, AbstractCard card, AbstractMonster target)
    {
        if(!CheckDrawEmpty() && damage > 0)
        {
            int voidAmount = 0;

            if(AbstractDungeon.player != null) {
                if (AbstractDungeon.player.hasPower(VoidPower.POWER_ID))
                    voidAmount = AbstractDungeon.player.getPower(VoidPower.POWER_ID).amount;
            }
            return damage + voidAmount;
        }
        return damage;
    }

    @Override
    public String modifyDescription(String rawDescription, AbstractCard card)
    {
        if(this.amount > 0)
        {
            rawDescription = "[#ee82ee]Voidbound[]. NL " + rawDescription;
            if(AbstractDungeon.player != null)
            {
                if(AbstractDungeon.player.hasPower(VoidPower.POWER_ID))
                    card.glowColor = Color.PURPLE;
            }
            if(!card.rawDescription.toLowerCase().contains(BaseMod.getKeywordProper(KeywordManager.VOIDBOUND_ID).toLowerCase()))
                card.keywords.add(0, KeywordManager.VOIDBOUND_ID);
        }
        return rawDescription;
    }

    public boolean CheckDrawEmpty()
    {
        if (AbstractDungeon.player.drawPile.isEmpty() && !AbstractDungeon.actionManager.turnHasEnded
                && !AbstractDungeon.isScreenUp && (AbstractDungeon.getCurrRoom()).phase == AbstractRoom.RoomPhase.COMBAT)
            return true;
        return false;
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