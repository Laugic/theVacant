package theVacant.orbs;

import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.OrbStrings;
import com.megacrit.cardcrawl.orbs.AbstractOrb;
import com.megacrit.cardcrawl.powers.DexterityPower;
import com.megacrit.cardcrawl.vfx.combat.OrbFlareEffect;
import theVacant.VacantMod;
import theVacant.powers.TemperancePower;

import static theVacant.VacantMod.makeOrbPath;

public class SapphireOrb extends AbstractGemOrb
{
    public static final String ORB_ID = VacantMod.makeID(SapphireOrb.class.getSimpleName());
    private static final OrbStrings orbString = CardCrawlGame.languagePack.getOrbString(ORB_ID);
    public static final String[] DESCRIPTIONS = orbString.DESCRIPTION;
    private static boolean TURN_START_ORB = true, ONE_SIZE_EFFECT = true;

    public SapphireOrb(int size)
    {
        super(ORB_ID, orbString.NAME, size, TURN_START_ORB, ONE_SIZE_EFFECT, makeOrbPath("SapphireOrb.png"));
    }

    @Override
    public void triggerPassive(int amount)
    {
        AbstractDungeon.actionManager.addToBottom(
                new VFXAction(new OrbFlareEffect(this, OrbFlareEffect.OrbFlareColor.DARK), 0.1f));
        chipSound();
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player,
                new TemperancePower(AbstractDungeon.player, AbstractDungeon.player, amount * 3), amount * 3));
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
        return new SapphireOrb(passiveAmount);
    }
}