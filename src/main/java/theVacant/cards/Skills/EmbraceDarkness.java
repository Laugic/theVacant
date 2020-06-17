package theVacant.cards.Skills;

import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theVacant.VacantMod;
import theVacant.actions.EnhanceInHandAction;
import theVacant.actions.EnhanceInPileAction;
import theVacant.actions.ExhumeAndEnhanceAction;
import theVacant.cards.AbstractDynamicCard;
import theVacant.cards.Modifiers.SoulforgedModifier;
import theVacant.cards.Modifiers.VoidboundModifier;
import theVacant.characters.TheVacant;

import static theVacant.VacantMod.makeCardPath;

public class EmbraceDarkness extends AbstractDynamicCard {

    public static final String ID = VacantMod.makeID(EmbraceDarkness.class.getSimpleName());
    public static final String IMG = makeCardPath("Skill.png");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);

    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = TheVacant.Enums.COLOR_GOLD;

    private static final int COST = 1;

    public EmbraceDarkness()
    {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        this.magicNumber = this.baseMagicNumber = 1;
        this.exhaust = true;
    }

    @Override
    public void use(AbstractPlayer player, AbstractMonster monster)
    {
        AbstractDungeon.actionManager.addToBottom(new ExhumeAndEnhanceAction(this.magicNumber, VoidboundModifier.ID, 1));
    }

    @Override
    public void upgrade()
    {
        if (!upgraded)
        {
            upgradeName();
            this.exhaust = false;
            this.rawDescription = cardStrings.UPGRADE_DESCRIPTION;
            initializeDescription();
        }
    }
}