package theVacant.actions;

import basemod.BaseMod;
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

import static com.megacrit.cardcrawl.characters.AbstractPlayer.uiStrings;

public class AddAttacksToHandAction extends AbstractGameAction
{

    public static final String[] TEXT = uiStrings.TEXT;

    private float startingDuration;
    public int amount;
    public boolean random;

    public AddAttacksToHandAction(int amount, boolean random)
    {
        this.actionType = ActionType.CARD_MANIPULATION;
        this.startingDuration = Settings.ACTION_DUR_FAST;
        this.duration = this.startingDuration;
        this.random = random;
        this.amount = amount;
    }

    public void update()
    {
        CreateSelection();
        PickCards();
    }

    private void CreateSelection()
    {
        AbstractPlayer player = AbstractDungeon.player;
        if(this.duration == this.startingDuration)
        {
            CardGroup tempGroup = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);
            for (AbstractCard card : player.drawPile.group)
            {
                if (card.type == AbstractCard.CardType.ATTACK)
                    tempGroup.addToRandomSpot(card);
            }
            if(tempGroup.size() == 0)
                this.isDone = true;
            else
            {
                if(this.random || tempGroup.size() <= this.amount)
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
                            AbstractDungeon.player.drawPile.removeCard(card);
                            AbstractDungeon.player.hand.addToTop(card);
                            AbstractDungeon.player.hand.refreshHandLayout();
                            AbstractDungeon.player.hand.applyPowers();
                            card.setCostForTurn(0);
                            tempGroup.removeCard(card);
                            if(tempGroup.size() == 0)
                                return;
                        }
                        else
                        {
                            AbstractDungeon.player.drawPile.moveToDiscardPile(card);
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
                if (player.hand.size() == BaseMod.MAX_HAND_SIZE)
                {
                    player.drawPile.moveToDiscardPile(card);
                    player.createHandIsFullDialog();
                } else
                {
                    player.drawPile.removeCard(card);
                    player.hand.addToTop(card);
                    card.setCostForTurn(0);
                }
                player.hand.refreshHandLayout();
                player.hand.applyPowers();
            }
            AbstractDungeon.gridSelectScreen.selectedCards.clear();
            player.hand.refreshHandLayout();
            this.isDone = true;
        }
    }
}
