package theVacant.cards.archive.Skills;

import basemod.BaseMod;
import basemod.helpers.TooltipInfo;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.HealAction;
import com.megacrit.cardcrawl.actions.common.LoseHPAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.ArtifactPower;
import theVacant.VacantMod;
import theVacant.cards.AbstractDynamicCard;
import theVacant.characters.TheVacant;
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

    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = TheVacant.Enums.COLOR_GOLD;

    private static ArrayList<TooltipInfo> Tooltip;

    private static final int COST = 2, LOSE_HP = 6;

    public Partake()
    {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        magicNumber = baseMagicNumber = 2;
        exhaust = true;
    }

    @Override
    public void use(AbstractPlayer player, AbstractMonster m)
    {
        addToBot(new LoseHPAction(player, player, LOSE_HP));
        if(getMagic() > 0)
        {
            magicNumber = baseMagicNumber = getMagic();
            addToBot(new ApplyPowerAction(player, player, new VoidPower(player, player, magicNumber), magicNumber));
            addToBot(new ApplyPowerAction(player, player, new ArtifactPower(player, magicNumber), magicNumber));
        }
    }

    @Override
    public void applyPowers()
    {
        magicNumber = baseMagicNumber = getMagic();
        getDesc();
        super.applyPowers();
    }
    @Override
    public void atTurnStart()
    {
        magicNumber = baseMagicNumber = getMagic();
        getDesc();
        super.atTurnStart();
    }

    @Override
    public void upgrade()
    {
        if (!upgraded)
        {
            upgradeName();
            exhaust = false;
            rawDescription = cardStrings.UPGRADE_DESCRIPTION;
            initializeDescription();
        }
    }

    private int getMagic()
    {
        AbstractPlayer player = AbstractDungeon.player;
        if(player == null)
            return 0;
        if(player.hasRelic(BrassGoblet.ID))
            return player.getRelic(BrassGoblet.ID).counter;
        if(player.hasRelic(OverflowingGobletRelic.ID))
            return player.getRelic(OverflowingGobletRelic.ID).counter;
        return 0;
    }

    private void getDesc()
    {
        rawDescription = (upgraded?cardStrings.EXTENDED_DESCRIPTION[1]:cardStrings.EXTENDED_DESCRIPTION[0]);
        initializeDescription();
    }

    @Override
    public void onMoveToDiscard()
    {
        rawDescription = upgraded?cardStrings.UPGRADE_DESCRIPTION:cardStrings.DESCRIPTION;
        initializeDescription();
    }

    @Override
    public List<TooltipInfo> getCustomTooltips()
    {
        if(Tooltip == null)
        {
            Tooltip = new ArrayList<>();
            Tooltip.add(new TooltipInfo(BaseMod.getKeywordProper(KeywordManager.VOID_ID), BaseMod.getKeywordDescription(KeywordManager.VOID_ID)));
            //Tooltip.add(new TooltipInfo(BaseMod.getKeywordProper(KeywordManager.VOID_FORM_ID), BaseMod.getKeywordDescription(KeywordManager.VOID_FORM_ID)));
        }
        return Tooltip;
    }
}