package theVacant.cards.Skills;

import basemod.helpers.TooltipInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theVacant.VacantMod;
import theVacant.actions.MineGemAction;
import theVacant.cards.AbstractDynamicCard;
import theVacant.characters.TheVacant;
import theVacant.orbs.OpalOrb;

import java.util.ArrayList;

import static theVacant.VacantMod.makeCardPath;

public class OpalShine extends AbstractDynamicCard {

    public static final String ID = VacantMod.makeID(OpalShine.class.getSimpleName());
    public static final String IMG = makeCardPath("OpalShine.png");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);

    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = TheVacant.Enums.COLOR_GOLD;

    private static final int COST = 1;

    private static ArrayList<TooltipInfo> SoulforgedTooltip;
    public OpalShine()
    {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        magicNumber = baseMagicNumber = 3;
    }

    @Override
    public void use(AbstractPlayer player, AbstractMonster monster)
    {
        addToBot(new MineGemAction(new OpalOrb(magicNumber)));
    }

//    @Override
//    public List<TooltipInfo> getCustomTooltips()
//    {
//        if(SoulforgedTooltip == null)
//        {
//            SoulforgedTooltip = new ArrayList<>();
//            SoulforgedTooltip.add(new TooltipInfo(BaseMod.getKeywordProper(KeywordManager.SOULFORGED_ID), BaseMod.getKeywordDescription(KeywordManager.SOULFORGED_ID)));
//        }
//        return SoulforgedTooltip;
//    }

    @Override
    public void upgrade()
    {
        if (!upgraded)
        {
            upgradeName();
            upgradeMagicNumber(1);
            upgradedMagicNumber = true;
            initializeDescription();
        }
    }
}