package theVacant.cards.Attacks;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theVacant.VacantMod;
import theVacant.cards.AbstractDynamicCard;
import theVacant.characters.TheVacant;

import static theVacant.VacantMod.makeCardPath;

public class Vengeance extends AbstractDynamicCard
{

    public static final String ID = VacantMod.makeID(Vengeance.class.getSimpleName());
    public static final String IMG = makeCardPath("Attack.png");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);

    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final CardType TYPE = CardType.ATTACK;
    public static final CardColor COLOR = TheVacant.Enums.COLOR_GOLD;

    private static final int COST = 1;
    private static final int DAMAGE = 4;
    private static final int UPGRADE_PLUS_DMG = 1;

    public Vengeance()
    {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        this.damage = this.baseDamage = DAMAGE;
        this.magicNumber = this.baseMagicNumber = 0;
        this.isUnnate = true;
    }

    @Override
    public void use(AbstractPlayer player, AbstractMonster m)
    {
        for(int i = 0; i < player.damagedThisCombat; i++)
            AbstractDungeon.actionManager.addToBottom( new DamageAction(m, new DamageInfo(player, this.damage, damageTypeForTurn), AbstractGameAction.AttackEffect.SLASH_DIAGONAL));
    }

    @Override
    public void upgrade()
    {
        if (!upgraded)
        {
            upgradeName();
            upgradeDamage(UPGRADE_PLUS_DMG);
            initializeDescription();
        }
    }

    @Override
    public void applyPowers()
    {
        this.rawDescription = this.cardStrings.DESCRIPTION;
        int amount = 0;
        if(AbstractDungeon.player != null)
            amount = AbstractDungeon.player.damagedThisCombat;
        this.rawDescription += cardStrings.EXTENDED_DESCRIPTION[0] + amount + ((amount==1)?cardStrings.EXTENDED_DESCRIPTION[1]:cardStrings.EXTENDED_DESCRIPTION[2]);
        initializeDescription();
        super.applyPowers();
    }

    @Override
    public void calculateCardDamage(AbstractMonster monster)
    {
        this.rawDescription = this.cardStrings.DESCRIPTION;
        int amount = 0;
        if(AbstractDungeon.player != null)
            amount = AbstractDungeon.player.damagedThisCombat;
        this.rawDescription += cardStrings.EXTENDED_DESCRIPTION[0] + amount + ((amount==1)?cardStrings.EXTENDED_DESCRIPTION[1]:cardStrings.EXTENDED_DESCRIPTION[2]);
        initializeDescription();
        super.calculateCardDamage(monster);
    }

    @Override
    public void onMoveToDiscard()
    {
        this.rawDescription = cardStrings.DESCRIPTION;
        initializeDescription();
    }
}