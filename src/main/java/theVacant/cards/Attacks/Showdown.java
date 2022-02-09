package theVacant.cards.Attacks;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.combat.AdrenalineEffect;
import com.megacrit.cardcrawl.vfx.combat.CleaveEffect;
import theVacant.VacantMod;
import theVacant.cards.AbstractDynamicCard;
import theVacant.characters.TheVacant;
import theVacant.powers.DoomPower;

import static theVacant.VacantMod.makeCardPath;

public class Showdown extends AbstractDynamicCard
{

    public static final String ID = VacantMod.makeID(Showdown.class.getSimpleName());
    public static final String IMG = makeCardPath("Showdown.png");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);

    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.ALL_ENEMY;
    private static final CardType TYPE = CardType.ATTACK;
    public static final CardColor COLOR = TheVacant.Enums.COLOR_GOLD;

    private static final int COST = 2;
    private static final int DAMAGE = 10;
    private static final int DOOM_AMOUNT = 2;

    public Showdown()
    {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        damage = baseDamage = DAMAGE;
        magicNumber = baseMagicNumber = 2;
        isMultiDamage = true;
    }

    @Override
    public void use(AbstractPlayer player, AbstractMonster monster)
    {
        addToBot(new VFXAction(new AdrenalineEffect(), 0.15F));
        for(int i = 0; i < this.magicNumber; i++)
        {
            AbstractDungeon.actionManager.addToBottom(new SFXAction("ATTACK_HEAVY"));
            AbstractDungeon.actionManager.addToBottom(new VFXAction(player, new CleaveEffect(), 0.1F));
            AbstractDungeon.actionManager.addToBottom(new DamageAllEnemiesAction(player, DamageInfo.createDamageMatrix(this.damage, true), this.damageTypeForTurn, AbstractGameAction.AttackEffect.NONE));
        }

        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(player, player, new DoomPower(player, player, DOOM_AMOUNT), DOOM_AMOUNT));
        for (AbstractMonster mo : (AbstractDungeon.getCurrRoom()).monsters.monsters)
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(mo, player, new DoomPower(mo, mo, DOOM_AMOUNT), DOOM_AMOUNT, true, AbstractGameAction.AttackEffect.NONE));
    }

    @Override
    public void upgrade()
    {
        if (!upgraded)
        {
            upgradeName();
            upgradeDamage(4);
            upgradedDamage = true;
            initializeDescription();
        }
    }
}