package theVacant.actions;

import basemod.BaseMod;
import basemod.helpers.CardModifierManager;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.DuplicationPower;
import com.megacrit.cardcrawl.vfx.combat.InflameEffect;
import theVacant.cards.AbstractDynamicCard;
import theVacant.cards.Modifiers.RicochetMod;
import theVacant.cards.Modifiers.SoulMod;
import theVacant.characters.TheVacant;
import theVacant.powers.*;
import theVacant.relics.LocketRelic;
import theVacant.vfx.ShowCardAndMillEffect;

import java.util.ArrayList;

public class VacantMillAction extends AbstractGameAction
{
    private float startingDuration;
    private int startAmount = 0;
    private int voidAmount;
    private int millNum;
    private int postReturnAmount = 0;
    private AbstractCard ignoredCard;
    private int numType = 0, typesRicocheted = 0;
    private boolean gainTemperanceForMill = false;
    private AbstractCard.CardType millUntil = null, ricochetType = null;

    private ArrayList<AbstractGameAction> actions;

    private final SFXAction waka = new SFXAction("theVacant:waka");

    public VacantMillAction(int numCards)
    {
        amount = numCards;
        voidAmount = 0;
        millNum = 0;
        startAmount = amount;
        actionType = ActionType.CARD_MANIPULATION;
        startingDuration = Settings.ACTION_DUR_FAST;
        duration = startingDuration;
        ignoredCard = null;
        actions = new ArrayList<>();
    }

    public VacantMillAction(int numCards, AbstractCard cardToIgnore)
    {
        amount = numCards;
        voidAmount = 0;
        millNum = 0;
        startAmount = amount;
        actionType = ActionType.CARD_MANIPULATION;
        startingDuration = Settings.ACTION_DUR_FAST;
        duration = startingDuration;
        ignoredCard = cardToIgnore;
        actions = new ArrayList<>();
    }

    public VacantMillAction(int numCards, AbstractCard.CardType ricochetType, AbstractCard cardToIgnore)
    {
        amount = numCards;
        voidAmount = 0;
        millNum = 0;
        startAmount = amount;
        actionType = ActionType.CARD_MANIPULATION;
        startingDuration = Settings.ACTION_DUR_FAST;
        duration = startingDuration;
        ignoredCard = cardToIgnore;
        this.ricochetType = ricochetType;
        actions = new ArrayList<>();
    }

    public VacantMillAction(int numCards, boolean temperance, int postReturnAmount, AbstractCard returnToIgnore)
    {
        amount = numCards;
        voidAmount = 0;
        millNum = 0;
        startAmount = amount;
        actionType = ActionType.CARD_MANIPULATION;
        startingDuration = Settings.ACTION_DUR_FAST;
        duration = startingDuration;
        gainTemperanceForMill = temperance;
        this.postReturnAmount = postReturnAmount;
        ignoredCard = returnToIgnore;
        actions = new ArrayList<>();
    }

    public VacantMillAction(AbstractCard.CardType millUntil, AbstractCard cardToIgnore){
        this(millUntil, cardToIgnore, 1);
    }

    public VacantMillAction(AbstractCard.CardType millUntil, AbstractCard cardToIgnore, int numType){
        this.millUntil = millUntil;
        amount = 999;
        actions = new ArrayList<>();
        ignoredCard = cardToIgnore;
        this.numType = numType;
        typesRicocheted = 0;
    }


    public void update()
    {
        PreMill();
        while (amount > 0 && AbstractDungeon.player.drawPile.size() > 0)
        {
            ProcessMill();
            amount--;
        }
        PostMill();
        isDone = true;
    }

    private void PreMill()
    {
//        if(amount > AbstractDungeon.player.drawPile.size() && AbstractDungeon.player.drawPile.size() > 0){
//            if(AbstractDungeon.player.hasPower(RunicThoughtsPower.POWER_ID)){
//                int runic = AbstractDungeon.player.getPower(RunicThoughtsPower.POWER_ID).amount;
//                addToBot(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new DoublePlayPower(AbstractDungeon.player, AbstractDungeon.player, runic)));
//                AbstractDungeon.player.getPower(RunicThoughtsPower.POWER_ID).flash();
//            }
//        }
    }

    private void ProcessMill()
    {
        AbstractCard card = AbstractDungeon.player.drawPile.getTopCard();
        millNum++;
        if(card != null && (!card.equals(ignoredCard)))
            CheckRicochet(card);
    }

    private void CheckRicochet(AbstractCard card)
    {
        if((card instanceof AbstractDynamicCard && ((AbstractDynamicCard)card).ricochet) || GetSpecialRicochet(card))
            Ricochet(card);
        else if(millUntil != null && card.type == millUntil)
        {
            Ricochet(card);
            typesRicocheted++;
            if(typesRicocheted >= numType)
                amount = 0;
        }
        else
        {
            MoveToDiscard(card);
            if((card instanceof AbstractDynamicCard && ((AbstractDynamicCard)card).postMillAction))
                ((AbstractDynamicCard)card).PostMillAction();
        }
    }

    private void Ricochet(AbstractCard card)
    {
        if(AbstractDungeon.player.hand.size() >= BaseMod.MAX_HAND_SIZE)
        {
            MoveToDiscard(card);
            if((card instanceof AbstractDynamicCard && ((AbstractDynamicCard)card).postMillAction))
                ((AbstractDynamicCard)card).PostMillAction();
            return;
        }

        AbstractDungeon.player.drawPile.removeCard(card);
        addToBot(new VFXAction(AbstractDungeon.player, new ShowCardAndMillEffect(card, AbstractDungeon.player.hand), Settings.ACTION_DUR_XFAST, true));

        if((card instanceof AbstractDynamicCard && ((AbstractDynamicCard)card).postMillAction))
            ((AbstractDynamicCard)card).PostMillAction();
        ProcessPostMill(card, true);
    }

    private void MoveToDiscard(AbstractCard card)
    {
        AbstractDungeon.player.drawPile.removeCard(card);
        addToBot(new VFXAction(AbstractDungeon.player, new ShowCardAndMillEffect(card, AbstractDungeon.player.discardPile), Settings.ACTION_DUR_XFAST, true));
        AbstractDungeon.player.discardPile.addToTop(card);
//        AbstractDungeon.player.drawPile.moveToDiscardPile(AbstractDungeon.player.drawPile.getTopCard());
        ProcessPostMill(card, false);
    }

    private void PostMill()
    {
        AbstractDungeon.player.hand.applyPowers();
        if(gainTemperanceForMill && millNum > 0)
            addToBot(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new TemperancePower(AbstractDungeon.player, AbstractDungeon.player, millNum)));
        int bonusReturn = 0;
//        if(AbstractDungeon.player.hasPower(RunicThoughtsPower.POWER_ID)){
//            bonusReturn += AbstractDungeon.player.getPower(RunicThoughtsPower.POWER_ID).amount;
//            AbstractDungeon.player.getPower(RunicThoughtsPower.POWER_ID).flash();
//        }
        addToBot(new MillWaitAction());
        if(postReturnAmount + bonusReturn > 0)
            addToBot(new ReturnAction(postReturnAmount + bonusReturn, ignoredCard));
    }

    private void ProcessPostMill(AbstractCard card, boolean ricocheted)
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
                        addToBot(new ApplyPowerAction(player, player, new TemperancePower(player, player, player.getPower(CleanseSoulPower.POWER_ID).amount), player.getPower(CleanseSoulPower.POWER_ID).amount));
                    }
                }
            }
            if(player.hasPower(AquamarinePower.POWER_ID) && player.getPower(AquamarinePower.POWER_ID).amount > 0)
                addToBot(new GainBlockAction(player, player.getPower(AquamarinePower.POWER_ID).amount));
            if(player instanceof TheVacant)
                ((TheVacant)player).millsThisTurn++;
            if(player.hasPower(ForgeSoulPower.POWER_ID) && (card.baseDamage >=0 || card.baseBlock >= 0))
            {
//                for(int i = 0; i < player.getPower(ForgeSoulPower.POWER_ID).amount && card.canUpgrade(); i++)
//                    card.upgrade();
                CardModifierManager.addModifier(card, new SoulMod(player.getPower(ForgeSoulPower.POWER_ID).amount));
            }
        }
    }

    private void PostMillVoidGain()
    {
        voidAmount++;
    }



    private boolean GetSpecialRicochet(AbstractCard card)
    {
        AbstractPlayer player = AbstractDungeon.player;
        if(player.hasPower(GloomPower.POWER_ID) && card.type == AbstractCard.CardType.POWER){
            AbstractDungeon.player.getPower(GloomPower.POWER_ID).flash();
            return true;
        }
        if(CardModifierManager.hasModifier(card, RicochetMod.ID))
            return true;
        if(card.type == ricochetType)
            return true;
        if(AbstractDungeon.player.hasRelic(LocketRelic.ID) && (LocketRelic.numMilled < LocketRelic.MAX_RICOCHET)){

            addToBot(new RelicAboveCreatureAction(AbstractDungeon.player, AbstractDungeon.player.getRelic(LocketRelic.ID)));
            AbstractDungeon.player.getRelic(LocketRelic.ID).flash();
            LocketRelic.numMilled++;
            if(LocketRelic.numMilled >= LocketRelic.MAX_RICOCHET)
                AbstractDungeon.player.getRelic(LocketRelic.ID).grayscale = true;
            return true;
        }
        RunicThoughtsPower runic = (RunicThoughtsPower) AbstractDungeon.player.getPower(RunicThoughtsPower.POWER_ID);
        if(runic != null){
            if(card.type == AbstractCard.CardType.STATUS && AbstractDungeon.player.hasPower(CleanseSoulPower.POWER_ID))
                return false;
            if(runic.millThisTurn < runic.amount){
                if(card.costForTurn > 0)
                    card.setCostForTurn(card.costForTurn - 1);
                runic.millThisTurn++;
                runic.flash();
                return true;
            }
        }
        return false;
    }
}
