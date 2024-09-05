package theVacant.orbs;

import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.OrbStrings;
import com.megacrit.cardcrawl.orbs.AbstractOrb;
import com.megacrit.cardcrawl.powers.ArtifactPower;
import com.megacrit.cardcrawl.powers.watcher.VigorPower;
import com.megacrit.cardcrawl.vfx.combat.OrbFlareEffect;
import theVacant.VacantMod;

import static theVacant.VacantMod.makeOrbPath;

public class TopazOrb extends AbstractGemOrb
{
    public static final String ORB_ID = VacantMod.makeID(TopazOrb.class.getSimpleName());
    private static final OrbStrings orbString = CardCrawlGame.languagePack.getOrbString(ORB_ID);
    public static final String[] DESCRIPTIONS = orbString.DESCRIPTION;
    private static final boolean TURN_START_ORB = true, ONE_SIZE_EFFECT = true;

    public TopazOrb(int size)
    {
        super(ORB_ID, orbString.NAME, size, TURN_START_ORB, ONE_SIZE_EFFECT, makeOrbPath("TopazOrb.png"));
        passiveAmount = evokeAmount = 1;
    }

    @Override
    public int getAmount() {
        return 1;
    }

    @Override
    public void triggerPassive(int amount)
    {
        AbstractDungeon.actionManager.addToBottom(// 2.This orb will have a flare effect
                new VFXAction(new OrbFlareEffect(this, OrbFlareEffect.OrbFlareColor.PLASMA), 0.1f));
        chipSound();
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player,
                new ArtifactPower(AbstractDungeon.player, amount), amount));
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
        return new TopazOrb(passiveAmount);
    }
}