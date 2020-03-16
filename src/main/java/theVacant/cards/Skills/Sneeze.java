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
    public static final String IMG = makeCardPath("Skill.png");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);

    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = TheVacant.Enums.COLOR_GOLD;

    private static final int COST = 1;
    private int retainBonus = 0;

    public Sneeze()
    {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        this.magicNumber = this.baseMagicNumber = 3;
        this.selfRetain = true;
        this.exhaust = true;
        this.getBonusMillToMagic = true;
        this.retainBonus = 0;
    }

    @Override
    public void use(AbstractPlayer player, AbstractMonster m)
    {
        AbstractDungeon.actionManager.addToBottom(new VacantMillAction(this.magicNumber));
        this.magicNumber = this.baseMagicNumber;
    }

    @Override
    public void onRetained()
    {
        this.retainBonus++;
        super.onRetained();
    }
    @Override
    public void applyPowers()
    {
        this.magicNumber = this.baseMagicNumber = this.upgraded?4+this.retainBonus:3+this.retainBonus;
        this.isMagicNumberModified = true;
        super.applyPowers();
    }
    @Override
    public void atTurnStart()
    {
        this.magicNumber = this.baseMagicNumber = this.upgraded?4+this.retainBonus:3+this.retainBonus;
        this.isMagicNumberModified = true;
        super.atTurnStart();
    }
    @Override
    public void upgrade()
    {
        if (!upgraded)
        {
            upgradeName();
            upgradeMagicNumber(1);
            upgradeBaseCost(0);
            initializeDescription();
        }
    }
}