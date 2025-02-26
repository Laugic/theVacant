package theVacant.cards.Skills;

import basemod.helpers.TooltipInfo;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.combat.InflameEffect;
import theVacant.VacantMod;
import theVacant.actions.MineGemAction;
import theVacant.cards.AbstractDynamicCard;
import theVacant.characters.TheVacant;
import theVacant.orbs.RubyOrb;

import java.util.ArrayList;

import static theVacant.VacantMod.makeCardPath;

public class RubyRage extends AbstractDynamicCard
{

    public static final String ID = VacantMod.makeID(RubyRage.class.getSimpleName());
    public static final String IMG = makeCardPath("RubyRage.png");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);

    private static final CardRarity RARITY = CardRarity.COMMON;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = TheVacant.Enums.COLOR_GOLD;

    private static final int COST = 1;

    private static ArrayList<TooltipInfo> ExtraTooltip;

    public RubyRage()
    {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        magicNumber = baseMagicNumber = 2;
        checkHollow = true;
    }

    @Override
    public void use(AbstractPlayer player, AbstractMonster monster)
    {
        addToBot(new VFXAction(player, new InflameEffect(player), .5F));
        for (int i = 0; i < magicNumber; i++)
            addToBot(new MineGemAction(new RubyOrb(2), getHollow()));
//        int vigorAmount = player.discardPile.size();
//        if(vigorAmount > 0)
//            addToBot(new ApplyPowerAction(player, player, new VigorPower(player, vigorAmount), vigorAmount));
//        addToBot(new SwitchFormAction(BoundSoul.FURY_FORM));
//        if(upgraded)
//            addToBot(new SyphonAction(magicNumber));
    }

//    @Override
//    public List<TooltipInfo> getCustomTooltips()
//    {
//        if(ExtraTooltip == null)
//        {
//            ExtraTooltip = new ArrayList<>();
//            ExtraTooltip.add(new TooltipInfo(BaseMod.getKeywordProper(KeywordManager.FURY_FORM_ID), BaseMod.getKeywordDescription(KeywordManager.FURY_FORM_ID)));
//        }
//        return ExtraTooltip;
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