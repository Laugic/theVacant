package theVacant.cards.Skills;

import basemod.BaseMod;
import basemod.helpers.TooltipInfo;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.LoseHPAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.ArtifactPower;
import com.megacrit.cardcrawl.powers.WeakPower;
import theVacant.VacantMod;
import theVacant.cards.AbstractDynamicCard;
import theVacant.characters.TheVacant;
import theVacant.powers.TemperancePower;
import theVacant.powers.VoidPower;
import theVacant.relics.BrassGoblet;
import theVacant.relics.OverflowingGobletRelic;
import theVacant.util.KeywordManager;

import java.util.ArrayList;
import java.util.List;

import static theVacant.VacantMod.makeCardPath;

public class Partake extends AbstractDynamicCard
{

    public static final String ID = VacantMod.makeID(Partake.class.getSimpleName());
    public static final String IMG = makeCardPath("Partake.png");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);

    private static final CardRarity RARITY = CardRarity.COMMON;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = TheVacant.Enums.COLOR_GOLD;

    private static ArrayList<TooltipInfo> Tooltip;

    private static final int COST = 0, BLOCK = 8, UP_BLOCK = 3, WEAK = 2;

    public Partake()
    {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        block = baseBlock = BLOCK;
    }

    @Override
    public void use(AbstractPlayer player, AbstractMonster m)
    {
        addToBot(new GainBlockAction(player, block));
        addToBot(new ApplyPowerAction(player, player, new WeakPower(player, WEAK, false), WEAK));
    }

//    @Override
//    public void applyPowers()
//    {
//        magicNumber = baseMagicNumber = getMagic();
//        getDesc();
//        super.applyPowers();
//    }
//    @Override
//    public void atTurnStart()
//    {
//        magicNumber = baseMagicNumber = getMagic();
//        getDesc();
//        super.atTurnStart();
//    }

    @Override
    public void upgrade()
    {
        if (!upgraded)
        {
            upgradeName();
            upgradeBlock(UP_BLOCK);
            initializeDescription();
        }
    }

//    private int getMagic()
//    {
//        AbstractPlayer player = AbstractDungeon.player;
//        if(player == null)
//            return 0;
//        if(player.hasRelic(BrassGoblet.ID))
//            return player.getRelic(BrassGoblet.ID).counter;
//        if(player.hasRelic(OverflowingGobletRelic.ID))
//            return player.getRelic(OverflowingGobletRelic.ID).counter;
//        return 0;
//    }
//
//    private void getDesc()
//    {
//        rawDescription = (upgraded?cardStrings.EXTENDED_DESCRIPTION[1]:cardStrings.EXTENDED_DESCRIPTION[0]);
//        initializeDescription();
//    }
//
//    @Override
//    public void onMoveToDiscard()
//    {
//        rawDescription = upgraded?cardStrings.UPGRADE_DESCRIPTION:cardStrings.DESCRIPTION;
//        initializeDescription();
//    }
//
//    @Override
//    public List<TooltipInfo> getCustomTooltips()
//    {
//        if(Tooltip == null)
//        {
//            Tooltip = new ArrayList<>();
//            Tooltip.add(new TooltipInfo(BaseMod.getKeywordProper(KeywordManager.VOID_ID), BaseMod.getKeywordDescription(KeywordManager.VOID_ID)));
//            //Tooltip.add(new TooltipInfo(BaseMod.getKeywordProper(KeywordManager.VOID_FORM_ID), BaseMod.getKeywordDescription(KeywordManager.VOID_FORM_ID)));
//        }
//        return Tooltip;
//    }
}