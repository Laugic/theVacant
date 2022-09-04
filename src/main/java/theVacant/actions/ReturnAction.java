package theVacant.actions;

import basemod.BaseMod;
import basemod.ReflectionHacks;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;
import com.megacrit.cardcrawl.vfx.cardManip.ExhaustCardEffect;
import theVacant.vfx.ShowCardAndMillEffect;

import static com.megacrit.cardcrawl.characters.AbstractPlayer.uiStrings;

public class ReturnAction extends AbstractGameAction
{

    public static final String[] TEXT = uiStrings.TEXT;

    private float startingDuration;
    public int amount;
    public boolean random;
    private AbstractCard ignoredCard;

    public ReturnAction(int amount){
        this(amount, null);
    }

    public ReturnAction(int amount, AbstractCard ignoreCard)
    {
        this.actionType = ActionType.CARD_MANIPULATION;
        this.startingDuration = Settings.ACTION_DUR_FAST;
        this.duration = this.startingDuration;
        this.amount = amount;
        ignoredCard = ignoreCard;
    }

    public void update()
    {
        if(AbstractDungeon.player != null)
        {
            while(AbstractDungeon.player.hand.size() < BaseMod.MAX_HAND_SIZE && AbstractDungeon.player.discardPile.size() > 0 && this.amount > 0)
            {
                AbstractCard card = topPlayable(AbstractDungeon.player.discardPile);
                if(card != null)
                {
                    AbstractDungeon.player.discardPile.removeCard(card);
                    addToBot(new VFXAction(AbstractDungeon.player, new ShowCardAndMillEffect(card, AbstractDungeon.player.hand, AbstractDungeon.player.discardPile), Settings.ACTION_DUR_XFAST, true));
                }
                amount--;
            }
        }
        this.isDone = true;
    }

    private AbstractCard topPlayable(CardGroup cardGroup)
    {
        AbstractCard card = null;
        if(cardGroup.group.size() > 0)
        {
            int i = 1;
            while(i <= cardGroup.group.size())
            {
                if(cardGroup.group.get(cardGroup.group.size() - i).cost > -2 && (ignoredCard == null || !cardGroup.group.get(cardGroup.group.size() - i).equals(ignoredCard)))
                {
                    card = cardGroup.group.get(cardGroup.group.size() - i);
                    i = cardGroup.group.size();
                }
                i++;
            }
        }
        return card;
    }
}
