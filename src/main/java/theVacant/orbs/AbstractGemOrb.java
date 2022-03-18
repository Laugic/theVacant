package theVacant.orbs;

import basemod.abstracts.CustomOrb;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.orbs.AbstractOrb;
import com.megacrit.cardcrawl.random.Random;
import com.megacrit.cardcrawl.vfx.combat.DarkOrbActivateEffect;
import com.megacrit.cardcrawl.vfx.combat.DarkOrbPassiveEffect;
import theVacant.actions.ReduceOrbSizeAction;
import theVacant.relics.RagRelic;
import theVacant.vfx.ChipVFX;

public abstract class AbstractGemOrb extends CustomOrb
{
    // Animation Rendering Numbers - You can leave these at default, or play around with them and see what they change.
    private float vfxTimer = 1.0f;
    private float vfxIntervalMin = 0.1f;
    private float vfxIntervalMax = 0.4f;
    private static final float ORB_WAVY_DIST = 0.04f;
    private static final float PI_4 = 12.566371f;

    public boolean turnStartOrb, oneSizeEffect;

    public AbstractGemOrb(String ID, String name, int size, boolean turnStart, boolean oneSize, String path)
    {
        super(ID, name, size, size, "", "", path);

        turnStartOrb = turnStart;
        oneSizeEffect = oneSize;
        updateDescription();

        angle = MathUtils.random(360.0f); // More Animation-related Numbers
        channelAnimTimer = 0.5f;
    }


    @Override //if you want to ignore Focus
    public void applyFocus()
    {
        passiveAmount = basePassiveAmount;
        evokeAmount = baseEvokeAmount;
    }

    @Override
    public void onEvoke()
    {
        onChip(passiveAmount);
        //evokeGem();
        //ReduceSize(baseEvokeAmount);
        //AbstractDungeon.actionManager.addToBottom(new DecreaseMaxOrbAction(1));
        //RemoveSpecificGem(player.orbs.indexOf(this));
    }

    public void onStartOfTurnPostDraw()
    {
        if(turnStartOrb)
            triggerPassive(oneSizeEffect ? 1 : passiveAmount);
        if(!AbstractDungeon.player.hasRelic(RagRelic.ID))
            AbstractDungeon.actionManager.addToBottom(new ReduceOrbSizeAction(this));
        updateDescription();
    }

    public void onChip(int chips)
    {
        AbstractDungeon.actionManager.addToBottom(new VFXAction(new ChipVFX(this, this.hb.cX, this.hb.cY), ChipVFX.DURATION/2F));
        triggerPassive(getAmountFromChip(chips));
        AbstractDungeon.actionManager.addToBottom(new ReduceOrbSizeAction(this, chips));
    }

    public int getAmountFromChip(int amount)
    {
        int tempSize = passiveAmount;
        int chipTotal = 0;
        for(int i = 0; i < amount; ++i)
        {
            if(tempSize > 0)
            {
                chipTotal += oneSizeEffect ? 1 : tempSize;
                tempSize--;
            }
        }
        return chipTotal;
    }

    public abstract void triggerPassive(int amount);

    public void reduceSize()
    {
        reduceSize(1);
    }

    public void reduceSize(int amount)
    {
        passiveAmount = basePassiveAmount -= Math.min(amount, basePassiveAmount);
        evokeAmount = baseEvokeAmount -= Math.min(amount, baseEvokeAmount);
        if(passiveAmount <= 0 || evokeAmount <= 0)
            removeSpecificGem(this);
        updateDescription();
    }

    public void removeSpecificGem(AbstractOrb orb)
    {
        AbstractPlayer player = AbstractDungeon.player;
        if(player.orbs.contains(orb))
        {
            AbstractDungeon.player.orbs.remove(orb);
            if (player.maxOrbs > 0)
                player.maxOrbs--;
            if (player.maxOrbs < 0)
                player.maxOrbs = 0;
            for(int i = 0; i < player.orbs.size(); ++i)
                (player.orbs.get(i)).setSlot(i, player.maxOrbs);
        }
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

    protected static void chipSound()
    {
        Random rand = new Random();
        int chip = rand.random(4);
        switch (chip)
        {
            case 0:
                AbstractDungeon.actionManager.addToBottom(new SFXAction("theVacant:gem1"));
                break;
            case 1:
                AbstractDungeon.actionManager.addToBottom(new SFXAction("theVacant:gem2"));
                break;
            case 2:
                AbstractDungeon.actionManager.addToBottom(new SFXAction("theVacant:gem3"));
                break;
            default:
                AbstractDungeon.actionManager.addToBottom(new SFXAction("theVacant:gem4"));
        }
    }

    @Override
    public void triggerEvokeAnimation()
    {
        AbstractDungeon.effectsQueue.add(new DarkOrbActivateEffect(cX, cY));
    }

    @Override
    public void playChannelSFX()
    {
        CardCrawlGame.sound.play("theVacant:gemSpawn", 0.1f);
    }
}
