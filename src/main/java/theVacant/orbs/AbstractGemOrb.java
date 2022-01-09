package theVacant.orbs;

import basemod.abstracts.CustomOrb;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.defect.DecreaseMaxOrbAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.orbs.AbstractOrb;
import com.megacrit.cardcrawl.orbs.EmptyOrbSlot;
import com.megacrit.cardcrawl.vfx.combat.DarkOrbActivateEffect;
import com.megacrit.cardcrawl.vfx.combat.DarkOrbPassiveEffect;
import theVacant.actions.ReduceOrbSizeAction;
import theVacant.powers.InvisibleGemOrbPower;

import java.util.Collections;

public abstract class AbstractGemOrb extends CustomOrb
{
    // Animation Rendering Numbers - You can leave these at default, or play around with them and see what they change.
    private float vfxTimer = 1.0f;
    private float vfxIntervalMin = 0.1f;
    private float vfxIntervalMax = 0.4f;
    private static final float ORB_WAVY_DIST = 0.04f;
    private static final float PI_4 = 12.566371f;

    public boolean turnStartOrb;

    public AbstractGemOrb(String ID, String name, int size, boolean turnStart, String path)
    {
        super(ID, name, size, size, "", "", path);

        turnStartOrb = turnStart;
        updateDescription();

        angle = MathUtils.random(360.0f); // More Animation-related Numbers
        channelAnimTimer = 0.5f;
    }

    /*
    @Override //if you want to ignore Focus
    public void applyFocus()
    {
        passiveAmount = basePassiveAmount;
        evokeAmount = baseEvokeAmount;
    }*/

    @Override
    public void onEvoke()
    {
        EvokeGem();
        //ReduceSize(baseEvokeAmount);
        AbstractDungeon.actionManager.addToBottom(new DecreaseMaxOrbAction(1));
        //RemoveSpecificGem(player.orbs.indexOf(this));
    }

    public void onStartOfTurnPostDraw()
    {
        if(turnStartOrb)
        {
            TriggerPassive();
            AbstractDungeon.actionManager.addToBottom(new ReduceOrbSizeAction(this));
            updateDescription();
        }
    }

    public abstract void TriggerPassive();

    public void ReduceSize()
    {
        ReduceSize(1);
    }

    public void ReduceSize(int amount)
    {
        passiveAmount = basePassiveAmount -= amount;
        evokeAmount = baseEvokeAmount -= amount;
        if(passiveAmount == 0 || evokeAmount == 0)
            RemoveSpecificGem(AbstractDungeon.player.orbs.indexOf(this));
        updateDescription();
    }

    public void RemoveSpecificGem(int slot)
    {
        AbstractPlayer player = AbstractDungeon.player;
        if (!player.orbs.isEmpty() && !(player.orbs.get(slot) instanceof EmptyOrbSlot))
        {
            if(player.orbs.size() > 1 && slot != player.orbs.size() - 1)
            {
                for (int i = slot; i < player.orbs.size() - 1; i++)
                    Collections.swap(player.orbs, i, i + 1);
            }

            AbstractOrb orbSlot = new EmptyOrbSlot((player.orbs.get(player.orbs.size() - 1)).cX, (player.orbs.get(player.orbs.size() - 1)).cY);

            if(player.orbs.size() > 1)
                player.orbs.set(player.orbs.size() - 1, orbSlot);
            else
                player.orbs.set(0, orbSlot);

            for(int i = 0; i < player.orbs.size(); ++i)
                (player.orbs.get(i)).setSlot(i, player.maxOrbs);

            onRemove();
            AbstractDungeon.player.decreaseMaxOrbSlots(1);
//            Collections.swap(player.orbs, slot, player.orbs.size() - 1);
//
//            for(int i = 0; i < player.orbs.size(); ++i)
//                (player.orbs.get(i)).setSlot(i, player.maxOrbs);
        }
    }

    public void onRemove()
    {

    }

    @Override
    public void updateAnimation()
    {
        super.updateAnimation();
        angle += Gdx.graphics.getDeltaTime() * 45.0f;
        vfxTimer -= Gdx.graphics.getDeltaTime();
        if (vfxTimer < 0.0f)
        {
            AbstractDungeon.effectList.add(new DarkOrbPassiveEffect(cX, cY)); // This is the purple-sparkles in the orb. You can change this to whatever fits your orb.
            vfxTimer = MathUtils.random(vfxIntervalMin, vfxIntervalMax);
        }

        AbstractPlayer player = AbstractDungeon.player;
        if(!player.hasPower(InvisibleGemOrbPower.POWER_ID))
            player.powers.add(new InvisibleGemOrbPower(player, player, 1));
    }

    // Render the orb.
    @Override
    public void render(SpriteBatch sb) {
        sb.setColor(new Color(1.0f, 1.0f, 1.0f, 1.0f));
        sb.draw(img, cX - 48.0f, cY - 48.0f + bobEffect.y, 48.0f, 48.0f, 96.0f, 96.0f, scale + MathUtils.sin(angle / PI_4) * ORB_WAVY_DIST * Settings.scale, scale, angle, 0, 0, 96, 96, false, false);
        //sb.setColor(new Color(1.0f, 1.0f, 1.0f, this.c.a / 2.0f));
        //sb.setBlendFunction(770, 1);
        //sb.draw(img, cX - 48.0f, cY - 48.0f + bobEffect.y, 48.0f, 48.0f, 96.0f, 96.0f, scale, scale + MathUtils.sin(angle / PI_4) * ORB_WAVY_DIST * Settings.scale, -angle, 0, 0, 96, 96, false, false);
        //sb.setBlendFunction(770, 771);
        renderText(sb);
        hb.render(sb);
    }

    @Override
    public void triggerEvokeAnimation()
    {
        AbstractDungeon.effectsQueue.add(new DarkOrbActivateEffect(cX, cY));
    }

    @Override
    public void playChannelSFX()
    {
        CardCrawlGame.sound.play("ATTACK_FIRE", 0.1f);
    }

    public abstract void EvokeGem();
}
