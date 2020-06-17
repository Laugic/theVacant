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
import theVacant.cards.Modifiers.VoidboundModifier;
import theVacant.characters.TheVacant;

import static theVacant.VacantMod.makeCardPath;

public class PitOfDespair extends AbstractDynamicCard
{

    public static final String ID = VacantMod.makeID(PitOfDespair.class.getSimpleName());
    public static final String IMG = makeCardPath("Attack.png");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);

    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final CardType TYPE = CardType.ATTACK;
    public static final CardColor COLOR = TheVacant.Enums.COLOR_GOLD;

    private static final int COST = 2;
    private static final int DAMAGE = 10;
    private static final int UPGRADE_PLUS_DMG = 4;

    private boolean played = false;

    public PitOfDespair()
    {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        this.damage = this.baseDamage = DAMAGE;
        VoidboundModifier.Enhance(this, 1);
        this.played = false;
    }

    @Override
    public void use(AbstractPlayer player, AbstractMonster monster)
    {
        AbstractDungeon.actionManager.addToBottom( new DamageAction(monster, new DamageInfo(player, this.damage, this.damageTypeForTurn), AbstractGameAction.AttackEffect.SLASH_DIAGONAL));
        this.played = true;
    }

    @Override
    public void onMoveToDiscard()
    {
        if(this.played)
        {
            VoidboundModifier.Enhance(this, 1);
            this.played = false;
            initializeDescription();
        }
    }

    @Override
    public void triggerOnExhaust()
    {
        if(this.played)
        {
            VoidboundModifier.Enhance(this, 1);
            this.played = false;
            initializeDescription();
        }
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
}