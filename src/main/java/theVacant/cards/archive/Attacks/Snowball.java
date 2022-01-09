package theVacant.cards.archive.Attacks;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theVacant.VacantMod;
import theVacant.cards.AbstractDynamicCard;
import theVacant.cards.Attacks.Barrage;
import theVacant.characters.TheVacant;

import static theVacant.VacantMod.makeCardPath;

public class Snowball extends AbstractDynamicCard {

    public static final String ID = VacantMod.makeID(Snowball.class.getSimpleName());
    public static final String IMG = makeCardPath("Attack.png");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);

    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final CardType TYPE = CardType.ATTACK;
    public static final CardColor COLOR = TheVacant.Enums.COLOR_GOLD;

    private static final int COST = 1;
    private static final int DAMAGE = 4;
    private static final int UPGRADE_PLUS_DMG = 1;

    public Snowball()
    {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        damage = baseDamage = DAMAGE;
        postMillAction = true;
        rebound = true;
    }


    public Snowball(int dmgMult)
    {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        damage = baseDamage = dmgMult;
        postMillAction = true;
        rebound = true;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m)
    {
        AbstractDungeon.actionManager.addToBottom( new DamageAction(m, new DamageInfo(p, damage, damageTypeForTurn), AbstractGameAction.AttackEffect.BLUNT_LIGHT));
    }

    @Override
    public void PostMillAction()
    {
        baseDamage *= 2;
    }

    @Override
    public void upgrade()
    {
        if (!upgraded)
        {
            upgradeName();
            upgradeDamage(UPGRADE_PLUS_DMG);
            isUnnate = true;
            isHeavy = true;
            rawDescription = cardStrings.UPGRADE_DESCRIPTION;
            initializeDescription();
        }
    }

    @Override
    public void applyPowers()
    {
        getDamage();
        super.applyPowers();
    }
    @Override
    public void atTurnStart()
    {
        getDamage();
        super.atTurnStart();
    }
    @Override
    public void calculateCardDamage(AbstractMonster monster)
    {
        getDamage();
        super.calculateCardDamage(monster);
    }

    private void getDamage()
    {
        this.damage = this.baseDamage;
    }

    @Override
    public AbstractCard makeCopy()
    {
        return new Snowball(baseDamage);
    }
}