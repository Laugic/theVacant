package theVacant.cards.Powers;

import basemod.BaseMod;
import basemod.helpers.TooltipInfo;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theVacant.VacantMod;
import theVacant.cards.AbstractDynamicCard;
import theVacant.cards.Modifiers.VoidboundModifier;
import theVacant.characters.TheVacant;
import theVacant.powers.ShardPower;
import theVacant.powers.VoidPower;
import theVacant.util.KeywordManager;

import java.util.ArrayList;
import java.util.List;

import static theVacant.VacantMod.makeCardPath;

public class Voidstone extends AbstractDynamicCard {

    public static final String ID = VacantMod.makeID(Voidstone.class.getSimpleName());
    public static final String IMG = makeCardPath("Voidstone.png");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);

    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.POWER;
    public static final CardColor COLOR = TheVacant.Enums.COLOR_GOLD;


    private static final int COST = 2;

    public Voidstone()
    {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        magicNumber = baseMagicNumber = 3;
    }


    private static ArrayList<TooltipInfo> VoidboundTooltip;


    @Override
    public List<TooltipInfo> getCustomTooltips()
    {
        if(VoidboundTooltip == null)
        {
            VoidboundTooltip = new ArrayList<>();
            VoidboundTooltip.add(new TooltipInfo(BaseMod.getKeywordProper(KeywordManager.VOID_ID), BaseMod.getKeywordDescription(KeywordManager.VOID_ID)));
            VoidboundTooltip.add(new TooltipInfo(BaseMod.getKeywordProper(KeywordManager.VOIDBOUND_ID), BaseMod.getKeywordDescription(KeywordManager.VOIDBOUND_ID)));
        }
        return VoidboundTooltip;
    }


    @Override
    public void use(AbstractPlayer player, AbstractMonster monster)
    {
        addToBot(new ApplyPowerAction(player, player, new VoidPower(player, player, magicNumber), magicNumber));
        addToBot(new ApplyPowerAction(player, player, new ShardPower(player, player, 1), 1));
    }


    @Override
    public void upgrade()
    {
        if (!upgraded)
        {
            upgradeName();
            isInnate = true;
            rawDescription = cardStrings.UPGRADE_DESCRIPTION;
            initializeDescription();
        }
    }
}