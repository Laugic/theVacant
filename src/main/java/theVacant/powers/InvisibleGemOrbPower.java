package theVacant.powers;

import basemod.interfaces.CloneablePowerInterface;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.evacipated.cardcrawl.mod.stslib.powers.interfaces.InvisiblePower;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.orbs.AbstractOrb;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.watcher.VigorPower;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.vfx.combat.OrbFlareEffect;
import theVacant.VacantMod;
import theVacant.actions.ChipOrbAction;
import theVacant.actions.ReduceOrbSizeAction;
import theVacant.orbs.AbstractGemOrb;
import theVacant.orbs.AmethystOrb;
import theVacant.orbs.DiamondOrb;
import theVacant.util.TextureLoader;

public class InvisibleGemOrbPower extends AbstractPower implements InvisiblePower
{
    public AbstractCreature source;

    public static final String POWER_ID = VacantMod.makeID(InvisibleGemOrbPower.class.getSimpleName());
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    private static final Texture tex84 = TextureLoader.getTexture("theVacantResources/images/powers/husk_power84.png");
    private static final Texture tex32 = TextureLoader.getTexture("theVacantResources/images/powers/husk_power32.png");

    public InvisibleGemOrbPower(final AbstractCreature owner, final AbstractCreature source, final int amount)
    {
        name = NAME;
        ID = POWER_ID;

        this.owner = owner;
        this.amount = amount;
        this.source = source;

        type = PowerType.valueOf("NEUTRAL");
        isTurnBased = false;

        this.region128 = new TextureAtlas.AtlasRegion(tex84, 0, 0, 84, 84);
        this.region48 = new TextureAtlas.AtlasRegion(tex32, 0, 0, 32, 32);

        updateDescription();
    }

    @Override
    public void atStartOfTurnPostDraw()
    {
        AbstractPlayer player = AbstractDungeon.player;
        if(player.orbs.size() > 0)
        {
            for (AbstractOrb gem : player.orbs)
            {
                if(gem instanceof AbstractGemOrb)
                    ((AbstractGemOrb) gem).onStartOfTurnPostDraw();
            }
        }
    }
/*
    @Override
    public float atDamageGive(float damage, DamageInfo.DamageType type)
    {

        if (CheckDrawEmpty() && type == DamageInfo.DamageType.NORMAL)
            return damage + GetAllAmethysts();
        return damage;
    }

    @Override
    public float modifyBlock(float blockAmount)
    {
        if (CheckDrawEmpty() && blockAmount > 0.0F)
            return blockAmount + GetAllAmethysts();
        return blockAmount;
    }

    @Override
    public int onAttackedToChangeDamage(DamageInfo info, int damageAmount)
    {
        DiamondOrb diamondOrb = getNextDiamond();
        if (damageAmount > 0 && diamondOrb != null)
        {
            addToTop(new ReduceOrbSizeAction(diamondOrb, 1));

            AbstractDungeon.actionManager.addToBottom(
                    new VFXAction(new OrbFlareEffect(diamondOrb, OrbFlareEffect.OrbFlareColor.PLASMA), 0.1f));
            return 0;
        }
        return damageAmount;
    }

    private DiamondOrb getNextDiamond()
    {
        AbstractPlayer player = AbstractDungeon.player;
        if(player.orbs.size() > 0)
        {
            for (AbstractOrb gem : player.orbs)
            {
                if(gem instanceof DiamondOrb)
                    return (DiamondOrb) gem;
            }
        }
        return null;
    }
*/
    private int GetAllAmethysts()
    {
        int bonusDamage = 0;
        AbstractPlayer player = AbstractDungeon.player;
        if(player.orbs.size() > 0)
        {
            for (AbstractOrb gem : player.orbs)
            {
                if(gem instanceof AmethystOrb)
                    bonusDamage += gem.passiveAmount;
            }
        }
        return bonusDamage;
    }

    public boolean CheckDrawEmpty()
    {
        if (AbstractDungeon.player.drawPile.isEmpty() && !AbstractDungeon.actionManager.turnHasEnded
                && !AbstractDungeon.isScreenUp && (AbstractDungeon.getCurrRoom()).phase == AbstractRoom.RoomPhase.COMBAT)
            return true;
        return false;
    }
}
