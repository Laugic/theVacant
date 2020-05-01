package theVacant.cards.Attacks;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theVacant.VacantMod;
import theVacant.actions.VacantMillAction;
import theVacant.cards.AbstractDynamicCard;
import theVacant.characters.TheVacant;

import static theVacant.VacantMod.makeCardPath;

public class Whip extends AbstractDynamicCard {

    public static final String ID = VacantMod.makeID(Whip.class.getSimpleName());
    public static final String IMG = makeCardPath("Attack.png");

    private static final CardRarity RARITY = CardRarity.COMMON;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final CardType TYPE = CardType.ATTACK;
    public static final CardColor COLOR = TheVacant.Enums.COLOR_GOLD;

    private static final int COST = 0;
    private static final int DAMAGE = 3;
    private static final int UPGRADE_PLUS_DMG = 2;

    public Whip()
    {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        this.baseDamage = DAMAGE;
        this.magicNumber = this.baseMagicNumber = 2;
        this.getBonusMillToMagic = true;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m)
    {
        AbstractDungeon.actionManager.addToBottom( new DamageAction(m, new DamageInfo(p, damage, damageTypeForTurn), AbstractGameAction.AttackEffect.BLUNT_LIGHT));
        AbstractDungeon.actionManager.addToBottom(new VacantMillAction(this.magicNumber));
    }

    @Override
    public void upgrade()
    {
        if (!upgraded)
        {
            upgradeName();
            //upgradeDamage(UPGRADE_PLUS_DMG);
            upgradeMagicNumber(2);
            this.upgradedMagicNumber = true;
            initializeDescription();
        }
    }
}