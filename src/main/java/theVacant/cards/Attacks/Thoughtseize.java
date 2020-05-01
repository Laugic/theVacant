package theVacant.cards.Attacks;

import basemod.helpers.BaseModCardTags;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.BetterDiscardPileToHandAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.PutOnDeckAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theVacant.VacantMod;
import theVacant.actions.ReleaseAction;
import theVacant.actions.VacantMillAction;
import theVacant.cards.AbstractDynamicCard;
import theVacant.characters.TheVacant;

import static theVacant.VacantMod.makeCardPath;

public class Thoughtseize extends AbstractDynamicCard {

    public static final String ID = VacantMod.makeID(Thoughtseize.class.getSimpleName());
    public static final String IMG = makeCardPath("Attack.png");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);

    private static final CardRarity RARITY = CardRarity.COMMON;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final CardType TYPE = CardType.ATTACK;
    public static final CardColor COLOR = TheVacant.Enums.COLOR_GOLD;

    private static final int COST = 2;
    private static final int DAMAGE = 14;
    private static final int UPGRADE_PLUS_DMG = 6;

    public Thoughtseize()
    {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        this.damage = this.baseDamage = DAMAGE;
        this.magicNumber = this.baseMagicNumber = 4;
        this.getBonusMillToMagic = true;
    }

    @Override
    public void use(AbstractPlayer player, AbstractMonster monster)
    {
        AbstractDungeon.actionManager.addToBottom( new DamageAction(monster, new DamageInfo(player, damage, damageTypeForTurn), AbstractGameAction.AttackEffect.BLUNT_LIGHT));
        AbstractDungeon.actionManager.addToBottom(new VacantMillAction(this.magicNumber));
        AbstractDungeon.actionManager.addToBottom(new BetterDiscardPileToHandAction(1));
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