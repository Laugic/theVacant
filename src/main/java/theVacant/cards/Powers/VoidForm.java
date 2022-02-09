package theVacant.cards.Powers;

import basemod.BaseMod;
import basemod.helpers.TooltipInfo;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theVacant.VacantMod;
import theVacant.cards.AbstractDynamicCard;
import theVacant.characters.TheVacant;
import theVacant.powers.VoidPower;
import theVacant.util.KeywordManager;

import java.util.ArrayList;
import java.util.List;

import static theVacant.VacantMod.makeCardPath;


public class VoidForm extends AbstractDynamicCard
{

    public static final String ID = VacantMod.makeID(VoidForm.class.getSimpleName());
    public static final String IMG = makeCardPath("VoidForm.png");

    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;

    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.POWER;
    public static final CardColor COLOR = TheVacant.Enums.COLOR_GOLD;

    private static ArrayList<TooltipInfo> Tooltip;

    private static final int COST = 3;

    public VoidForm()
    {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        magicNumber = baseMagicNumber = 5;
    }

    @Override
    public void use(AbstractPlayer player, AbstractMonster monster)
    {
        addToBot(new ApplyPowerAction(player, player, new VoidPower(player, player, magicNumber), magicNumber));
//        addToBot(new ApplyPowerAction(player, player, new VoidFormPower(player, player, 1), 1));
        //addToBot(new SwitchFormAction(BoundSoul.VOID_FORM));
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
    @Override
    public void upgrade()
    {
        if (!upgraded)
        {
            upgradeName();
            upgradeMagicNumber(3);
            upgradedMagicNumber = true;
            initializeDescription();
        }
    }
}