package theVacant.vfx;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.Hitbox;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;
import theVacant.util.TextureLoader;

public class ParticleEffect extends AbstractGameEffect {
    private float x, y, oX, oY;
    public float alpha, fadeOut, xVel = 0, yVel = 0, scaleMult = 1f;
    private AtlasRegion img;
    public Texture texture;
    private int frame, frames, prevFrame;

    public ParticleEffect(float x, float y, Texture texture, int frames, float life){
        this(x, y, texture, frames, life, false, -1);
    }

    public ParticleEffect(float x, float y, Texture texture, int frames, float life, boolean randomize, float fade) {
        duration = startingDuration = life;
        img = new TextureAtlas.AtlasRegion(texture, 0, 0, texture.getWidth(), texture.getHeight() / frames);
        if(randomize)
            scale = MathUtils.random(0.8F, 1.2F) * Settings.scale;
        else
            scale = 1;
        if(randomize)
        {
            oX += MathUtils.random(-50.0F, 50.0F) * Settings.scale;
            oY += MathUtils.random(-50.0F, 50.0F) * Settings.scale;
        }
        oX -= img.packedWidth / 2.0F;
        oY -= img.packedHeight / 2.0F;
        if(randomize)
            renderBehind = MathUtils.randomBoolean(0.2F + (scale - 0.5F));
        this.x = x;
        this.y = y;
        this.texture = texture;
        this.frames = frames;
        frame = prevFrame = 0;
        rotation = 0;
        alpha = 1;
        fadeOut = fade;
    }

    @Override
    public void update() {
        duration -= Gdx.graphics.getDeltaTime();
        x += xVel * Gdx.graphics.getDeltaTime();
        y += yVel * Gdx.graphics.getDeltaTime();
        frame = frames - (int)((duration / startingDuration) * frames);
        if(frames == 1)
            frame = 0;
        if(frame != prevFrame)
        {
            prevFrame = frame;
            img = new TextureAtlas.AtlasRegion(texture, 0, frame * texture.getHeight() / frames, texture.getWidth(), texture.getHeight() / frames);
        }
        if(duration < fadeOut)
            alpha = duration / fadeOut;
        if (duration < 0.0F)
            isDone = true;
    }

    @Override
    public void render(SpriteBatch sb) {
        sb.setColor(new Color(1, 1, 1, alpha));
        sb.draw(img, x + oX, y + oY, (float)img.packedWidth / 2.0F, (float)img.packedHeight / 2.0F, (float)img.packedWidth, (float)img.packedHeight, scale * scaleMult, scale * scaleMult, rotation);
    }

    public void setRotation(float rotation){
        this.rotation = rotation;
    }

    public void setX(float X){
        x = X;
    }

    public void setY(float Y){
        y = Y;
    }

    @Override
    public void dispose() {
    }
}
