package theVacant.cards.Attacks;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import theVacant.VacantMod;
import theVacant.actions.IncreaseMagicNumberAction;
import theVacant.cards.AbstractDynamicCard;
import theVacant.characters.TheVacant;

import static theVacant.VacantMod.makeCardPath;

public class Barrage extends AbstractDynamicCard {

    public static final String ID = VacantMod.makeID(Barrage.class.getSimpleName());
    public static final String IMG = makeCardPath("Barrage.png");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);

    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final CardType TYPE = CardType.ATTACK;
    public static final CardColor COLOR = TheVacant.Enums.COLOR_GOLD;

    private static final int COST = 1;
    private static final int DAMAGE = 4;

    //public Barrage(int upgrades)
    public Barrage()
    {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        damage = baseDamage = DAMAGE;
        magicNumber = baseMagicNumber = 2;
        //this.timesUpgraded = upgrades;
    }


    @Override
    public void use(AbstractPlayer player, AbstractMonster monster)
    {
        for(int i = 0; i < GetTotalBuffs(player); i++)
            addToBot( new DamageAction(monster, new DamageInfo(player, damage, damageTypeForTurn), AbstractGameAction.AttackEffect.BLUNT_LIGHT));
        //addToBot(new IncreaseMagicNumberAction(this, 1));
    }

    private int GetTotalBuffs(AbstractPlayer player)
    {
        int numBuffs = 0;
        for (AbstractPower power: player.powers)
        {
            if(upgraded || power.type == AbstractPower.PowerType.BUFF)
                numBuffs++;
        }
        return numBuffs;
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
        upgradeDamage(1);
        upgradedDamage = true;
        rawDescription = cardStrings.UPGRADE_DESCRIPTION;
        initializeDescription();
    }

    @Override
    public AbstractCard makeCopy()
    {
        return new Barrage();
    }
}