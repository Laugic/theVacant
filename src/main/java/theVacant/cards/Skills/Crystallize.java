package theVacant.cards.Skills;

import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.CardLibrary;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theVacant.VacantMod;
import theVacant.actions.YeetAction;
import theVacant.cards.AbstractDynamicCard;
import theVacant.characters.TheVacant;

import java.util.Random;

import static theVacant.VacantMod.makeCardPath;

public class Crystallize extends AbstractDynamicCard
{

    public static final String ID = VacantMod.makeID(Crystallize.class.getSimpleName());
    public static final String IMG = makeCardPath("Skill.png");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;

    private static final CardRarity RARITY = CardRarity.BASIC;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = TheVacant.Enums.COLOR_GOLD;


    private static final int COST = 1;

    public Crystallize()
    {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        rebound = true;
    }

    @Override
    public void use(AbstractPlayer player, AbstractMonster monster)
    {
        Random rand = new Random();
        AbstractCard c = CardLibrary.getCard(TheVacant.crystalCards.get(rand.nextInt(TheVacant.crystalCards.size() - 1)));
        if(upgraded)
            c.upgrade();
        addToBot(new MakeTempCardInHandAction(c, true));
    }

    @Override
    public void PostMillAction()
    {
        AbstractDungeon.actionManager.addToTop(new YeetAction(this));
    }

    @Override
    public void upgrade()
    {
        if (!upgraded)
        {
            upgradeName();
            this.rawDescription = cardStrings.UPGRADE_DESCRIPTION;
            initializeDescription();
        }
    }
}