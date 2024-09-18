package theVacant.cards.Skills;

import basemod.helpers.TooltipInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theVacant.VacantMod;
import theVacant.actions.ChipOrbAction;
import theVacant.actions.MineGemAction;
import theVacant.cards.AbstractDynamicCard;
import theVacant.characters.TheVacant;
import theVacant.orbs.AmethystOrb;

import java.util.ArrayList;

import static theVacant.VacantMod.makeCardPath;

public class ShatterAmethyst extends AbstractDynamicCard
{

    public static final String ID = VacantMod.makeID(ShatterAmethyst.class.getSimpleName());
    public static final String IMG = makeCardPath("ShatterAmethyst.png");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;

    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = TheVacant.Enums.COLOR_GOLD;

    private static ArrayList<TooltipInfo> VoidTooltip;

    private static final int COST = 1;

    public ShatterAmethyst()
    {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        magicNumber = baseMagicNumber = 4;
        ricochet = true;
    }

    @Override
    public void use(AbstractPlayer player, AbstractMonster monster)
    {
//        addToBot(new VFXAction(player, new VerticalAuraEffect(Color.BLACK, player.hb.cX, player.hb.cY), 0.33F));
//        addToBot(new VFXAction(player, new BorderFlashEffect(Color.PURPLE), 0.3F, true));

        AmethystOrb orb = new AmethystOrb(magicNumber);
        addToBot(new MineGemAction(orb, true));
        if(upgraded)
            addToBot(new ChipOrbAction(orb, 1));
//        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(player, player, new VoidPower(player, player, 1), 1));
//        AbstractDungeon.actionManager.addToBottom(new VacantMillAction(magicNumber));
    }
/*
    @Override
    public List<TooltipInfo> getCustomTooltips()
    {
        if(VoidTooltip == null)
        {
            VoidTooltip = new ArrayList<>();
            VoidTooltip.add(new TooltipInfo(BaseMod.getKeywordProper(KeywordManager.VOID_ID), BaseMod.getKeywordDescription(KeywordManager.VOID_ID)));
        }
        return VoidTooltip;
    }
*/
    @Override
    public void upgrade()
    {
        if (!upgraded)
        {
            upgradeName();
            upgradeMagicNumber(1);
            rawDescription = cardStrings.UPGRADE_DESCRIPTION;
            initializeDescription();
        }
    }
}