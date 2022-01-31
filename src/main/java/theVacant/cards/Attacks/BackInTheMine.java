package theVacant.cards.Attacks;

import basemod.BaseMod;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.actions.unique.ExhumeAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theVacant.VacantMod;
import theVacant.actions.ExhumeAndEnhanceAction;
import theVacant.actions.IncreaseMagicNumberAction;
import theVacant.actions.MineGemAction;
import theVacant.cards.AbstractDynamicCard;
import theVacant.characters.TheVacant;

import java.util.ArrayList;
import java.util.Random;

import static theVacant.VacantMod.makeCardPath;

public class BackInTheMine extends AbstractDynamicCard {

    public static final String ID = VacantMod.makeID(BackInTheMine.class.getSimpleName());
    public static final String IMG = makeCardPath("BackintheMine.png");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);

    private static final CardRarity RARITY = CardRarity.COMMON;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final CardType TYPE = CardType.ATTACK;
    public static final CardColor COLOR = TheVacant.Enums.COLOR_GOLD;

    private static final int COST = 1;
    private static final int DAMAGE = 7;

    public BackInTheMine()
    {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        damage = baseDamage = DAMAGE;
        magicNumber = baseMagicNumber = 3;
    }


    @Override
    public void use(AbstractPlayer player, AbstractMonster monster)
    {
        addToBot( new DamageAction(monster, new DamageInfo(player, damage, damageTypeForTurn), AbstractGameAction.AttackEffect.BLUNT_LIGHT));
        //addToBot(new ExhumeAndEnhanceAction(1));
        addToBot(new MineGemAction(null, magicNumber));
    }

    @Override
    public void upgrade()
    {
        if (!upgraded)
        {
            upgradeName();
            upgradeDamage(3);
            upgradedDamage = true;
            upgradeMagicNumber(1);
            upgradedMagicNumber = true;
            initializeDescription();
        }
    }
}