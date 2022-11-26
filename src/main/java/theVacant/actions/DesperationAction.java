package theVacant.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.orbs.AbstractOrb;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndAddToHandEffect;
import theVacant.cards.Modifiers.*;
import theVacant.cards.Skills.Desperation;
import theVacant.cards.Skills.Enchant;
import theVacant.orbs.*;

public class DesperationAction extends AbstractGameAction
{

    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(Desperation.ID);

    AbstractPlayer player;
    boolean upgraded;

    public DesperationAction(boolean freeThisTurn)
    {
        this.actionType = ActionType.CARD_MANIPULATION;

        startDuration = Settings.ACTION_DUR_FAST;
        duration = startDuration;
        player = AbstractDungeon.player;
        this.upgraded = freeThisTurn;
    }

    public void update()
    {
        if (this.duration == this.startDuration)
        {
            if (player.hand.size() == 0)
            {
                this.isDone = true;
                return;
            }
            String displayString = cardStrings.EXTENDED_DESCRIPTION[0];
            AbstractDungeon.handCardSelectScreen.open(displayString, 1, false, false, false, false, true);
            tickDuration();
            return;
        }
        if (!AbstractDungeon.handCardSelectScreen.wereCardsRetrieved)
        {
            for (AbstractCard c : AbstractDungeon.handCardSelectScreen.selectedCards.group)
            {
                AbstractDungeon.player.hand.addToTop(c);

                AbstractCard newCard = c.makeStatEquivalentCopy();

                if(upgraded)
                    newCard.setCostForTurn(0);

                AbstractDungeon.effectList.add(new ShowCardAndAddToHandEffect(newCard));
            }
            AbstractDungeon.handCardSelectScreen.wereCardsRetrieved = true;
        }
        tickDuration();
    }
}
