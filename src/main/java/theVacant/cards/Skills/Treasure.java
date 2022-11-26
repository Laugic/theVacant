package theVacant.cards.Skills;

import basemod.helpers.TooltipInfo;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.ShineSparkleEffect;
import theVacant.VacantMod;
import theVacant.actions.MineGemAction;
import theVacant.cards.AbstractDynamicCard;
import theVacant.characters.TheVacant;
import theVacant.orbs.EmeraldOrb;
import theVacant.orbs.OpalOrb;
import theVacant.orbs.RubyOrb;

import java.util.ArrayList;

import static theVacant.VacantMod.makeCardPath;

public class Treasure extends AbstractDynamicCard
{

    public static final String ID = VacantMod.makeID(Treasure.class.getSimpleName());
    public static final String IMG = makeCardPath("Treasure.png");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);

    private static final CardRarity RARITY = CardRarity.COMMON;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = TheVacant.Enums.COLOR_GOLD;

    private static final int COST = 0;

    private static ArrayList<TooltipInfo> ExtraTooltip;

    public Treasure()
    {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        magicNumber = baseMagicNumber = 1;
        exhaust = true;
    }

    @Override
    public void use(AbstractPlayer player, AbstractMonster monster)
    {
        addToBot(new MineGemAction(new EmeraldOrb(magicNumber)));
        addToBot(new MineGemAction(new OpalOrb(magicNumber)));
        for(int i = 0; i < 20; ++i)
            AbstractDungeon.effectsQueue.add(new ShineSparkleEffect(player.hb.x + (float)Math.random() * player.hb.width, player.hb.y + (float)Math.random() * player.hb.height));
    }

    @Override
    public void upgrade()
    {
        if (!upgraded)
        {
            upgradeName();
            exhaust = false;
            rawDescription = cardStrings.UPGRADE_DESCRIPTION;
            initializeDescription();
        }
    }
}