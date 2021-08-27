package theVacant.cards.archive.Powers;

import basemod.BaseMod;
import basemod.helpers.TooltipInfo;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theVacant.VacantMod;
import theVacant.cards.AbstractDynamicCard;
import theVacant.cards.Modifiers.SoulforgedModifier;
import theVacant.characters.TheVacant;
import theVacant.powers.ForgeSoulPower;
import theVacant.util.KeywordManager;

import java.util.ArrayList;
import java.util.List;

import static theVacant.VacantMod.makeCardPath;


public class ForgeSoul extends AbstractDynamicCard
{

    public static final String ID = VacantMod.makeID(ForgeSoul.class.getSimpleName());
    public static final String IMG = makeCardPath("Power.png");

    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;

    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.POWER;
    public static final CardColor COLOR = TheVacant.Enums.COLOR_GOLD;

    private static final int COST = 3;

    private static ArrayList<TooltipInfo> SoulforgedTooltip;

    public ForgeSoul()
    {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        this.magicNumber = this.baseMagicNumber = 1;
    }

    @Override
    public void use(AbstractPlayer player, AbstractMonster monster)
    {
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(player, player, new ForgeSoulPower(player, player, this.magicNumber), this.magicNumber));
    }

    @Override
    public List<TooltipInfo> getCustomTooltips()
    {
        if(SoulforgedTooltip == null)
        {
            SoulforgedTooltip = new ArrayList<>();
            SoulforgedTooltip.add(new TooltipInfo(BaseMod.getKeywordProper(KeywordManager.SOULFORGED_ID), BaseMod.getKeywordDescription(KeywordManager.SOULFORGED_ID)));
        }
        return SoulforgedTooltip;
    }

    @Override
    public void upgrade()
    {
        if (!upgraded)
        {
            upgradeName();
            SoulforgedModifier.Enhance(this, 1);
            initializeDescription();
        }
    }
}