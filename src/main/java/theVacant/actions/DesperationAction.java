package theVacant.actions;

import basemod.cardmods.ExhaustMod;
import basemod.helpers.CardModifierManager;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndAddToHandEffect;
import sun.security.krb5.internal.crypto.Des;
import theVacant.cards.Skills.Desperation;

import java.util.ArrayList;

public class DesperationAction extends AbstractGameAction
{

    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(Desperation.ID);

    AbstractPlayer player;
    boolean freeThisTurn;
    ArrayList<AbstractCard> removedCards;

    public DesperationAction(boolean freeThisTurn)
    {
        this.actionType = ActionType.CARD_MANIPULATION;

        startDuration = Settings.ACTION_DUR_FAST;
        duration = startDuration;
        player = AbstractDungeon.player;
        this.freeThisTurn = freeThisTurn;
        removedCards = new ArrayList<>();
    }

    public void update()
    {
        if (this.duration == this.startDuration)
        {
            /*for (AbstractCard card: AbstractDungeon.player.hand.group) {
                if(card instanceof Desperation)
                    removedCards.add(card);
            }
            for (AbstractCard card: removedCards)
                AbstractDungeon.player.hand.group.remove(card);
            */
            if (player.discardPile.size() == 0)
            {
                isDone = true;
                return;
            }
            if(player.discardPile.size() == 1)
            {
                copyCard(player.discardPile.getTopCard());
                isDone = true;
                done();
                return;
            }
            String displayString = cardStrings.EXTENDED_DESCRIPTION[0];

            CardGroup group = new CardGroup(CardGroup.CardGroupType.CARD_POOL);
            for (AbstractCard card: player.discardPile.group) {
                if(!(card instanceof Desperation))
                    group.addToTop(card);
            }
            AbstractDungeon.gridSelectScreen.open(group, 1, false, displayString);
            tickDuration();
            return;
        }
        if (!AbstractDungeon.gridSelectScreen.selectedCards.isEmpty())
        {
            for (AbstractCard c : AbstractDungeon.gridSelectScreen.selectedCards)
                copyCard(c);

            AbstractDungeon.gridSelectScreen.selectedCards.clear();
            done();
        }
        tickDuration();
    }

    private void copyCard(AbstractCard card) {
        AbstractCard newCard = card.makeStatEquivalentCopy();
        if(freeThisTurn)
            newCard.setCostForTurn(0);
        player.hand.addToHand(newCard);

        for (AbstractCard c : player.discardPile.group) {
            c.unhover();
            c.target_x = CardGroup.DISCARD_PILE_X;
            c.target_y = 0.0F;
        }
    }

    private void done(){
        AbstractDungeon.player.hand.refreshHandLayout();
        for (AbstractCard c : player.hand.group)
            c.applyPowers();
    }
}
