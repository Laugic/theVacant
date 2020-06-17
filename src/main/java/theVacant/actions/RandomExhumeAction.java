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
import theVacant.cards.Modifiers.VoidboundModifier;

import java.util.ArrayList;

import static basemod.helpers.CardModifierManager.addModifier;
import static basemod.helpers.CardModifierManager.getModifiers;
import static com.megacrit.cardcrawl.characters.AbstractPlayer.uiStrings;

public class RandomExhumeAction extends AbstractGameAction
{

    public static final String[] TEXT = uiStrings.TEXT;

    private float startingDuration;
    public int amount;

    public RandomExhumeAction(int amount)
    {
        this.actionType = ActionType.CARD_MANIPULATION;
        this.startingDuration = Settings.ACTION_DUR_FAST;
        this.duration = this.startingDuration;
        this.amount = amount;
    }

    public void update()
    {
        AbstractPlayer player = AbstractDungeon.player;
        for(int i = 0; i < this.amount; i++)
        {
            AbstractCard card = null;
            if(AbstractDungeon.player.exhaustPile.size() > 0)
                card = player.exhaustPile.getNCardFromTop(AbstractDungeon.cardRandomRng.random(0, player.exhaustPile.size()-1));
            if(card != null)
            {
                if(AbstractDungeon.player.hand.size() < BaseMod.MAX_HAND_SIZE)
                {
                    card.setAngle(0.0F);
                    card.drawScale = 0.12F;
                    card.targetDrawScale = 0.75F;
                    card.current_x = CardGroup.DRAW_PILE_X;
                    card.current_y = CardGroup.DRAW_PILE_Y;
                    card.initializeDescription();
                    AbstractDungeon.player.exhaustPile.removeCard(card);
                    AbstractDungeon.player.hand.addToTop(card);
                    AbstractDungeon.player.hand.refreshHandLayout();
                    AbstractDungeon.player.hand.applyPowers();
                    card.unhover();
                    card.fadingOut = false;
                }
                else
                {
                    AbstractDungeon.player.exhaustPile.moveToDiscardPile(card);
                    AbstractDungeon.player.createHandIsFullDialog();
                }
            }
        }
        this.isDone = true;
    }

}
