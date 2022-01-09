package theVacant.cards.Skills;

import basemod.BaseMod;
import basemod.helpers.TooltipInfo;
import basemod.patches.com.megacrit.cardcrawl.screens.SingleCardViewPopup.TitleFontSize;
import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.IntangiblePlayerPower;
import com.megacrit.cardcrawl.vfx.BorderFlashEffect;
import com.megacrit.cardcrawl.vfx.combat.VerticalAuraEffect;
import theVacant.VacantMod;
import theVacant.actions.MineGemAction;
import theVacant.actions.PlayDiamondIsUnbreakableAction;
import theVacant.actions.PlayExhumationAction;
import theVacant.actions.VacantMillAction;
import theVacant.cards.AbstractDynamicCard;
import theVacant.characters.TheVacant;
import theVacant.orbs.DiamondOrb;
import theVacant.powers.TemperancePower;
import theVacant.powers.VoidPower;
import theVacant.util.KeywordManager;

import java.util.ArrayList;
import java.util.List;

import static theVacant.VacantMod.makeCardPath;

public class DiamondIsUnbreakable extends AbstractDynamicCard
{

    public static final String ID = VacantMod.makeID(DiamondIsUnbreakable.class.getSimpleName());
    public static final String IMG = makeCardPath("DiamondIsUnbreakable.png");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;

    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = TheVacant.Enums.COLOR_GOLD;

    private static final int COST = -1;

    public DiamondIsUnbreakable()
    {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        exhaust = true;
    }

    @Override
    public float getTitleFontSize()
    {
        return 18;
    }

    @Override
    public void use(AbstractPlayer player, AbstractMonster monster)
    {
//        addToBot(new VFXAction(player, new VerticalAuraEffect(Color.CYAN, player.hb.cX, player.hb.cY), 0.33F));
        addToBot(new VFXAction(player, new BorderFlashEffect(Color.WHITE), 0.3F, true));
        addToBot(new PlayDiamondIsUnbreakableAction(freeToPlayOnce, energyOnUse, upgraded?1:0));
        //addToBot(new MineGemAction(new DiamondOrb(magicNumber)));

//        addToBot(new ApplyPowerAction(player, player, new IntangiblePlayerPower(player, 2), 2));
//        addToBot(new ApplyPowerAction(player, player, new TemperancePower(player, player, magicNumber), magicNumber));
    }

    @Override
    public void upgrade()
    {
        if (!upgraded)
        {
            upgradeName();
            rawDescription = cardStrings.UPGRADE_DESCRIPTION;
            initializeDescription();
        }
    }
}