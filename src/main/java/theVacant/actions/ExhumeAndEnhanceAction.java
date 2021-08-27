package theVacant.actions;

import basemod.BaseMod;
import basemod.abstracts.AbstractCardModifier;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.utility.DrawPileToHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theVacant.cards.Modifiers.SoulforgedModifier;
import theVacant.cards.Modifiers.VoidboundModifier;

import java.util.ArrayList;

import static basemod.helpers.CardModifierManager.addModifier;
import static basemod.helpers.CardModifierManager.getModifiers;
import static com.megacrit.cardcrawl.characters.AbstractPlayer.uiStrings;

public class ExhumeAndEnhanceAction extends AbstractGameAction
{

    public static final String[] TEXT = uiStrings.TEXT;

    private float startingDuration;
    public int numCards;
    public String modifierID;
    public int amount;
    public boolean random;

    public ExhumeAndEnhanceAction(int numCards, String id, int amountToEnhance, boolean isRandom)
    {
        this.actionType = ActionType.CARD_MANIPULATION;
        this.startingDuration = Settings.ACTION_DUR_FAST;
        this.duration = this.startingDuration;
        this.numCards = numCards;
        this.modifierID = id;
        this.amount = amountToEnhance;
        this.random = isRandom;
    }

    public ExhumeAndEnhanceAction(int numCards, String id, int amountToEnhance)
    {
        this(numCards, id, amountToEnhance, false);
    }

    public ExhumeAndEnhanceAction(int numCards)
    {
        this(numCards, "", 0, false);
    }

    public void update()
    {
        CreateSelection();
        PickCards();
    }

    private void CreateSelection()
    {
        AbstractPlayer player = AbstractDungeon.player;
        if(player.exhaustPile.size() == 0)
        {
            this.isDone = true;
            return;
        }
        if(this.duration == this.startingDuration)
        {
            CardGroup tempGroup = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);
            for (AbstractCard card : player.exhaustPile.group)
            {
                tempGroup.addToRandomSpot(card);
            }
            if(tempGroup.size() == 0)
                this.isDone = true;
            else
            {
                if(tempGroup.size() <= this.amount || this.random)
                {
                    for(int i = 0; i < this.amount; i++)
                    {
                        AbstractCard card = tempGroup.getTopCard();
                        if(AbstractDungeon.player.hand.size() < BaseMod.MAX_HAND_SIZE)
                        {
                            card.unhover();
                            card.lighten(true);
                            card.unfadeOut();
                            card.setAngle(0.0F);
                            card.drawScale = 0.12F;
                            card.targetDrawScale = 0.75F;
                            card.current_x = CardGroup.DRAW_PILE_X;
                            card.current_y = CardGroup.DRAW_PILE_Y;
                            Enhance(card);
                            card.initializeDescription();
                            AbstractDungeon.player.exhaustPile.removeCard(card);
                            AbstractDungeon.player.hand.addToTop(card);
                            AbstractDungeon.player.hand.refreshHandLayout();
                            AbstractDungeon.player.hand.applyPowers();
                            tempGroup.removeCard(card);
                            if(tempGroup.size() == 0)
                                return;
                        }
                        else
                        {
                            AbstractDungeon.player.exhaustPile.moveToDiscardPile(card);
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
                card.unfadeOut();
                Enhance(card);
                card.initializeDescription();

                if (player.hand.size() == BaseMod.MAX_HAND_SIZE)
                {
                    player.exhaustPile.moveToDiscardPile(card);
                    player.createHandIsFullDialog();
                } else
                {
                    player.exhaustPile.removeCard(card);
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
