package theVacant.cards.Modifiers;

import basemod.BaseMod;
import basemod.abstracts.AbstractCardModifier;
import basemod.helpers.CardModifierManager;
import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardQueueItem;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.ArtifactPower;
import com.megacrit.cardcrawl.powers.watcher.VigorPower;
import theVacant.powers.TemperancePower;
import theVacant.powers.VoidEmbracePower;
import theVacant.powers.VoidPower;
import theVacant.util.KeywordManager;

import java.util.ArrayList;

import static basemod.helpers.CardModifierManager.addModifier;
import static basemod.helpers.CardModifierManager.getModifiers;

public class EchoModifier extends AbstractCardModifier
{
    private int amount;
    public static String ID = "VacantEchoModifier";

    public EchoModifier(int num)
    {
        amount = num;
    }

    public void Increase(int num)
    {
        amount += num;
    }

    @Override
    public void onInitialApplication(AbstractCard card)
    {
        AddEchoKeyword(card);
    }
/*
    @Override
    public float modifyDamageFinal(float damage, DamageInfo.DamageType type, AbstractCard card, AbstractMonster target)
    {
        AbstractPlayer player = AbstractDungeon.player;
        if (this.amount > 0 && damage > 0 && player != null)
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(player, player, new VigorPower(player, amount * (int)damage), amount * (int)damage));
        return damage;
    }

    @Override
    public float modifyBlockFinal(float block, AbstractCard card)
    {
        AbstractPlayer player = AbstractDungeon.player;
        if (this.amount > 0 && block > 0 && player != null)
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(player, player, new TemperancePower(player, player, amount * (int)block), amount * (int)block));
        return block;
    }
*/
    @Override
    public void onUse(AbstractCard card, AbstractCreature target, UseCardAction action)
    {
        AbstractPlayer player = AbstractDungeon.player;
        if(amount <= 0 || player == null || !player.hasPower(VoidPower.POWER_ID) || player.getPower(VoidPower.POWER_ID).amount <= 0)
            return;

        int voidAmount = player.getPower(VoidPower.POWER_ID).amount;
        if(card.type == AbstractCard.CardType.ATTACK)
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(player, player, new VigorPower(player, GetEchoAmount() * voidAmount), GetEchoAmount() * voidAmount));

        if (card.type == AbstractCard.CardType.SKILL)
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(player, player, new TemperancePower(player, player, GetEchoAmount() * voidAmount), GetEchoAmount() * voidAmount));

        if (card.type == AbstractCard.CardType.POWER)
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(player, player, new ArtifactPower(player, GetEchoAmount()), GetEchoAmount()));
    }

    @Override
    public String modifyDescription(String rawDescription, AbstractCard card)
    {
        if(amount > 0)
        {
            rawDescription += " NL [#6A97E2]Echo[] " + GetEchoAmount() + (GetEchoAmount() > 1 ? " times.":" time.");
            AddEchoKeyword(card);
        }
        return rawDescription;
    }


    private int GetEchoAmount()
    {
        int plusAmount = 0;
        if(AbstractDungeon.isPlayerInDungeon() && AbstractDungeon.player.hasPower(VoidEmbracePower.POWER_ID) && AbstractDungeon.player.getPower(VoidEmbracePower.POWER_ID).amount > 0)
            plusAmount = AbstractDungeon.player.getPower(VoidEmbracePower.POWER_ID).amount;
        return amount + plusAmount;
    }


    private void AddEchoKeyword(AbstractCard card)
    {
        if(!card.keywords.toString().contains(KeywordManager.ECHO_ID))
        {
            switch (card.type)
            {
                case ATTACK:
                    card.keywords.add(KeywordManager.ECHO_ATTACK_ID);
                    break;
                case SKILL:
                    card.keywords.add(KeywordManager.ECHO_SKILL_ID);
                    break;
                case POWER:
                    card.keywords.add(KeywordManager.ECHO_POWER_ID);
                    break;
                default:
                    card.keywords.add(KeywordManager.ECHO_ID);
                    break;
            }
        }
    }

    public static void Enhance(AbstractCard card, int num)
    {
        EchoModifier echoModifier = null;
        ArrayList<AbstractCardModifier> echo = getModifiers(card, ID);
        for (AbstractCardModifier mod : echo)
        {
            if(mod instanceof EchoModifier)
                echoModifier = (EchoModifier) mod;
        }
        if(echoModifier != null)
            echoModifier.Increase(num);
        else
            addModifier(card, new EchoModifier(num));
    }

    @Override
    public String identifier(AbstractCard card)
    {
        return ID;
    }

    @Override
    public AbstractCardModifier makeCopy()
    {
        return new EchoModifier(amount);
    }
}