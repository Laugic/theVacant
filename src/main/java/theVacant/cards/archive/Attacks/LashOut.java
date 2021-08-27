package theVacant.cards.archive.Attacks;

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

public class LashOut extends AbstractDynamicCard
{

    public static final String ID = VacantMod.makeID(LashOut.class.getSimpleName());
    public static final String IMG = makeCardPath("LashOut.png");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);

    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final CardType TYPE = CardType.ATTACK;
    public static final CardColor COLOR = TheVacant.Enums.COLOR_GOLD;

    private static final int COST = 2;
    private static final int DAMAGE = 0;

    public LashOut()
    {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        baseDamage = DAMAGE;
        exhaust = true;
    }

    @Override
    public void use(AbstractPlayer player, AbstractMonster monster)
    {
        AbstractDungeon.actionManager.addToBottom(
                new DamageAction(monster, new DamageInfo(player, this.damage, damageTypeForTurn),
                        AbstractGameAction.AttackEffect.SLASH_DIAGONAL));
    }

    @Override
    public void upgrade()
    {
        if (!upgraded)
        {
            upgradeName();
            upgradeBaseCost(1);
            initializeDescription();
        }
    }
    @Override
    public void applyPowers()
    {
        getDamage();
        getDesc();
        super.applyPowers();
    }
    @Override
    public void atTurnStart()
    {
        getDamage();
        getDesc();
        super.atTurnStart();
    }
    /*@Override
    public void calculateCardDamage(AbstractMonster monster)
    {
        getDamage();
        getDesc();
        super.calculateCardDamage(monster);
    }*/

    private void getDamage()
    {
        AbstractPlayer player = AbstractDungeon.player;
        if(player != null)
            this.baseDamage = player.maxHealth - player.currentHealth;
        else
            this.baseDamage = 0;
    }

    private void getDesc()
    {
        this.rawDescription = cardStrings.DESCRIPTION + cardStrings.EXTENDED_DESCRIPTION[0];
        initializeDescription();
    }

    @Override
    public void onMoveToDiscard()
    {
        this.rawDescription = cardStrings.DESCRIPTION;
        initializeDescription();
    }
}