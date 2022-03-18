package theVacant.cards.Powers;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theVacant.VacantMod;
import theVacant.cards.AbstractDynamicCard;
import theVacant.characters.TheVacant;
import theVacant.powers.RunicThoughtsPower;

import static theVacant.VacantMod.makeCardPath;


public class RunicThoughts extends AbstractDynamicCard
{

    public static final String ID = VacantMod.makeID(RunicThoughts.class.getSimpleName());
    public static final String IMG = makeCardPath("RunicThoughts.png");

    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;

    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.POWER;
    public static final CardColor COLOR = TheVacant.Enums.COLOR_GOLD;

    private static final int COST = 1;

    public RunicThoughts()
    {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
    }

    @Override
    public void use(AbstractPlayer player, AbstractMonster monster)
    {
        if(!player.hasPower(RunicThoughtsPower.POWER_ID))
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(player, player, new RunicThoughtsPower(player, player, 1), 0));
    }

    @Override
    public void upgrade()
    {
        if (!upgraded)
        {
            upgradeName();
            rebound = true;
            upgradeBaseCost(0);
            rawDescription = cardStrings.UPGRADE_DESCRIPTION;
            initializeDescription();
        }
    }
}