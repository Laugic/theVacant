package theVacant.cards.Skills;

import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theVacant.VacantMod;
import theVacant.actions.ChipOrbAction;
import theVacant.cards.AbstractDynamicCard;
import theVacant.characters.TheVacant;

import static theVacant.VacantMod.makeCardPath;

public class Shatter extends AbstractDynamicCard
{

    public static final String ID = VacantMod.makeID(Shatter.class.getSimpleName());
    public static final String IMG = makeCardPath("Shatter.png");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;

    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = TheVacant.Enums.COLOR_GOLD;


    private static final int COST = 3;

    public Shatter()
    {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        exhaust = true;
    }

    @Override
    public void use(AbstractPlayer player, AbstractMonster monster)
    {
        addToBot(new ChipOrbAction(9999));
        /*
        if(player.orbs.size() > 0)
        {
            for (AbstractOrb gem : player.orbs)
            {
                if(gem instanceof AbstractOrb)
                {
                    for (int i = 0; i < gem.passiveAmount; i++)
                        addToBot(new ChipAction(gem));
                }
            }
        }*/
    }

    @Override
    public void upgrade()
    {
        if (!upgraded)
        {
            upgradeName();
            upgradeBaseCost(2);
            upgradedCost = true;
            initializeDescription();
        }
    }
}