package theVacant.orbs;

import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.OrbStrings;
import com.megacrit.cardcrawl.orbs.AbstractOrb;
import com.megacrit.cardcrawl.vfx.combat.OrbFlareEffect;
import theVacant.VacantMod;
import theVacant.actions.VacantMillAction;
import theVacant.powers.VoidPower;

import static theVacant.VacantMod.makeOrbPath;

public class OpalOrb extends AbstractGemOrb
{
    public static final String ORB_ID = VacantMod.makeID(OpalOrb.class.getSimpleName());
    private static final OrbStrings orbString = CardCrawlGame.languagePack.getOrbString(ORB_ID);
    public static final String[] DESCRIPTIONS = orbString.DESCRIPTION;
    private static boolean TURN_START_ORB = true;

    public OpalOrb(int size)
    {
        super(ORB_ID, orbString.NAME, size, TURN_START_ORB, makeOrbPath("OpalOrb.png"));
    }

    @Override
    public void TriggerPassive()
    {
        AbstractDungeon.actionManager.addToBottom(// 2.This orb will have a flare effect
                new VFXAction(new OrbFlareEffect(this, OrbFlareEffect.OrbFlareColor.PLASMA), 0.1f));
        AbstractDungeon.actionManager.addToBottom(new SFXAction("ORB_LIGHTNING_CHANNEL"));
        AbstractDungeon.actionManager.addToBottom(new GainEnergyAction(1));
    }

    @Override
    public void EvokeGem()
    {
        AbstractDungeon.actionManager.addToBottom(new GainEnergyAction(evokeAmount));
        AbstractDungeon.actionManager.addToBottom(new SFXAction("ORB_LIGHTNING_CHANNEL"));
    }

    @Override
    public void updateDescription()
    {
        applyFocus();
        description = DESCRIPTIONS[0] + 1 + DESCRIPTIONS[1] + evokeAmount + DESCRIPTIONS[2];
    }

    @Override
    public AbstractOrb makeCopy()
    {
        return new AmethystOrb(passiveAmount);
    }
}