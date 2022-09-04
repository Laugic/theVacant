package theVacant.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.orbs.AbstractOrb;
import theVacant.cards.Modifiers.*;
import theVacant.cards.Skills.Enchant;
import theVacant.orbs.*;

import static com.megacrit.cardcrawl.characters.AbstractPlayer.uiStrings;

public class EnchantAction extends AbstractGameAction
{

    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(Enchant.ID);

    AbstractPlayer player;
    AbstractGemOrb nextOrb;

    public EnchantAction()
    {
        this.actionType = ActionType.CARD_MANIPULATION;

        startDuration = Settings.ACTION_DUR_FAST;
        duration = startDuration;
        player = AbstractDungeon.player;
        nextOrb= getFirstGem();
    }

    public void update()
    {
        if (this.duration == this.startDuration)
        {
            nextOrb= getFirstGem();
            if (nextOrb == null)
            {
                this.isDone = true;
                return;
            }
            addToTop(new ReduceOrbSizeAction(nextOrb, nextOrb.passiveAmount));
            if (player.hand.size() == 0)
            {
                this.isDone = true;
                return;
            }
            String displayString = cardStrings.NAME + cardStrings.EXTENDED_DESCRIPTION[0] + nextOrb.name;
            AbstractDungeon.handCardSelectScreen.open(displayString, 1, false, false, false, false, true);
            tickDuration();
            return;
        }
        if (!AbstractDungeon.handCardSelectScreen.wereCardsRetrieved)
        {
            for (AbstractCard c : AbstractDungeon.handCardSelectScreen.selectedCards.group)
            {
                enchantCard(c, nextOrb);
                AbstractDungeon.player.hand.addToTop(c);
            }
            AbstractDungeon.handCardSelectScreen.wereCardsRetrieved = true;
        }
        tickDuration();
    }

    private static AbstractGemOrb getFirstGem()
    {
        for (AbstractOrb o : AbstractDungeon.player.orbs)
        {
            if (o instanceof AbstractGemOrb && o.passiveAmount > 0)
                return (AbstractGemOrb) o;
        }
        return null;
    }

    private void enchantCard(AbstractCard card, AbstractGemOrb gem)
    {
        if(gem instanceof OpalOrb)
            OpalModifier.Enhance(card, 1);
        if(gem instanceof EmeraldOrb)
            EmeraldModifier.Enhance(card, 1);
        if(gem instanceof RubyOrb)
            RubyModifier.Enhance(card, 3);
        if(gem instanceof SapphireOrb)
            SapphireModifier.Enhance(card, 3);
        if(gem instanceof OnyxOrb)
            OnyxModifier.Enhance(card, 1);
        if(gem instanceof AmethystOrb)
            AmethystModifier.Enhance(card, 3);
        if(gem instanceof DiamondOrb)
            DiamondModifier.Enhance(card, 1);
        if(gem instanceof TopazOrb)
            TopazModifier.Enhance(card, 1);
        card.initializeDescription();
    }
}
