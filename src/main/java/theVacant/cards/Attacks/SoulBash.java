package theVacant.cards.Attacks;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.watcher.VigorPower;
import theVacant.VacantMod;
import theVacant.cards.AbstractDynamicCard;
import theVacant.characters.TheVacant;

import static theVacant.VacantMod.makeCardPath;

public class SoulBash extends AbstractDynamicCard
{

    public static final String ID = VacantMod.makeID(SoulBash.class.getSimpleName());
    public static final String IMG = makeCardPath("Crunch.png");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);

    private static final CardRarity RARITY = CardRarity.BASIC;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final CardType TYPE = CardType.ATTACK;
    public static final CardColor COLOR = TheVacant.Enums.COLOR_GOLD;

    private static final int COST = 0;
    private static final int DAMAGE = 3;

    public SoulBash()
    {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        damage = baseDamage = DAMAGE;
        magicNumber = baseMagicNumber = 3;
        rebound = true;
    }

    @Override
    public void use(AbstractPlayer player, AbstractMonster monster)
    {
        addToBot( new DamageAction(monster, new DamageInfo(player, damage, damageTypeForTurn), AbstractGameAction.AttackEffect.SMASH));

        addToBot(new ApplyPowerAction(player, player, new VigorPower(player, magicNumber), magicNumber));

        /*switch(GetForm())
        {
            case 1:
                addToBot(new ApplyPowerAction(monster, player, new VulnerablePower(monster, magicNumber, false), magicNumber));
                break;
            case 2:
                addToBot(new ApplyPowerAction(monster, player, new DoomPower(monster, player, magicNumber), magicNumber));
                break;
            case 3:
                addToBot(new ApplyPowerAction(monster, player, new ReapPower(monster, player, magicNumber), magicNumber));
                break;
            default:
                addToBot(new ApplyPowerAction(monster, player, new WeakPower(monster, magicNumber, false), magicNumber));
        }*/
    }
/*
    @Override
    public void applyPowers()
    {
        if(GetForm() == -1)
            return;
        this.rawDescription = this.cardStrings.UPGRADE_DESCRIPTION;

        this.rawDescription += cardStrings.EXTENDED_DESCRIPTION[GetForm()];
        initializeDescription();
        super.applyPowers();
    }

    private int GetForm()
    {
        int amount = -1;
        AbstractPlayer player = AbstractDungeon.player;
        if(player == null || !player.hasRelic(BoundSoul.ID))
            return amount;
        if(!((BoundSoul)player.getRelic(BoundSoul.ID)).inBattle)
            return amount;
        amount = ((BoundSoul)player.getRelic(BoundSoul.ID)).GetCurrentForm();
        return amount;
    }

    @Override
    public void onMoveToDiscard()
    {
        this.rawDescription = cardStrings.DESCRIPTION;
        initializeDescription();
    }
*/
    @Override
    public void upgrade()
    {
        if (!upgraded)
        {
            upgradeName();
            upgradeDamage(2);
            upgradedDamage = true;
            upgradeMagicNumber(2);
            upgradedMagicNumber = true;
            initializeDescription();
        }
    }
}