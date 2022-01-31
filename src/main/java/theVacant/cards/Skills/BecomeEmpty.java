package theVacant.cards.Skills;

import basemod.BaseMod;
import basemod.helpers.TooltipInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theVacant.VacantMod;
import theVacant.actions.SwitchFormAction;
import theVacant.actions.SyphonAction;
import theVacant.cards.AbstractDynamicCard;
import theVacant.characters.TheVacant;
import theVacant.relics.BoundSoulOld;
import theVacant.util.KeywordManager;

import java.util.ArrayList;
import java.util.List;

import static theVacant.VacantMod.makeCardPath;

public class BecomeEmpty extends AbstractDynamicCard
{

    public static final String ID = VacantMod.makeID(BecomeEmpty.class.getSimpleName());
    public static final String IMG = makeCardPath("Skill.png");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);

    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = TheVacant.Enums.COLOR_GOLD;

    private static final int COST = 2;

    private static ArrayList<TooltipInfo> BecomeEmptyTooltip;

    public BecomeEmpty()
    {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        magicNumber = baseMagicNumber = 80;
        exhaust = true;
    }

    @Override
    public void use(AbstractPlayer player, AbstractMonster m)
    {
        addToBot(new SwitchFormAction(BoundSoulOld.VACANT_FORM));
        addToBot(new SyphonAction(magicNumber));
    }

    @Override
    public List<TooltipInfo> getCustomTooltips()
    {
        if(BecomeEmptyTooltip == null)
        {
            BecomeEmptyTooltip = new ArrayList<>();
            BecomeEmptyTooltip.add(new TooltipInfo(BaseMod.getKeywordProper(KeywordManager.SOUL_ID), BaseMod.getKeywordDescription(KeywordManager.SOUL_ID)));
            BecomeEmptyTooltip.add(new TooltipInfo(BaseMod.getKeywordProper(KeywordManager.VACANT_FORM_ID), BaseMod.getKeywordDescription(KeywordManager.VACANT_FORM_ID)));
        }
        return BecomeEmptyTooltip;
    }

    @Override
    public void upgrade()
    {
        if (!upgraded)
        {
            upgradeName();
            upgradeBaseCost(1);
            initializeDescription();
        }
    }
}