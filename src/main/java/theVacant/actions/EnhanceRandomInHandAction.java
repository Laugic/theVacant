package theVacant.actions;

import basemod.BaseMod;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import theVacant.cards.Modifiers.SoulforgedModifier;
import theVacant.cards.Modifiers.VoidboundModifier;

import static com.megacrit.cardcrawl.characters.AbstractPlayer.uiStrings;

public class EnhanceRandomInHandAction extends AbstractGameAction
{

    public static final String[] TEXT = uiStrings.TEXT;

    private float startingDuration;
    public int numCards;
    public String modifierID;
    public int amount;

    public EnhanceRandomInHandAction(int numCards, String id, int amountToEnhance)
    {
        this.actionType = ActionType.CARD_MANIPULATION;
        this.startingDuration = Settings.ACTION_DUR_FAST;
        this.duration = this.startingDuration;
        this.modifierID = id;
        this.numCards = numCards;
        this.amount = amountToEnhance;
    }

    public void update()
    {
        PickCards();
    }

    private void PickCards()
    {
        AbstractPlayer player = AbstractDungeon.player;
        if(AbstractDungeon.player.hand.size() == 0)
        {
            this.isDone = true;
            return;
        }
        for(int i = 0; i < this.numCards; i++)
        {
            Enhance(player.hand.getNCardFromTop(AbstractDungeon.cardRandomRng.random(0, player.hand.size()-1)));
        }
        this.isDone = true;
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
