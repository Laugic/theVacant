package theVacant.cards.Skills;

import com.megacrit.cardcrawl.actions.common.MakeTempCardInDrawPileAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theVacant.VacantMod;
import theVacant.cards.AbstractDynamicCard;
import theVacant.cards.Attacks.Snap;
import theVacant.characters.TheVacant;

import static theVacant.VacantMod.makeCardPath;

public class Krispen extends AbstractDynamicCard
{

    public static final String ID = VacantMod.makeID(Krispen.class.getSimpleName());
    public static final String IMG = makeCardPath("Skill.png");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);

    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = TheVacant.Enums.COLOR_GOLD;

    private static final int COST = 1;

    public Krispen()
    {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        this.cardsToPreview = new Snap();
        this.exhaust = true;
    }

    @Override
    public void use(AbstractPlayer player, AbstractMonster m)
    {
        AbstractDungeon.actionManager.addToBottom(new MakeTempCardInDrawPileAction(this.cardsToPreview, 1, true, true));
        AbstractCard crackle = new Crackle();
        AbstractCard pop = new Pop();
        if(this.upgraded)
        {
            crackle.upgrade();
            pop.upgrade();
        }
        AbstractDungeon.actionManager.addToBottom(new MakeTempCardInDrawPileAction(crackle, 1, true, true));
        AbstractDungeon.actionManager.addToBottom(new MakeTempCardInDrawPileAction(pop, 1, true, true));
    }

    @Override
    public void upgrade()
    {
        if (!upgraded)
        {
            upgradeName();
            this.cardsToPreview.upgrade();
            this.rawDescription = cardStrings.UPGRADE_DESCRIPTION;
            this.keywords.addAll(cardsToPreview.keywords);
            initializeDescription();
        }
    }
}