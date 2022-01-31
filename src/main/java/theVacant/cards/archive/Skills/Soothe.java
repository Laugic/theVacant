package theVacant.cards.archive.Skills;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theVacant.VacantMod;
import theVacant.cards.AbstractDynamicCard;
import theVacant.characters.TheVacant;
import theVacant.powers.TemperancePower;

import static theVacant.VacantMod.makeCardPath;

public class Soothe extends AbstractDynamicCard
{

    public static final String ID = VacantMod.makeID(Soothe.class.getSimpleName());
    public static final String IMG = makeCardPath("Soothe.png");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);

    private static final CardRarity RARITY = CardRarity.COMMON;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = TheVacant.Enums.COLOR_GOLD;

    private static final int COST = 1;
    private static final int BLOCK = 4;

    public Soothe()
    {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        block = baseBlock = BLOCK;
        magicNumber = baseMagicNumber = BLOCK;
    }

    @Override
    public void use(AbstractPlayer player, AbstractMonster m)
    {
        AbstractDungeon.actionManager.addToBottom(new GainBlockAction(player, player, block));
        addToBot(new ApplyPowerAction(player, player, new TemperancePower(player, player, magicNumber), magicNumber));
    }

    @Override
    public void upgrade()
    {
        if (!upgraded)
        {
            upgradeName();
            upgradeBlock(2);
            upgradedBlock = true;
            upgradeMagicNumber(2);
            upgradedMagicNumber = true;
            initializeDescription();
        }
    }
}