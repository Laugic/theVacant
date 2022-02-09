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

    private static ArrayList<TooltipInfo> VoidboundTooltip;

    private static final int COST = 2;
    private static final int UPGRADE_MAGIC_NUM = 3;
    private static final int DOOM_AMOUNT = 2;

    public EmbraceDarkness()
    {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        magicNumber = baseMagicNumber = 6;
        exhaust = true;

    }

    @Override
    public void use(AbstractPlayer player, AbstractMonster monster)
    {
        addToBot(new ApplyPowerAction(player, player, new ShroudPower(player, player, magicNumber), magicNumber));
        addToBot(new ApplyPowerAction(player, player, new DoomPower(player, player, DOOM_AMOUNT), DOOM_AMOUNT));
        for (AbstractMonster mo : (AbstractDungeon.getCurrRoom()).monsters.monsters)
            addToBot(new ApplyPowerAction(mo, player, new DoomPower(mo, mo, DOOM_AMOUNT), DOOM_AMOUNT, true, AbstractGameAction.AttackEffect.NONE));
    }

    @Override
    public void upgrade()
    {
        if (!upgraded)
        {
            upgradeName();
            upgradeMagicNumber(UPGRADE_MAGIC_NUM);
            upgradedMagicNumber = true;
            initializeDescription();
        }
    }
}