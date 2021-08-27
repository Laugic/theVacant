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
import theVacant.cards.AbstractDynamicCard;
import theVacant.characters.TheVacant;

import java.util.ArrayList;
import java.util.Random;

import static theVacant.VacantMod.makeCardPath;
import static theVacant.Enums.VacantTags.GEMS;

public class BackInTheMine extends AbstractDynamicCard {

    public static final String ID = VacantMod.makeID(BackInTheMine.class.getSimpleName());
    public static final String IMG = makeCardPath("Attack.png");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);

    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final CardType TYPE = CardType.ATTACK;
    public static final CardColor COLOR = TheVacant.Enums.COLOR_GOLD;

    private static final int COST = 2;
    private static final int DAMAGE = 16;

    public BackInTheMine()
    {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        damage = baseDamage = DAMAGE;
    }


    @Override
    public void use(AbstractPlayer player, AbstractMonster monster)
    {
        addToBot( new DamageAction(monster, new DamageInfo(player, damage, damageTypeForTurn), AbstractGameAction.AttackEffect.BLUNT_LIGHT));
        //addToBot(new ExhumeAndEnhanceAction(1));

        if(GEMS.size() > 0)
        {
            AbstractCard c = GEMS.get(AbstractDungeon.cardRandomRng.random(GEMS.size() - 1)).makeCopy();
            /*if(upgraded)
                c.setCostForTurn(0);*/
            addToBot(new MakeTempCardInHandAction(c, true));
        }
    }

    @Override
    public void upgrade()
    {
        if (!upgraded)
        {
            upgradeName();
            upgradeDamage(6);
            upgradedDamage = true;
            //rawDescription = cardStrings.UPGRADE_DESCRIPTION;
            initializeDescription();
        }
    }
}