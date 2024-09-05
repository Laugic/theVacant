package theVacant.actions;

import basemod.BaseMod;
import basemod.helpers.CardModifierManager;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.EmptyDeckShuffleAction;
import com.megacrit.cardcrawl.actions.utility.NewQueueCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import theVacant.cards.Modifiers.FaceDownMod;
import theVacant.cards.Skills.Desperation;
import theVacant.cards.Skills.Memoria;

import java.util.Iterator;

public class PlayDiscardForFreeAndExhaustAction  extends AbstractGameAction {
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(Desperation.ID);
    private AbstractPlayer player;
    private int playAmt;

    public PlayDiscardForFreeAndExhaustAction(int numberOfCards) {
        this.actionType = ActionType.CARD_MANIPULATION;// 19
        this.duration = this.startDuration = Settings.ACTION_DUR_FAST;// 20
        this.player = AbstractDungeon.player;// 21
        this.playAmt = numberOfCards;// 22
    }// 23

    public void update() {
        if (this.duration == this.startDuration) {// 26
            if (this.player.discardPile.isEmpty()) {// 27
                this.isDone = true;// 28
            } else {
                CardGroup temp = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);// 31
                // 32

                for (AbstractCard c : this.player.discardPile.group) {
                    if(!(c instanceof Memoria))
                    temp.addToTop(c);// 33
                }

                temp.sortAlphabetically(true);// 35
                temp.sortByRarityPlusStatusCardType(false);// 36
                if(temp.size() < 1)
                {
                    isDone = true;
                    return;
                }
                AbstractDungeon.gridSelectScreen.open(temp, 1, cardStrings.EXTENDED_DESCRIPTION[0], false);// 37
                this.tickDuration();// 38
            }
        } else {
            if (!AbstractDungeon.gridSelectScreen.selectedCards.isEmpty()) {// 42
                // 43

                for (AbstractCard c : AbstractDungeon.gridSelectScreen.selectedCards) {
                    c.exhaust = true;// 44
                    AbstractDungeon.player.discardPile.group.remove(c);// 45
                    AbstractDungeon.getCurrRoom().souls.remove(c);// 46
                    this.addToBot(new NewQueueCardAction(c, true, false, true));// 50

                    for (int i = 0; i < this.playAmt - 1; ++i) {// 53
                        AbstractCard tmp = c.makeStatEquivalentCopy();// 54
                        tmp.purgeOnUse = true;// 55
                        this.addToBot(new NewQueueCardAction(tmp, true, false, true));// 56
                    }
                }

                AbstractDungeon.gridSelectScreen.selectedCards.clear();// 59
                AbstractDungeon.player.hand.refreshHandLayout();// 60
            }

            this.tickDuration();// 62
        }
    }// 29 39 63
}
