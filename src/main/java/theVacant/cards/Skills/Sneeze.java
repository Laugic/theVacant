package theVacant.cards.Skills;

import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theVacant.VacantMod;
import theVacant.actions.VacantMillAction;
import theVacant.cards.AbstractDynamicCard;
import theVacant.characters.TheVacant;

import static theVacant.VacantMod.makeCardPath;

public class Sneeze extends AbstractDynamicCard
{

    public static final String ID = VacantMod.makeID(Sneeze.class.getSimpleName());
    public static final String IMG = makeCardPath("Sneeze.png");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);

    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = TheVacant.Enums.COLOR_GOLD;

    private static final int COST = 0, STARTMILL = 4;
    private int retainBonus = 0;
    public Sneeze()
    {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        magicNumber = baseMagicNumber = STARTMILL;
        selfRetain = true;
        exhaust = true;
        getBonusMillToMagic = true;
        retainBonus = 0;
    }

    @Override
    public void use(AbstractPlayer player, AbstractMonster m)
    {
        AbstractDungeon.actionManager.addToBottom(new VacantMillAction(magicNumber));
        magicNumber = baseMagicNumber;
    }

    @Override
    public void onRetained()
    {
        retainBonus += 2;
        super.onRetained();
    }
    @Override
    public void applyPowers()
    {
        magicNumber = baseMagicNumber = STARTMILL + retainBonus;
        isMagicNumberModified = (retainBonus > 0);
        super.applyPowers();
    }

    @Override
    public void atTurnStart()
    {
        magicNumber = baseMagicNumber = STARTMILL + retainBonus;
        isMagicNumberModified = (retainBonus > 0);
        super.atTurnStart();
    }
    @Override
    public void upgrade()
    {
        if (!upgraded)
        {
            upgradeName();
            isInnate = true;
            upgradeMagicNumber(2);
            upgradedMagicNumber = true;
            rawDescription = cardStrings.UPGRADE_DESCRIPTION;
            initializeDescription();
        }
    }
}