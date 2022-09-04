package theVacant.cards.Skills;

import basemod.helpers.TooltipInfo;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theVacant.VacantMod;
import theVacant.cards.AbstractDynamicCard;
import theVacant.characters.TheVacant;
import theVacant.powers.DoomPower;
import theVacant.powers.ShroudPower;

import java.util.ArrayList;

import static theVacant.VacantMod.makeCardPath;

public class EmbraceDarkness extends AbstractDynamicCard {

    public static final String ID = VacantMod.makeID(EmbraceDarkness.class.getSimpleName());
    public static final String IMG = makeCardPath("EmbraceDarkness.png");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);

    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.ALL;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = TheVacant.Enums.COLOR_GOLD;

    private static ArrayList<TooltipInfo> Tooltip;

    private static final int COST = 1;
    private static final int UPGRADE_MAGIC_NUM = 2;
    private static final int DOOM_AMOUNT = 2, UPGRADED_DOOM_AMOUNT = 3;

    public EmbraceDarkness()
    {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        magicNumber = baseMagicNumber = 4;
        exhaust = true;
    }

    @Override
    public void use(AbstractPlayer player, AbstractMonster monster)
    {
        addToBot(new ApplyPowerAction(player, player, new ShroudPower(player, player, magicNumber), magicNumber));
        //addToBot(new ApplyPowerAction(player, player, new VoidPower(player, player, upgraded ? UPGRADED_VOID_AMOUNT : VOID_AMOUNT), upgraded ? UPGRADED_VOID_AMOUNT : VOID_AMOUNT));
        addToBot(new ApplyPowerAction(player, player, new DoomPower(player, player, upgraded ? UPGRADED_DOOM_AMOUNT : DOOM_AMOUNT), upgraded ? UPGRADED_DOOM_AMOUNT : DOOM_AMOUNT));

        for (AbstractMonster mo : (AbstractDungeon.getCurrRoom()).monsters.monsters)
            addToBot(new ApplyPowerAction(mo, player, new DoomPower(mo, player, upgraded ? UPGRADED_DOOM_AMOUNT : DOOM_AMOUNT), upgraded ? UPGRADED_DOOM_AMOUNT : DOOM_AMOUNT, true, AbstractGameAction.AttackEffect.NONE));
    }
/*
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
    }*/
    
    @Override
    public void upgrade()
    {
        if (!upgraded)
        {
            upgradeName();
            upgradeMagicNumber(UPGRADE_MAGIC_NUM);
            upgradedMagicNumber = true;
            rawDescription = cardStrings.UPGRADE_DESCRIPTION;
            initializeDescription();
        }
    }
}