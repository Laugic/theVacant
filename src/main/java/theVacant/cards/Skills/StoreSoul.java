package theVacant.cards.Skills;

import com.megacrit.cardcrawl.actions.common.LoseHPAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import theVacant.VacantMod;
import theVacant.cards.AbstractDynamicCard;
import theVacant.characters.TheVacant;
import theVacant.relics.BrassGoblet;
import theVacant.relics.OverflowingGobletRelic;

import static theVacant.VacantMod.makeCardPath;

public class StoreSoul extends AbstractDynamicCard
{

    public static final String ID = VacantMod.makeID(StoreSoul.class.getSimpleName());
    public static final String IMG = makeCardPath("StoreSoul.png");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);

    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = TheVacant.Enums.COLOR_GOLD;

    private static final int COST = 3;

    public StoreSoul()
    {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        magicNumber = baseMagicNumber = 6;
        exhaust = true;
        tags.add(CardTags.HEALING);
    }

    @Override
    public void use(AbstractPlayer player, AbstractMonster m)
    {
        if(!upgraded)
            addToBot(new LoseHPAction(player, player, magicNumber));
        for (AbstractRelic relic : player.relics)
        {
            if(relic instanceof  BrassGoblet)
                ((BrassGoblet)relic).IncreaseCounter(1);
            if(relic instanceof OverflowingGobletRelic)
            {
                relic.counter++;
                ((OverflowingGobletRelic)relic).getUpdatedDescription();
            }
        }
    }

    @Override
    public void upgrade()
    {
        if (!upgraded)
        {
            upgradeName();
            rawDescription = cardStrings.UPGRADE_DESCRIPTION;
            initializeDescription();
        }
    }
}