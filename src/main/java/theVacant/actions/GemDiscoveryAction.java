package theVacant.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.screens.CardRewardScreen;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndAddToDiscardEffect;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndAddToHandEffect;
import theVacant.Enums.VacantTags;

import java.util.ArrayList;
import java.util.List;

public class GemDiscoveryAction extends AbstractGameAction
{
    private boolean retrieveCard = false;

    public GemDiscoveryAction()
    {
        actionType = AbstractGameAction.ActionType.CARD_MANIPULATION;
        duration = Settings.ACTION_DUR_FAST;
    }

    public void update()
    {
        if (this.duration == Settings.ACTION_DUR_FAST)
        {
            AbstractDungeon.cardRewardScreen.customCombatOpen(GenerateGemChoice(), CardRewardScreen.TEXT[1], false);
            tickDuration();
            return;
        }
        if (!this.retrieveCard)
        {
            if (AbstractDungeon.cardRewardScreen.discoveryCard != null) {
                AbstractCard disCard = AbstractDungeon.cardRewardScreen.discoveryCard.makeStatEquivalentCopy();
                /*if (this.upgraded)
                    disCard.setCostForTurn(0);*/
                disCard.current_x = -1000.0F * Settings.CARD_VIEW_PAD_X;
                if (AbstractDungeon.player.hand.size() < 10)
                    AbstractDungeon.effectList.add(new ShowCardAndAddToHandEffect(disCard, Settings.WIDTH / 2.0F, Settings.HEIGHT / 2.0F));
                else
                    AbstractDungeon.effectList.add(new ShowCardAndAddToDiscardEffect(disCard, Settings.WIDTH / 2.0F, Settings.HEIGHT / 2.0F));
                AbstractDungeon.cardRewardScreen.discoveryCard = null;
            }
            this.retrieveCard = true;
        }
        tickDuration();
    }

    private ArrayList<AbstractCard> GenerateGemChoice()
    {
        ArrayList<AbstractCard> derp = new ArrayList<>();
        while (derp.size() != 3)
        {
            boolean dupe = false;
            AbstractCard tmp = VacantTags.GEMS.get(AbstractDungeon.cardRandomRng.random(VacantTags.GEMS.size()));
            for (AbstractCard c : derp)
            {
                if (c.cardID.equals(tmp.cardID)) {
                    dupe = true;
                    break;
                }
            }
            if (!dupe)
                derp.add(tmp.makeCopy());
        }
        return derp;
    }
}
