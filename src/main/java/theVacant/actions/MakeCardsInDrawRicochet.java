package theVacant.actions;

import basemod.abstracts.AbstractCardModifier;
import basemod.helpers.CardModifierManager;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.GetAllInBattleInstances;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardBrieflyEffect;
import theVacant.cards.Modifiers.RicochetMod;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import static theVacant.VacantMod.makeID;

public class MakeCardsInDrawRicochet extends AbstractGameAction {
    public static final String ID = makeID("Enhance");
    public static String[] TEXT = CardCrawlGame.languagePack.getUIString(ID).TEXT;

    public MakeCardsInDrawRicochet(int numCards) {
        amount = numCards;
        startDuration = Settings.ACTION_DUR_FAST;
        duration = startDuration;
    }
    public void update() {
        if (this.duration == this.startDuration) {
            if (!AbstractDungeon.player.drawPile.isEmpty()) {
                if (AbstractDungeon.player.drawPile.size() <= amount) {
                    for (AbstractCard c : AbstractDungeon.player.drawPile.group) {
                        CardModifierManager.addModifier(c, new RicochetMod());
                        c.initializeDescription();
                        AbstractDungeon.effectList.add(new ShowCardBrieflyEffect(c.makeStatEquivalentCopy(), Settings.WIDTH / 2.0f, Settings.HEIGHT / 2.0f));
                    }

                    this.isDone = true;
                } else {
                    CardGroup temp = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);

                    for (AbstractCard c : AbstractDungeon.player.drawPile.group) {
                        temp.addToTop(c);
                    }

                    temp.sortAlphabetically(true);
                    temp.sortByRarityPlusStatusCardType(false);
                    AbstractDungeon.gridSelectScreen.open(temp, amount, TEXT[0], false);

                    this.tickDuration();
                }
            } else {
                this.isDone = true;
            }
        } else {
            if (!AbstractDungeon.gridSelectScreen.selectedCards.isEmpty()) {
                for (AbstractCard c : AbstractDungeon.gridSelectScreen.selectedCards) {
                    CardModifierManager.addModifier(c, new RicochetMod());
                    c.applyPowers();
                    c.initializeDescription();
                    AbstractDungeon.effectList.add(new ShowCardBrieflyEffect(c.makeStatEquivalentCopy(), Settings.WIDTH / 2.0f, Settings.HEIGHT / 2.0f));
                }

                AbstractDungeon.gridSelectScreen.selectedCards.clear();
                AbstractDungeon.player.hand.refreshHandLayout();
            }

            this.tickDuration();
        }
    }
}
