package theVacant.cards.Skills;

import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theVacant.VacantMod;
import theVacant.cards.AbstractDynamicCard;
import theVacant.characters.TheVacant;

import static theVacant.VacantMod.makeCardPath;

public class Ward extends AbstractDynamicCard
{

    public static final String ID = VacantMod.makeID(Ward.class.getSimpleName());
    public static final String IMG = makeCardPath("Ward.png");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);

    private static final CardRarity RARITY = CardRarity.COMMON;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = TheVacant.Enums.COLOR_GOLD;

    private static final int COST = 0;
    private static final int BLOCK = 3;
    private static final int UPGRADE_PLUS_BLOCK = 1;

    public Ward()
    {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        this.block = this.baseBlock = BLOCK;
        this.getBonusMillToMagic = true;
        this.displayWill = true;
    }

    @Override
    public void use(AbstractPlayer player, AbstractMonster m)
    {
        PreRelease();
        for(int i = 0; i < GetWill(); i++)
            AbstractDungeon.actionManager.addToBottom(new GainBlockAction(player, player, block));
        PostRelease();
    }

    @Override
    public void upgrade()
    {
        if (!upgraded)
        {
            upgradeName();
            upgradeBlock(UPGRADE_PLUS_BLOCK);
            initializeDescription();
        }
    }
}