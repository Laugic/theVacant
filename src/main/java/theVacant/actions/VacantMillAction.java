package theVacant.actions;

import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.vfx.BorderLongFlashEffect;
import com.megacrit.cardcrawl.vfx.combat.InflameEffect;
import theVacant.cards.AbstractDynamicCard;
import theVacant.characters.TheVacant;
import theVacant.powers.CleanseSoulPower;
import theVacant.powers.GreaterMindPower;
import theVacant.powers.VoidPower;
import theVacant.powers.WillPower;

public class VacantMillAction  extends AbstractGameAction
{
    private float startingDuration;
    private int startAmount = 0;
    private int voidAmount;
    public VacantMillAction(int numCards)
    {
        this.amount = numCards;
        this.voidAmount = 0;
        this.startAmount = this.amount;
        this.actionType = ActionType.CARD_MANIPULATION;
        this.startingDuration = Settings.ACTION_DUR_FAST;
        this.duration = this.startingDuration;
    }
    public void update()
    {
        if(this.amount > 0)
        {
            PreMill();
            while (this.amount > 0)
            {
                if (AbstractDungeon.player.drawPile.size() > 0)
                    ProcessMill();
                this.amount--;
            }
            PostMill();
            this.isDone = true;
        }
    }

    private void PreMill()
    {

    }

    private void ProcessMill()
    {
        AbstractCard card = AbstractDungeon.player.drawPile.getTopCard();
        if(card != null)
        {
            if(card instanceof AbstractDynamicCard)
            {
                if(((AbstractDynamicCard)card).rebound || GetSpecialRebound(card))
                {
                    AbstractDungeon.player.drawPile.moveToHand(card, AbstractDungeon.player.drawPile);
                    if(((AbstractDynamicCard) card).postMillAction)
                        ((AbstractDynamicCard) card).PostMillAction();
                    ProcessPostMill(card);
                    return;
                }
                AbstractDungeon.player.drawPile.moveToDiscardPile(AbstractDungeon.player.drawPile.getTopCard());
                if(((AbstractDynamicCard) card).postMillAction)
                    ((AbstractDynamicCard) card).PostMillAction();
                ProcessPostMill(card);
                return;
            }
            AbstractDungeon.player.drawPile.moveToDiscardPile(AbstractDungeon.player.drawPile.getTopCard());
            ProcessPostMill(card);
            return;
        }
    }

    private void PostMill()
    {
        GainVoid();
    }

    private void GainVoid()
    {
        AbstractPlayer player = AbstractDungeon.player;
        if(player != null && this.voidAmount > 0)
        {
            addToTop(new VFXAction(player, new InflameEffect(player), .1F));
            player.addPower(new VoidPower(player, player, this.voidAmount));
        }
    }

    private void ProcessPostMill(AbstractCard card)
    {
        PostMillCard(card);
        PostMillVoidGain();
    }

    private void PostMillCard(AbstractCard card)
    {
        AbstractPlayer player = AbstractDungeon.player;
        if(player != null)
        {
            if(player.hasPower(CleanseSoulPower.POWER_ID))
            {
                if(card.type == AbstractCard.CardType.STATUS || card.type == AbstractCard.CardType.CURSE)
                {
                    this.actionType = ActionType.EXHAUST;
                    if(player.discardPile.contains(card))
                        player.discardPile.moveToExhaustPile(card);
                }
            }
        }
        if(player instanceof TheVacant)
            ((TheVacant)player).millsThisTurn++;
    }

    private void PostMillVoidGain()
    {
        this.voidAmount++;
    }

    private boolean GetSpecialRebound(AbstractCard card)
    {
        AbstractPlayer player = AbstractDungeon.player;
        if(player != null)
        {
            if(player.hasPower(GreaterMindPower.POWER_ID) && (card.rarity == AbstractCard.CardRarity.COMMON || card.rarity == AbstractCard.CardRarity.BASIC))
                return true;
        }
        return false;
    }
}
