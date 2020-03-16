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

public class BurdenBreak extends AbstractDynamicCard
{

    public static final String ID = VacantMod.makeID(BurdenBreak.class.getSimpleName());
    public static final String IMG = makeCardPath("Attack.png");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);

    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final CardType TYPE = CardType.ATTACK;
    public static final CardColor COLOR = TheVacant.Enums.COLOR_GOLD;

    private static final int COST = 1;
    private static final int DAMAGE = 7;
    private static final int UPGRADE_PLUS_DMG = 1;

    public BurdenBreak()
    {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        this.damage = this.baseDamage = DAMAGE;
    }

    @Override
    public void use(AbstractPlayer player, AbstractMonster monster)
    {
        int mills = 1;
        if(player instanceof TheVacant)
            mills = ((TheVacant) player).millsThisTurn;
        for(int i = 0; i < mills; i++)
            AbstractDungeon.actionManager.addToBottom( new DamageAction(monster, new DamageInfo(player, this.damage, damageTypeForTurn), AbstractGameAction.AttackEffect.SLASH_DIAGONAL));
    }
    @Override
    public void upgrade()
    {
        if (!upgraded)
        {
            upgradeName();
            upgradeDamage(UPGRADE_PLUS_DMG);
            this.rebound = true;
            this.rawDescription = cardStrings.UPGRADE_DESCRIPTION;
            initializeDescription();
        }
    }
    @Override
    public void applyPowers()
    {
        super.applyPowers();
        int count = 0;
        AbstractPlayer player = AbstractDungeon.player;
        if(player instanceof TheVacant)
        {
            count = ((TheVacant) player).millsThisTurn;
        }
        this.rawDescription = this.upgraded?cardStrings.UPGRADE_DESCRIPTION:cardStrings.DESCRIPTION;
        this.rawDescription += cardStrings.EXTENDED_DESCRIPTION[0] + count;
        if (count == 1)
            this.rawDescription += cardStrings.EXTENDED_DESCRIPTION[1];
        else
            this.rawDescription += cardStrings.EXTENDED_DESCRIPTION[2];
        initializeDescription();
    }
    @Override
    public void onMoveToDiscard()
    {
        this.rawDescription = this.upgraded?cardStrings.UPGRADE_DESCRIPTION:cardStrings.DESCRIPTION;
        initializeDescription();
    }
}