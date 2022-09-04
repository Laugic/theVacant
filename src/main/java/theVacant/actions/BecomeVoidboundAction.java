package theVacant.actions;

import basemod.BaseMod;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndAddToHandEffect;
import theVacant.cards.Modifiers.VoidboundModifier;
import theVacant.cards.Skills.Desperation;
import theVacant.cards.Skills.Voidstone;

public class BecomeVoidboundAction extends AbstractGameAction
{

    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(Voidstone.ID);

    AbstractPlayer player;

    public BecomeVoidboundAction(boolean allCards)
    {
        this.actionType = ActionType.CARD_MANIPULATION;

        startDuration = Settings.ACTION_DUR_FAST;
        duration = startDuration;
        player = AbstractDungeon.player;
        if(allCards)
            amount = BaseMod.MAX_HAND_SIZE;
        else
            amount = 1;
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
            if (player.hand.size() <= amount)
            {
                for (AbstractCard c : player.hand.group)
                {
                    VoidboundModifier.Enhance(c, 1);
                }

                this.isDone = true;
                return;
            }
            String displayString = cardStrings.EXTENDED_DESCRIPTION[0];
            AbstractDungeon.handCardSelectScreen.open(displayString, amount, false, false, false, false, true);
            tickDuration();
            return;
        }
        if (!AbstractDungeon.handCardSelectScreen.wereCardsRetrieved)
        {
            for (AbstractCard c : AbstractDungeon.handCardSelectScreen.selectedCards.group)
            {
                AbstractDungeon.player.hand.addToTop(c);

                VoidboundModifier.Enhance(c, 1);
            }
            AbstractDungeon.handCardSelectScreen.wereCardsRetrieved = true;
        }
        tickDuration();
    }
}
