package theVacant.vfx;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;
import theVacant.util.TextureLoader;

public class SparkleParticle extends AbstractGameEffect {

    // Settings

    // Textures.
    private static final Texture texture = TextureLoader.getTexture("theVacantResources/images/vfx/shine.png");

    private Texture TextureUsed;
    private Texture FinalTexture;

    // Duration.
    private float startingDuration;
    private float duration;

    // Position
    private float x;
    private float y;

    // Where go
    private float speed;
    private float direction = 90f;

    // How move
    private float rotation;
    private float rotation_speed;
    private float flip_speed;
    private float flip_counter;
    private float scale;
    private float totalscale;

    public SparkleParticle(AbstractCreature target) {

        rotation = MathUtils.random(0.0F, 360.0F);
        TextureUsed = texture;

        FinalTexture = TextureUsed;
        startingDuration = MathUtils.random(1.0F, 3.0F);
        duration = startingDuration;
        startingDuration = duration;
        renderBehind = false;
        rotation_speed = MathUtils.random(-24.0F, 24.0F) * Settings.scale;
        flip_speed = MathUtils.random(-2.0F, 2.0F) * Settings.scale;
        flip_counter = MathUtils.random(0.0F, 6.4F);
        speed = MathUtils.random(100.0F, 150.0F) * Settings.scale * .25f;
        scale = MathUtils.random(-1.0F, 1.0F) * Settings.scale * .025f;
        totalscale = MathUtils.random(0.7F, 1.0F);
        if (MathUtils.random(0, 4) == 0 && totalscale >= 0.9F)
            this.totalscale = MathUtils.random(0.9F, 2F);

        // Location
        y = MathUtils.random(target.hb.y, target.hb.y + target.hb.height);
        x = MathUtils.random(target.hb.x, target.hb.x + target.hb.width);

        color = new Color(1, 1, 1, 1F);
    }

    public SparkleParticle(float x, float y, float width, float height) {

        rotation = MathUtils.random(0.0F, 360.0F);
        TextureUsed = texture;

        FinalTexture = TextureUsed;
        startingDuration = MathUtils.random(1.0F, 3.0F);
        duration = startingDuration;
        startingDuration = duration;
        renderBehind = false;
        rotation_speed = MathUtils.random(-24.0F, 24.0F) * Settings.scale;
        flip_speed = MathUtils.random(-2.0F, 2.0F) * Settings.scale;
        flip_counter = MathUtils.random(0.0F, 6.4F);
        speed = MathUtils.random(100.0F, 150.0F) * Settings.scale * .25f;
        scale = MathUtils.random(-1.0F, 1.0F) * Settings.scale * .025f;
        totalscale = MathUtils.random(0.7F, 1.0F);
        if (MathUtils.random(0, 4) == 0 && totalscale >= 0.9F)
            this.totalscale = MathUtils.random(0.9F, 2F);

        // Location
        this.y = MathUtils.random(y, y + height);
        this.x = MathUtils.random(x, x + width);

        color = new Color(1, 1, 1, 1F);
    }

    @Override
    public void render(SpriteBatch sb) {
        if(duration / startingDuration < .8)
            color.a = duration / startingDuration;
        else
            color.a = (1 - (duration / startingDuration)) * 4;
        sb.setColor(this.color);

        final int w = FinalTexture.getWidth();
        final int h = FinalTexture.getHeight();
        final int w2 = FinalTexture.getWidth();
        final int h2 = FinalTexture.getHeight();
        sb.draw(FinalTexture, x - w2 / 2f, y - h2 / 2f,
                w / 2f, h / 2f,
                w2, h2,
                (float) (scale) * Settings.scale * totalscale * .5f, .5F * Settings.scale * totalscale,
                rotation,
                0, 0,
                w2, h2,
                scale > 0, false);
    }

    @Override
    public void dispose() {
    }

    @Override
    public void update() {
        final float dt = Gdx.graphics.getDeltaTime();

        this.rotation += this.rotation_speed * dt;
        this.flip_counter += this.flip_speed * dt;

        this.scale = MathUtils.sin(this.flip_counter);
/*
        if (this.scale > 0.0F) {
            this.FinalTexture = this.TextureUsed;
        } else
            this.FinalTexture = texture_back;
*/
        this.duration -= dt;
        if (this.duration < 0.0F) {
            this.isDone = true;
        }
    }
}