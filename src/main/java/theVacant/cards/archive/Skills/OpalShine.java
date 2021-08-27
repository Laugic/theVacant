package theVacant.cards.archive.Skills;

import basemod.BaseMod;
import basemod.helpers.TooltipInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theVacant.VacantMod;
import theVacant.actions.EnhanceInPileAction;
import theVacant.cards.AbstractDynamicCard;
import theVacant.cards.Modifiers.SoulforgedModifier;
import theVacant.characters.TheVacant;
import theVacant.util.KeywordManager;

import java.util.ArrayList;
import java.util.List;

import static theVacant.Enums.VacantTags.GEMS;
import static theVacant.VacantMod.makeCardPath;

public class OpalShine extends AbstractDynamicCard {

    public static final String ID = VacantMod.makeID(OpalShine.class.getSimpleName());
    public static final String IMG = makeCardPath("Skill.png");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);

    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = TheVacant.Enums.COLOR_GOLD;

    private static final int COST = 3;

    private static ArrayList<TooltipInfo> SoulforgedTooltip;
    public OpalShine()
    {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        magicNumber = baseMagicNumber = 1;
        exhaust = true;
        SoulforgedModifier.Enhance(this, 1);
    }

    @Override
    public void use(AbstractPlayer player, AbstractMonster monster)
    {
        AbstractDungeon.actionManager.addToBottom(new EnhanceInPileAction(player.hand, this.magicNumber, SoulforgedModifier.ID, 1));
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