package theVacant.cards;

import static com.megacrit.cardcrawl.core.CardCrawlGame.languagePack;

public abstract class AbstractDynamicCard extends AbstractVacantCard
{
    public AbstractDynamicCard(final String ID,
                               final String img,
                               final int cost,
                               final CardType type,
                               final CardColor color,
                               final CardRarity rarity,
                               final CardTarget target) {

        super(ID, languagePack.getCardStrings(ID).NAME, img, cost, languagePack.getCardStrings(ID).DESCRIPTION, type, color, rarity, target);
    }
}
