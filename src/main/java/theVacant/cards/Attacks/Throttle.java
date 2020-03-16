package theVacant.cards.Attacks;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.VulnerablePower;
import theVacant.VacantMod;
import theVacant.cards.AbstractDynamicCard;
import theVacant.characters.TheVacant;

import static theVacant.VacantMod.makeCardPath;

public class Throttle extends AbstractDynamicCard
{

    public static final String ID = VacantMod.makeID(Throttle.class.getSimpleName());
    public static final String IMG = makeCardPath("Attack.png");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);

    private static final CardRarity RARITY = CardRarity.COMMON;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final CardType TYPE = CardType.ATTACK;
    public static final CardColor COLOR = TheVacant.Enums.COLOR_GOLD;

    private static final int COST = 2;
    private static final int DAMAGE = 14;
    private static final int UPGRADE_PLUS_DMG = 4;
    private static final int VULNERABLE_NUM = 3;

    public Throttle()
    {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        this.magicNumber = this.baseMagicNumber = 4;
        this.damage = this.baseDamage = DAMAGE;
    }

    @Override
    public void use(AbstractPlayer player, AbstractMonster monster)
    {
        AbstractDungeon.actionManager.addToBottom(new DamageAction(monster, new DamageInfo(player, this.damage, damageTypeForTurn), AbstractGameAction.AttackEffect.BLUNT_LIGHT));

        if(GetWill() > this.magicNumber)
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(monster, player, new VulnerablePower(monster, VULNERABLE_NUM, false), VULNERABLE_NUM));
    }

    @Override
    public void upgrade()
    {
        if (!upgraded)
        {
            upgradeName();
            upgradeDamage(UPGRADE_PLUS_DMG);
            upgradeMagicNumber(-1);
            upgradedMagicNumber = true;
            initializeDescription();
        }
    }
    @Override
    public void applyPowers()
    {
        this.magicNumber = this.baseMagicNumber = GetFractureThreshold();
        this.magicNumber -= (this.upgraded?1:0);
        this.isMagicNumberModified = true;
        super.applyPowers();
    }
    @Override
    public void atTurnStart()
    {
        this.magicNumber = this.baseMagicNumber = GetFractureThreshold();
        this.magicNumber -= (this.upgraded?1:0);
        this.isMagicNumberModified = true;
        super.atTurnStart();
    }
}