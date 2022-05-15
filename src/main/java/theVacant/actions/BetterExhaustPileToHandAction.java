package theVacant.actions;

import basemod.BaseMod;
import basemod.ReflectionHacks;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.AbstractGameAction.ActionType;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.cards.CardGroup.CardGroupType;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;
import com.megacrit.cardcrawl.vfx.cardManip.ExhaustCardEffect;

import java.util.ArrayList;
import java.util.Iterator;

public class BetterExhaustPileToHandAction extends AbstractGameAction //COPIED FROM VANILLA!
{
    public static final String[] TEXT;
    private AbstractPlayer player;
    private int numberOfCards;
    private boolean optional;

    public BetterExhaustPileToHandAction(int numberOfCards, boolean optional)
    {
        this.actionType = ActionType.CARD_MANIPULATION;
        this.duration = this.startDuration = Settings.ACTION_DUR_FAST;
        this.player = AbstractDungeon.player;
        this.numberOfCards = numberOfCards;
        this.optional = optional;
    }

    public BetterExhaustPileToHandAction(int numberOfCards)
    {
        this(numberOfCards, false);
    }

    public void update()
    {
        if (this.duration == this.startDuration)
        {
            if (!this.player.exhaustPile.isEmpty() && this.numberOfCards > 0)
            {
                AbstractCard card;
                Iterator counter;
                if (this.player.exhaustPile.size() <= this.numberOfCards && !this.optional)
                {
                    ArrayList<AbstractCard> cardsToMove = new ArrayList();
                    counter = this.player.exhaustPile.group.iterator();

                    while (counter.hasNext())
                    {
                        card = (AbstractCard) counter.next();
                        card.unhover();
                        card.unfadeOut();
                        card.lighten(true);
                        card.setAngle(0.0F);
                        cardsToMove.add(card);
                    }

                    counter = cardsToMove.iterator();

                    while (counter.hasNext())
                    {
                        card = (AbstractCard) counter.next();

                        if (this.player.hand.size() >= BaseMod.MAX_HAND_SIZE)
                        {
                            if(player.exhaustPile.contains(card))
                            {
                                player.exhaustPile.moveToDiscardPile(card);
                                player.exhaustPile.group.remove(card);
                                AbstractGameEffect e = null;
                                for (AbstractGameEffect effect: AbstractDungeon.effectList)
                                {
                                    if(effect instanceof ExhaustCardEffect)
                                    {
                                        AbstractCard c = ReflectionHacks.getPrivate(effect, ExhaustCardEffect.class, "c");
                                        if(c == card)
                                            e = effect;
                                    }
                                }
                                AbstractDungeon.effectList.remove(e);
                            }
                            if(player.limbo.contains(card))
                            {
                                player.limbo.moveToDiscardPile(card);
                                player.limbo.removeCard(card);
                                AbstractGameEffect e = null;
                                for (AbstractGameEffect effect: AbstractDungeon.effectList)
                                {
                                    if(effect instanceof ExhaustCardEffect)
                                    {
                                        AbstractCard c = ReflectionHacks.getPrivate(effect, ExhaustCardEffect.class, "c");
                                        if(c == card)
                                            e = effect;
                                    }
                                }
                                AbstractDungeon.effectList.remove(e);
                            }
                            player.createHandIsFullDialog();
                            card.unhover();
                            card.unfadeOut();
                            card.lighten(true);
                            card.setAngle(0.0F);
                            card.fadingOut = false;
                        }
                        else
                        {
                            card.unhover();
                            card.unfadeOut();
                            card.lighten(true);
                            card.setAngle(0.0F);
                            card.fadingOut = false;
                            this.player.exhaustPile.moveToHand(card, this.player.exhaustPile);
                        }
                    }

                    this.isDone = true;
                } else
                {
                    CardGroup temp = new CardGroup(CardGroupType.UNSPECIFIED);
                    counter = this.player.exhaustPile.group.iterator();

                    while (counter.hasNext())
                    {
                        card = (AbstractCard) counter.next();
                        temp.addToTop(card);
                        card.unhover();
                        card.unfadeOut();
                        card.lighten(true);
                        card.setAngle(0.0F);
                    }

                    temp.sortAlphabetically(true);
                    temp.sortByRarityPlusStatusCardType(false);
                    if (this.numberOfCards == 1)
                    {
                        if (this.optional)
                        {
                            AbstractDungeon.gridSelectScreen.open(temp, this.numberOfCards, true, TEXT[0]);
                        } else
                        {
                            AbstractDungeon.gridSelectScreen.open(temp, this.numberOfCards, TEXT[0], false);
                        }
                    }
                    else if (this.optional)
                    {
                        AbstractDungeon.gridSelectScreen.open(temp, this.numberOfCards, true, TEXT[1] + this.numberOfCards + TEXT[2]);
                    }
                    else
                    {
                        AbstractDungeon.gridSelectScreen.open(temp, this.numberOfCards, TEXT[1] + this.numberOfCards + TEXT[2], false);
                    }

                    this.tickDuration();
                }
            } else
            {
                this.isDone = true;
            }
        } else
        {
            if (!AbstractDungeon.gridSelectScreen.selectedCards.isEmpty())
            {
                Iterator iterator = AbstractDungeon.gridSelectScreen.selectedCards.iterator();

                while (iterator.hasNext())
                {
                    AbstractCard card = (AbstractCard) iterator.next();
                    card.unhover();
                    card.lighten(true);
                    card.unfadeOut();
                    card.setAngle(0.0F);
                    if (this.player.hand.size() == 10)
                    {
                        this.player.exhaustPile.moveToDiscardPile(card);
                        this.player.createHandIsFullDialog();

                    } else
                    {
                        this.player.exhaustPile.moveToHand(card, this.player.exhaustPile);
                    }
                }

                AbstractDungeon.gridSelectScreen.selectedCards.clear();
                AbstractDungeon.player.hand.refreshHandLayout();
            }

            this.tickDuration();
        }
    }// 36 51 81 97

    static
    {
        TEXT = CardCrawlGame.languagePack.getUIString("BetterToHandAction").TEXT;// 15
    }
}
