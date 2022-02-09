package theVacant.actions;

import basemod.BaseMod;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.vfx.combat.InflameEffect;
import theVacant.cards.AbstractDynamicCard;
import theVacant.characters.TheVacant;
import theVacant.powers.*;
import theVacant.relics.LocketRelic;

public class VacantMillAction  extends AbstractGameAction
{
    private float startingDuration;
    private int startAmount = 0;
    private int voidAmount;
    private int millNum;

    private SFXAction waka = new SFXAction("theVacant:waka");

    public VacantMillAction(int numCards)
    {
        amount = numCards;
        voidAmount = 0;
        millNum = 0;
        startAmount = amount;
        actionType = ActionType.CARD_MANIPULATION;
        startingDuration = Settings.ACTION_DUR_FAST;
        duration = startingDuration;
    }

    public void update()
    {
        if(amount > 0)
        {
            PreMill();
            while (amount > 0)
            {
                if (AbstractDungeon.player.drawPile.size() > 0)
                    ProcessMill();
                amount--;
            }
            PostMill();
            isDone = true;
        }
    }

    private void PreMill()
    {

    }

    private void ProcessMill()
    {
        AbstractCard card = AbstractDungeon.player.drawPile.getTopCard();
        millNum++;
        if(card != null)
        {
            if(card instanceof AbstractDynamicCard)
            {
                CheckRebound((AbstractDynamicCard)card);
                return;
            }
            MoveToDiscard(card);
            return;
        }
    }

    private void CheckRebound(AbstractDynamicCard card)
    {
        if((card).rebound || GetSpecialRebound(card))
            Rebound(card);
        else
        {
            if(card.postMillAction)
                card.PostMillAction();
            MoveToDiscard(card);
        }
    }

    private void Rebound(AbstractDynamicCard card)
    {
        if(AbstractDungeon.player.hand.size() >= BaseMod.MAX_HAND_SIZE)
        {
            if(card.postMillAction)
                card.PostMillAction();
            MoveToDiscard(card);
            return;
        }
        AbstractDungeon.player.drawPile.moveToHand(card, AbstractDungeon.player.drawPile);
        PostRebound(card);
        if(card.postMillAction)
            card.PostMillAction();
        ProcessPostMill(card, true);
    }

    private void MoveToDiscard(AbstractCard card)
    {
        AbstractDungeon.player.drawPile.moveToDiscardPile(AbstractDungeon.player.drawPile.getTopCard());
        ProcessPostMill(card, false);
    }

    private void PostMill()
    {
        AbstractDungeon.player.hand.applyPowers();
    }

    private void GetBonusVoid()
    {
        AbstractPlayer player = AbstractDungeon.player;
        if(player != null && player.hasPower(VacancyRune.POWER_ID))
            voidAmount += player.getPower(VacancyRune.POWER_ID).amount;
    }

    private void GainVoid()
    {
        AbstractPlayer player = AbstractDungeon.player;
        if(player != null && voidAmount > 0)
        {
            addToTop(new VFXAction(player, new InflameEffect(player), .1F));
            player.addPower(new VoidPower(player, player, voidAmount));
        }
    }

    private void ProcessPostMill(AbstractCard card, boolean rebounded)
    {
        PostMillCard(card);
        PostMillVoidGain();
    }

    private void PostMillCard(AbstractCard card)
    {
        AbstractPlayer player = AbstractDungeon.player;
        if(player != null)
        {
            if(player.hasPower(CleanseSoulPower.POWER_ID))
            {
                if(card.type == AbstractCard.CardType.STATUS || card.type == AbstractCard.CardType.CURSE)
                {
                    actionType = ActionType.EXHAUST;
                    if(player.discardPile.contains(card))
                    {
                        player.discardPile.moveToExhaustPile(card);
                        if(!AbstractDungeon.actionManager.actions.contains(waka))
                            AbstractDungeon.actionManager.addToBottom(waka);
                        player.getPower(CleanseSoulPower.POWER_ID).flash();
                    }
                }
            }
            if(player.hasPower(AquamarinePower.POWER_ID) && player.getPower(AquamarinePower.POWER_ID).amount > 0)
                addToBot(new GainBlockAction(player, player.getPower(AquamarinePower.POWER_ID).amount));
            if(player instanceof TheVacant)
                ((TheVacant)player).millsThisTurn++;
            if(player.hasPower(ForgeSoulPower.POWER_ID) && card.canUpgrade())
                card.upgrade();
        }
    }

    private void PostMillVoidGain()
    {
        voidAmount++;
    }

    private void PostRebound(AbstractCard card)
    {
        AbstractPlayer player = AbstractDungeon.player;
        if(player != null && player.hasRelic(LocketRelic.ID))
        {
            player.getRelic(LocketRelic.ID).flash();
            addToBot(new GainBlockAction(player, LocketRelic.BLOCK_AMOUNT));
        }
    }

    private boolean GetSpecialRebound(AbstractCard card)
    {
        AbstractPlayer player = AbstractDungeon.player;
        if(player != null)
        {
            if(player.hasPower(RunicThoughtsPower.POWER_ID) && card.type == AbstractCard.CardType.POWER)
            {
                card.setCostForTurn(0);
                return true;
            }
            if(player.hasPower(DarkReflexPower.POWER_ID) && millNum <= player.getPower(DarkReflexPower.POWER_ID).amount)
                return true;
        }
        return false;
    }
}
