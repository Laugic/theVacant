package theVacant.cards.Skills;

import basemod.helpers.CardModifierManager;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.cards.status.Dazed;
import com.megacrit.cardcrawl.cards.status.Wound;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.orbs.AbstractOrb;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.ArtifactPower;
import com.megacrit.cardcrawl.powers.FrailPower;
import com.megacrit.cardcrawl.powers.VulnerablePower;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.vfx.combat.ExplosionSmallEffect;
import com.megacrit.cardcrawl.vfx.combat.VerticalAuraEffect;
import theVacant.VacantMod;
import theVacant.actions.ChipOrbAction;
import theVacant.actions.MineGemAction;
import theVacant.cards.AbstractDynamicCard;
import theVacant.cards.Modifiers.RicochetMod;
import theVacant.characters.TheVacant;
import theVacant.orbs.AbstractGemOrb;
import theVacant.orbs.OnyxOrb;
import theVacant.orbs.RubyOrb;
import theVacant.patches.MultiPreviewFieldPatches;
import theVacant.powers.DoomPower;
import theVacant.powers.RemoveDoomPower;

import java.util.Iterator;

import static theVacant.VacantMod.makeCardPath;

public class AwMan extends AbstractDynamicCard
{

    public static final String ID = VacantMod.makeID(AwMan.class.getSimpleName());
    public static final String IMG = makeCardPath("AwMan.png");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);

    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = TheVacant.Enums.COLOR_GOLD;

    private static final int COST = 0, FRAIL = 2;

    public AwMan()
    {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        magicNumber = baseMagicNumber = 2;
    }

    @Override
    public void use(AbstractPlayer player, AbstractMonster monster)
    {
        AbstractDungeon.actionManager.addToBottom(new SFXAction("theVacant:awman"));

        addToBot(new VFXAction(new ExplosionSmallEffect(player.hb.cX, player.hb.cY), 0.1F));
        //addToBot(new ApplyPowerAction(player, player, new FrailPower(player, FRAIL, false), FRAIL));

        addToBot(new ApplyPowerAction(player, player, new DoomPower(player, player, 2)));
        if(!player.hasPower(ArtifactPower.POWER_ID))
            addToBot(new ApplyPowerAction(player, player, new RemoveDoomPower(player, player, 2)));

        addToBot(new MineGemAction(new RubyOrb(magicNumber)));
        addToBot(new MineGemAction(new OnyxOrb(magicNumber)));

    }

    @Override
    public void upgrade()
    {
        if (!upgraded)
        {
            upgradeName();
            upgradeMagicNumber(1);
            initializeDescription();
        }
    }
}