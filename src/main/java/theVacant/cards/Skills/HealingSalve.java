package theVacant.cards.Skills;

import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.actions.unique.RegenAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theVacant.VacantMod;
import theVacant.cards.AbstractDynamicCard;
import theVacant.characters.TheVacant;

import static theVacant.VacantMod.makeCardPath;

public class HealingSalve extends AbstractDynamicCard
{

    public static final String ID = VacantMod.makeID(HealingSalve.class.getSimpleName());
    public static final String IMG = makeCardPath("Skill.png");

    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = TheVacant.Enums.COLOR_GOLD;

    private static final int COST = 1;
    private static final int BLOCK = 0;

    public HealingSalve()
    {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        baseBlock = 0;
        isEthereal = true;
        magicNumber = baseMagicNumber = 2;
    }
    @Override
    public void triggerWhenDrawn()
    {
        addToTop(new MakeTempCardInHandAction(makeStatEquivalentCopy()));
    }

    @Override
    public void triggerOnExhaust()
    {
        AbstractDungeon.actionManager.addToBottom(new RegenAction(AbstractDungeon.player, magicNumber));
    }

    @Override
    public void upgrade()
    {
        if (!upgraded)
        {
            upgradeName();
            upgradeMagicNumber(1);
            initializeDescription();
        }
    }

    @Override
    public void use(AbstractPlayer abstractPlayer, AbstractMonster abstractMonster)
    {

    }
}