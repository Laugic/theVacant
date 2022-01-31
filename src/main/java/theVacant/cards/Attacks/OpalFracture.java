package theVacant.cards.Attacks;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.actions.unique.RegenAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theVacant.VacantMod;
import theVacant.actions.MineGemAction;
import theVacant.cards.AbstractDynamicCard;
import theVacant.cards.Modifiers.EchoModifier;
import theVacant.cards.Modifiers.SoulforgedModifier;
import theVacant.characters.TheVacant;
import theVacant.orbs.OpalOrb;

import static theVacant.VacantMod.makeCardPath;

public class OpalFracture extends AbstractDynamicCard
{

    public static final String ID = VacantMod.makeID(OpalFracture.class.getSimpleName());
    public static final String IMG = makeCardPath("OpalFracture.png");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);

    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final CardType TYPE = CardType.ATTACK;
    public static final CardColor COLOR = TheVacant.Enums.COLOR_GOLD;

    private static final int COST = 2;
    private static final int DAMAGE = 16;

    public OpalFracture()
    {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        damage = baseDamage = DAMAGE;
        magicNumber = baseMagicNumber = 2;
    }

    @Override
    public void use(AbstractPlayer player, AbstractMonster monster)
    {
        addToBot(new DamageAction(monster, new DamageInfo(player, damage, damageTypeForTurn), AbstractGameAction.AttackEffect.BLUNT_LIGHT));
        addToBot(new MineGemAction(new OpalOrb(magicNumber)));
        //addToBot(new DamageAction(AbstractDungeon.player, new DamageInfo(AbstractDungeon.player, damage, DamageInfo.DamageType.NORMAL), AbstractGameAction.AttackEffect.BLUNT_LIGHT));
        //addToBot(new GainEnergyAction(magicNumber));
    }

    @Override
    public void upgrade()
    {
        if (!upgraded)
        {
            upgradeName();
            upgradeDamage(6);
            upgradedDamage = true;
            upgradeMagicNumber(1);
            upgradedMagicNumber = true;
            initializeDescription();
        }
    }
}