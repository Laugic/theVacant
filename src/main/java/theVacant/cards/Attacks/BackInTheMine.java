package theVacant.cards.Attacks;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theVacant.VacantMod;
import theVacant.actions.ChipOrbAction;
import theVacant.actions.MineGemAction;
import theVacant.actions.SpelunkAction;
import theVacant.cards.AbstractDynamicCard;
import theVacant.characters.TheVacant;

import static theVacant.VacantMod.makeCardPath;

public class BackInTheMine extends AbstractDynamicCard {

    public static final String ID = VacantMod.makeID(BackInTheMine.class.getSimpleName());
    public static final String IMG = makeCardPath("BackintheMine.png");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);

    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.ALL_ENEMY;
    private static final CardType TYPE = CardType.ATTACK;
    public static final CardColor COLOR = TheVacant.Enums.COLOR_GOLD;

    private static final int COST = 2;
    private static final int DAMAGE = 12;

    public BackInTheMine()
    {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        damage = baseDamage = DAMAGE;
        isMultiDamage = true;
    }


    @Override
    public void use(AbstractPlayer player, AbstractMonster monster)
    {
        addToBot(new DamageAllEnemiesAction(player, multiDamage, damageTypeForTurn, AbstractGameAction.AttackEffect.FIRE));
        for (AbstractMonster mon : (AbstractDungeon.getMonsters()).monsters) {
            if (!mon.isDeadOrEscaped())
                addToBot(new MineGemAction(null, 1));
        }
    }

    @Override
    public void upgrade()
    {
        if (!upgraded)
        {
            upgradeName();
            upgradeDamage(5);
            initializeDescription();
        }
    }
}