package theVacant.actions;

import basemod.BaseMod;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import theVacant.cards.Modifiers.SoulforgedModifier;
import theVacant.cards.Modifiers.VoidboundModifier;

import static com.megacrit.cardcrawl.characters.AbstractPlayer.uiStrings;

public class EnhanceInPileAction extends AbstractGameAction
{

    public static final String[] TEXT = uiStrings.TEXT;

    private float startingDuration;
    public int numCards;
    public String modifierID;
    public int amount;
    public CardGroup cardGroup;

    public EnhanceInPileAction(CardGroup cardGroup, int numCards, String id, int amountToEnhance)
    {
        this.actionType = ActionType.CARD_MANIPULATION;
        this.startingDuration = Settings.ACTION_DUR_FAST;
        this.duration = this.startingDuration;
        this.cardGroup = cardGroup;
        this.modifierID = id;
        this.numCards = numCards;
        this.amount = amountToEnhance;
    }

    public void update()
    {
        CreateSelection();
        PickCards();
    }

    private void CreateSelection()
    {
        if(cardGroup.size() == 0)
        {
            this.isDone = true;
            return;
        }
        if(this.duration == this.startingDuration)
        {
            CardGroup tempGroup = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);
            for (AbstractCard card : cardGroup.group)
            {
                tempGroup.addToRandomSpot(card);
            }
            if(tempGroup.size() == 0)
                this.isDone = true;
            else
            {
                if(tempGroup.size() <= this.amount)
                {
                    for(int i = 0; i < this.amount; i++)
                    {
                        AbstractCard card = tempGroup.getTopCard();
                        if(AbstractDungeon.player.hand.size() < BaseMod.MAX_HAND_SIZE)
                        {
                            card.unhover();
                            card.lighten(true);
                            card.setAngle(0.0F);
                            card.drawScale = 0.12F;
                            card.targetDrawScale = 0.75F;
                            card.current_x = CardGroup.DRAW_PILE_X;
                            card.current_y = CardGroup.DRAW_PILE_Y;
                            Enhance(card);
                            card.initializeDescription();
                            cardGroup.removeCard(card);
                            AbstractDungeon.player.hand.addToTop(card);
                            AbstractDungeon.player.hand.refreshHandLayout();
                            AbstractDungeon.player.hand.applyPowers();
                            tempGroup.removeCard(card);
                            if(tempGroup.size() == 0)
                                return;
                        }
                        else
                        {
                            cardGroup.moveToDiscardPile(card);
                            AbstractDungeon.player.createHandIsFullDialog();
                        }
                    }
                    this.isDone = true;
                }
                else
                {
                    AbstractDungeon.gridSelectScreen.open(tempGroup, this.amount, TEXT[0], false);
                    tickDuration();
                }
            }
        }
    }

    private void PickCards()
    {
        AbstractPlayer player = AbstractDungeon.player;
        if (AbstractDungeon.gridSelectScreen.selectedCards.size() != 0)
        {
            for (AbstractCard card : AbstractDungeon.gridSelectScreen.selectedCards)
            {
                card.unhover();
                Enhance(card);
                card.initializeDescription();

                if (player.hand.size() == BaseMod.MAX_HAND_SIZE)
                {
                    cardGroup.moveToDiscardPile(card);
                    player.createHandIsFullDialog();
                } else
                {
                    cardGroup.removeCard(card);
                    player.hand.addToTop(card);
                }
                player.hand.refreshHandLayout();
                player.hand.applyPowers();
            }
            AbstractDungeon.gridSelectScreen.selectedCards.clear();
            player.hand.refreshHandLayout();
            this.isDone = true;
        }
    }

    private void Enhance(AbstractCard card)
    {
        if(this.modifierID == VoidboundModifier.ID)
            VoidboundModifier.Enhance(card, this.amount);
        if(this.modifierID == SoulforgedModifier.ID)
            SoulforgedModifier.Enhance(card, this.amount);
        card.initializeDescription();
    }
}
