package theVacant.cards.Attacks;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theVacant.VacantMod;
import theVacant.cards.AbstractDynamicCard;
import theVacant.characters.TheVacant;

import static theVacant.VacantMod.makeCardPath;

public class SoulBarrage extends AbstractDynamicCard {

    public static final String ID = VacantMod.makeID(SoulBarrage.class.getSimpleName());
    public static final String IMG = makeCardPath("SoulBarrage.png");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);

    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final CardType TYPE = CardType.ATTACK;
    public static final CardColor COLOR = TheVacant.Enums.COLOR_GOLD;

    private static final int COST = 2, DAMAGE = 4, STARTING_MAGIC = 3;

    public SoulBarrage()
    {
        this(0);
    }
    public SoulBarrage(int upgrades)
    {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        damage = baseDamage = DAMAGE;
        magicNumber = baseMagicNumber = STARTING_MAGIC;
        timesUpgraded = upgrades;
    }

    @Override
    public void use(AbstractPlayer player, AbstractMonster monster)
    {
        for(int i = 0; i < magicNumber; i++)
            addToBot( new DamageAction(monster, new DamageInfo(player, damage, damageTypeForTurn), AbstractGameAction.AttackEffect.BLUNT_LIGHT));
    }

    @Override
    public boolean canUpgrade()
    {
        return true;
    }

    @Override
    public void upgrade()
    {
        upgradeName();
        upgradeMagicNumber(1);
        upgradedMagicNumber = true;
        upgraded = true;
        name = cardStrings.NAME + "+" + (timesUpgraded);
        initializeDescription();
    }

    @Override
    public AbstractCard makeCopy()
    {
        return new SoulBarrage(timesUpgraded);
    }
}