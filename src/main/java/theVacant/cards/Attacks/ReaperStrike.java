package theVacant.cards.Attacks;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.combat.CleaveEffect;
import com.megacrit.cardcrawl.vfx.combat.IntimidateEffect;
import theVacant.VacantMod;
import theVacant.actions.VacantMillAction;
import theVacant.cards.AbstractDynamicCard;
import theVacant.characters.TheVacant;

import static theVacant.VacantMod.makeCardPath;

public class ReaperStrike extends AbstractDynamicCard
{

    public static final String ID = VacantMod.makeID(ReaperStrike.class.getSimpleName());
    public static final String IMG = makeCardPath("Attack.png");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);

    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.ALL_ENEMY;
    private static final CardType TYPE = CardType.ATTACK;
    public static final CardColor COLOR = TheVacant.Enums.COLOR_GOLD;

    private static final int COST = 2;
    private static final int DAMAGE = 14;
    private static final int UPGRADE_PLUS_DMG = 4;

    public ReaperStrike()
    {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        this.damage = this.baseDamage = DAMAGE;
        this.magicNumber = this.baseMagicNumber = 3;
        this.getBonusMillToMagic = true;
    }

    @Override
    public void use(AbstractPlayer player, AbstractMonster monster)
    {
        AbstractDungeon.actionManager.addToBottom(new SFXAction("ATTACK_HEAVY"));
        AbstractDungeon.actionManager.addToBottom(new VFXAction(player, new CleaveEffect(), 0.1F));
        AbstractDungeon.actionManager.addToBottom(new DamageAllEnemiesAction(player, DamageInfo.createDamageMatrix(this.damage, true), this.damageTypeForTurn, AbstractGameAction.AttackEffect.LIGHTNING));
    }

    @Override
    public void upgrade()
    {
        if (!upgraded)
        {
            upgradeName();
            upgradeDamage(UPGRADE_PLUS_DMG);
            upgradeMagicNumber(1);
            this.upgradedMagicNumber = true;
            initializeDescription();
        }
    }
}