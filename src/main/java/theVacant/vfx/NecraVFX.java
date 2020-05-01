package theVacant.vfx;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;
import com.megacrit.cardcrawl.vfx.BorderFlashEffect;
import com.megacrit.cardcrawl.vfx.BorderLongFlashEffect;
import com.megacrit.cardcrawl.vfx.ExhaustEmberEffect;
import com.megacrit.cardcrawl.vfx.scene.TorchParticleXLEffect;

public class NecraVFX extends AbstractGameEffect
{
    private float x;
    private float y;
    private static final float X_RADIUS = 150.0F * Settings.scale;
    private static final float Y_RADIUS = 250.0F * Settings.scale;
    private boolean flashedBorder = true;
    private Vector2 v1 = new Vector2(0.0F, 0.0F);
    private Vector2 v2 = new Vector2(0.0F, 0.0F);
    private int phase = 0;
    private float baseDuration = 0;

    public NecraVFX(float x, float y)
    {
        this.duration = 0.5F;
        this.baseDuration = this.duration;
        this.x = x;
        this.y = y;
        this.renderBehind = false;
    }

    public void update()
    {
        if (this.flashedBorder)
        {
            CardCrawlGame.sound.play("ATTACK_FLAME_BARRIER", 0.05F);
            this.flashedBorder = false;
            AbstractDungeon.effectsQueue.add(new BorderLongFlashEffect(Color.BLACK));
            AbstractDungeon.effectsQueue.add(new BorderLongFlashEffect(Color.CYAN));
            AbstractDungeon.effectsQueue.add(new BorderFlashEffect(Color.WHITE));
        }
        CheckPhase();
        MakeParticle(this.v1.x, this.v1.y);
        MakeParticle(this.v2.x, this.v2.y);
        DurationCheck();
    }

    private void CheckPhase()
    {
        if(this.duration > this.baseDuration / 2)
            Phase1();
        else
            Phase2();
    }

    private void Phase1()
    {
        float curDir = (this.baseDuration - this.duration)/this.baseDuration;
        this.v1.x = -X_RADIUS + (X_RADIUS / 2) * MathUtils.cos(curDir * -3.14159265f);
        this.v1.y = Y_RADIUS / 2 + (Y_RADIUS / 2) * MathUtils.sin(curDir * -3.14159265f);
        this.v2.x = X_RADIUS - (X_RADIUS / 2) * MathUtils.cos(curDir * -3.14159265f);
        this.v2.y = Y_RADIUS / 2 + (Y_RADIUS / 2) * MathUtils.sin(curDir * -3.14159265f);
    }

    private void Phase2()
    {
        float curDir = (this.baseDuration - this.duration)/(this.baseDuration);
        this.v1.x = -3 * X_RADIUS + X_RADIUS * curDir * 4;
        this.v1.y = Y_RADIUS / 3;
        if(this.duration > this.baseDuration / 2)
            this.v2 = this.v1;
        else
        {
            this.v2.x = 0;
            this.v2.y = Y_RADIUS / 3 - (Y_RADIUS - Y_RADIUS * curDir);
        }
    }

    private void MakeParticle(float vX, float vY)
    {
        float shift = 4f;
        AbstractDungeon.effectsQueue.add(new TorchParticleXLEffect(this.x + vX +
                MathUtils.random(-shift*2, shift*2) * Settings.scale, this.y + vY +
                MathUtils.random(-shift, shift) * Settings.scale));
        AbstractDungeon.effectsQueue.add(new TorchParticleXLEffect(this.x + vX +
                MathUtils.random(-shift*2, shift*2) * Settings.scale, this.y + vY +
                MathUtils.random(-shift, shift) * Settings.scale));
        AbstractDungeon.effectsQueue.add(new TorchParticleXLEffect(this.x + vX +
                MathUtils.random(-shift*2, shift*2) * Settings.scale, this.y + vY +
                MathUtils.random(-shift, shift) * Settings.scale));
        AbstractDungeon.effectsQueue.add(new ExhaustEmberEffect(this.x + vX, this.y + vY));
    }

    private void DurationCheck()
    {
        this.duration -= Gdx.graphics.getDeltaTime();
        if (this.duration < 0.0F)
            this.isDone = true;
    }

    public void render(SpriteBatch sb) {}

    public void dispose() {}
}
