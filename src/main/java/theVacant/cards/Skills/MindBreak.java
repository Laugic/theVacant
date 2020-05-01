package theVacant.cards.Skills;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DiscardAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.RegenPower;
import theVacant.VacantMod;
import theVacant.actions.VacantMillAction;
import theVacant.cards.AbstractDynamicCard;
import theVacant.cards.Attacks.Snap;
import theVacant.characters.TheVacant;
import theVacant.powers.WillPower;

import static theVacant.VacantMod.makeCardPath;

public class MindBreak extends AbstractDynamicCard
{

    public static final String ID = VacantMod.makeID(MindBreak.class.getSimpleName());
    public static final String IMG = makeCardPath("Skill.png");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);

    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = TheVacant.Enums.COLOR_GOLD;

    private static final int COST = 2;

    public MindBreak()
    {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        this.cardsToPreview = new Snap();
        this.magicNumber = this.baseMagicNumber = 1;
        this.exhaust = true;
        this.getBonusMillToMagic = true;
    }

    @Override
    public void use(AbstractPlayer player, AbstractMonster monster)
    {
        int numCards = Math.max(player.hand.size() - 1, 0);
        if(numCards > 0)
        {
            AbstractDungeon.actionManager.addToTop(new MakeTempCardInHandAction(this.cardsToPreview, numCards));
            AbstractDungeon.actionManager.addToTop(new DiscardAction(player, player, numCards, false));
            AbstractDungeon.actionManager.addToBottom(new VacantMillAction(numCards*this.magicNumber));
        }
    }

    @Override
    public void upgrade()
    {
        if (!upgraded)
        {
            upgradeName();
            AbstractCard upgCard = new Snap();
            upgCard.upgrade();
            this.cardsToPreview = upgCard;
            initializeDescription();
        }
    }
}