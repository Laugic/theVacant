package theVacant.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import theVacant.cards.Modifiers.EchoModifier;
import theVacant.cards.Modifiers.MaterializeModifier;
import theVacant.cards.Modifiers.SoulforgedModifier;
import theVacant.cards.Modifiers.VoidboundModifier;

import static com.megacrit.cardcrawl.characters.AbstractPlayer.uiStrings;

public class EnhanceInHandAction extends AbstractGameAction
{

    public static final String[] TEXT = uiStrings.TEXT;

    private float startingDuration;
    public int numCards;
    public String modifierID;
    public int amount;
    public AbstractPlayer p;
    private boolean isRandom;

    private boolean anyNumber;

    private boolean canPickZero;

    public EnhanceInHandAction(int numCards, String id, int amountToEnhance)
    {
        this.actionType = ActionType.CARD_MANIPULATION;
        this.startingDuration = Settings.ACTION_DUR_FAST;
        this.duration = this.startingDuration;
        this.modifierID = id;
        this.numCards = numCards;
        this.amount = amountToEnhance;


        this.p = AbstractDungeon.player;
        this.canPickZero = false;
        this.isRandom = false;
    }

    public void update()
    {
        if (this.duration == this.startDuration)
        {
            if (this.p.hand.size() == 0)
            {
                this.isDone = true;
                return;
            }
            if (this.p.hand.size() <= this.amount)
            {
                this.amount = this.p.hand.size();
                int tmp = this.p.hand.size();
                for (int i = 0; i < tmp; i++)
                {
                    AbstractCard c = this.p.hand.getTopCard();
                    this.p.hand.moveToExhaustPile(c);
                }
                CardCrawlGame.dungeon.checkForPactAchievement();
                return;
            }
            if (this.isRandom)
            {
                for (int i = 0; i < this.amount; i++)
                    this.p.hand.moveToExhaustPile(this.p.hand.getRandomCard(AbstractDungeon.cardRandomRng));
                CardCrawlGame.dungeon.checkForPactAchievement();
            }
            else
            {
                AbstractDungeon.handCardSelectScreen.open(TEXT[0], this.amount, this.anyNumber, this.canPickZero);
                tickDuration();
                return;
            }
        }
        if (!AbstractDungeon.handCardSelectScreen.wereCardsRetrieved)
        {
            for (AbstractCard c : AbstractDungeon.handCardSelectScreen.selectedCards.group)
                this.p.hand.moveToExhaustPile(c);
            CardCrawlGame.dungeon.checkForPactAchievement();
            AbstractDungeon.handCardSelectScreen.wereCardsRetrieved = true;
        }
        tickDuration();
        /*AbstractPlayer player = AbstractDungeon.player;
        if (this.duration == this.startDuration)
        {
            if (player.hand.size() == 0)
            {
                this.isDone = true;
                return;
            }
            if (player.hand.size() <= this.amount)
            {
                for (int i = 0; i < player.hand.size(); i++)
                {
                    AbstractCard card = player.hand.getNCardFromTop(i);
                    Enhance(card);
                }
                return;
            }
            AbstractDungeon.handCardSelectScreen.open(TEXT[0], this.amount, false, false);
            tickDuration();
            return;
        }
        if (!AbstractDungeon.handCardSelectScreen.wereCardsRetrieved)
        {
            for (AbstractCard card : AbstractDungeon.handCardSelectScreen.selectedCards.group)
                Enhance(card);
            AbstractDungeon.handCardSelectScreen.wereCardsRetrieved = true;
        }
        tickDuration();
/*
        CheckIfAll();
        CreateSelection();
        PickCards();*/
    }

    private void CheckIfAll()
    {
        AbstractPlayer player = AbstractDungeon.player;
        if(this.duration == this.startingDuration)
        {
            if(player.hand.size() == 0)
            {
                this.isDone = true;
                return;
            }
            if(player.hand.size() < this.numCards)
            {
                int temp = player.hand.size();
                for (int i = 0; i < temp; i++)
                {
                    AbstractCard card = player.hand.getNCardFromTop(i);
                    Enhance(card);
                }
                AbstractDungeon.player.hand.applyPowers();
                tickDuration();
                this.isDone = true;
                return;
            }
        }
    }

    private void CreateSelection()
    {
        AbstractPlayer player = AbstractDungeon.player;
        if(this.duration == this.startingDuration)
        {
            if (player.hand.size() > this.amount)
                AbstractDungeon.handCardSelectScreen.open(TEXT[0], this.amount, true, false);
            AbstractDungeon.player.hand.applyPowers();
            tickDuration();
        }
    }

    private void PickCards()
    {
        AbstractPlayer player = AbstractDungeon.player;
        if (!AbstractDungeon.handCardSelectScreen.wereCardsRetrieved)
        {
            for (AbstractCard card : AbstractDungeon.handCardSelectScreen.selectedCards.group)
            {
                card.unhover();
                Enhance(card);
                AbstractDungeon.player.hand.addToTop(card);
                AbstractDungeon.player.hand.refreshHandLayout();
                AbstractDungeon.player.hand.applyPowers();
            }
            AbstractDungeon.handCardSelectScreen.wereCardsRetrieved = true;
            player.hand.refreshHandLayout();
            player.hand.applyPowers();
            this.isDone = true;
        }
        tickDuration();
    }

    private void Enhance(AbstractCard card)
    {
        if(this.modifierID == VoidboundModifier.ID)
            VoidboundModifier.Enhance(card, this.amount);
        if(this.modifierID == SoulforgedModifier.ID)
            SoulforgedModifier.Enhance(card, this.amount);
        if(this.modifierID == EchoModifier.ID)
            EchoModifier.Enhance(card, this.amount);
        if(this.modifierID == MaterializeModifier.ID)
            MaterializeModifier.Enhance(card, this.amount);
        card.initializeDescription();
    }
}
