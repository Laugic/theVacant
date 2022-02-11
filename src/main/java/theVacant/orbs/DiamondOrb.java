package theVacant.orbs;

import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.OrbStrings;
import com.megacrit.cardcrawl.orbs.AbstractOrb;
import com.megacrit.cardcrawl.powers.BufferPower;
import com.megacrit.cardcrawl.powers.IntangiblePlayerPower;
import com.megacrit.cardcrawl.powers.MetallicizePower;
import com.megacrit.cardcrawl.vfx.combat.OrbFlareEffect;
import theVacant.VacantMod;
import theVacant.actions.ReduceOrbSizeAction;
import theVacant.powers.TemperancePower;
import theVacant.vfx.ChipVFX;

import static theVacant.VacantMod.makeOrbPath;

public class DiamondOrb extends AbstractGemOrb
{
    public static final String ORB_ID = VacantMod.makeID(DiamondOrb.class.getSimpleName());
    private static final OrbStrings orbString = CardCrawlGame.languagePack.getOrbString(ORB_ID);
    public static final String[] DESCRIPTIONS = orbString.DESCRIPTION;
    private static boolean TURN_START_ORB = false, ONE_SIZE_EFFECT = true;

    public DiamondOrb(int size)
    {
        super(ORB_ID, orbString.NAME, size, TURN_START_ORB, ONE_SIZE_EFFECT, makeOrbPath("DiamondOrb.png"));
    }

    @Override
    public void triggerPassive(int amount)
    {
        chipSound();
        AbstractDungeon.actionManager.addToBottom(
            new VFXAction(new OrbFlareEffect(this, OrbFlareEffect.OrbFlareColor.PLASMA), 0.1f));
    }

    @Override
    public void onChip(int chips)
    {
        AbstractDungeon.actionManager.addToBottom(new VFXAction(new ChipVFX(this.hb.cX, this.hb.cY), ChipVFX.DURATION));
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player,
                new BufferPower(AbstractDungeon.player, chips)));
        AbstractDungeon.actionManager.addToBottom(
                new VFXAction(new OrbFlareEffect(this, OrbFlareEffect.OrbFlareColor.PLASMA), 0.1f));
        chipSound();
        AbstractDungeon.actionManager.addToBottom(new ReduceOrbSizeAction(this, chips));
    }

    @Override
    public void updateDescription()
    {
        applyFocus();
        description = DESCRIPTIONS[0];
    }

    @Override
    public AbstractOrb makeCopy()
    {
        return new DiamondOrb(passiveAmount);
    }
}