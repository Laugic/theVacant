package theVacant.vfx;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;
import theVacant.util.TextureLoader;

public class ReapVFX extends AbstractGameEffect {
    private static final Texture TEXTURE = TextureLoader.getTexture("theVacantResources/images/vfx/DeathSickle.png"); //Change me to change the image used
    private static final Texture BACKGROUND = TextureLoader.getTexture("theVacantResources/images/vfx/blackBack.jpg"); //Change me to change the background fade
    private static final float DURATION = 0.75f; //Duration of the action, can lengthen or shorten as you wish. All interpolations are relative to this, so changing this is safe
    private static final float HALF_DUR = DURATION/2F;
    private final float sx, sy, tx, ty;
    private final float sa, ta, ea;
    private final TextureAtlas.AtlasRegion img;
    private float x, y;
    private float t;

    public ReapVFX() {
        super();
        this.renderBehind = false; //Render over the card
        img = new TextureAtlas.AtlasRegion(TEXTURE, 0, 0, TEXTURE.getWidth(), TEXTURE.getHeight()); //Load the image
        sx = Settings.WIDTH/3F - img.getRegionWidth()/2F; // x coord scythe spawns at and swings back to
        sy = Settings.HEIGHT/2F - img.getRegionHeight()/2F; // y coord scythe spawns at and swings back to
        tx = Settings.WIDTH*3/5F - img.getRegionWidth()/2F; // x coord scythe pauses at
        ty = Settings.HEIGHT*3/5F - img.getRegionHeight()/2F; // y coord scythe pauses at
        sa = -20; //Angle scythe spawns at
        ta = 60; //Angle scythe pauses at
        ea = -150; //Angle scythe swings to
        duration = startingDuration = DURATION;
        color = new Color(1.0F, 1.0F, 1.0F, 0.0F); //Start invisible and not null
    }

    @Override
    public void update() {
        //Play sfx at the start
        if (duration == startingDuration) {
            CardCrawlGame.sound.playA("HEART_SIMPLE", MathUtils.random(0.7F, 0.8F));
        }
        //Increment interpolation timer
        t += Gdx.graphics.getDeltaTime();
        //Second half of the animation swings back
        if (t >= HALF_DUR) {
            this.color.a = Interpolation.pow5In.apply(1.0F, 0.0F, (t - HALF_DUR)/HALF_DUR);
            x = Interpolation.pow5In.apply(tx, sx, (t - HALF_DUR)/HALF_DUR);
            y = Interpolation.pow5In.apply(ty, sy, (t - HALF_DUR)/HALF_DUR);
            rotation = Interpolation.pow5In.apply(ta, ea, (t - HALF_DUR)/HALF_DUR);
        } else { //First half of the animation grows larger and pauses
            this.color.a = Interpolation.pow5Out.apply(0.0F, 1.0F, t/HALF_DUR);
            x = Interpolation.pow5Out.apply(sx, tx, t/HALF_DUR);
            y = Interpolation.pow5Out.apply(sy, ty, t/HALF_DUR);
            rotation = Interpolation.pow5Out.apply(sa, ta, t/HALF_DUR);
            scale = Interpolation.smooth.apply(Settings.scale*5F, Settings.scale*10F, t/HALF_DUR);
        }
        //Reduce duration and end action if duration is 0
        duration -= Gdx.graphics.getDeltaTime();
        if (duration <= 0.0F) {
            isDone = true;
        }
    }

    @Override
    public void render(SpriteBatch sb) {
        //Set the color so your alpha is applied
        sb.setColor(color);
        //Draw the background
        sb.draw(BACKGROUND, 0.0F, 0.0F, (float)Settings.WIDTH, (float)Settings.HEIGHT);
        //Draw the image
        sb.draw(this.img, this.x - this.img.packedWidth / 2.0F, this.y - this.img.packedHeight / 2.0F, this.img.packedWidth / 2.0F, this.img.packedWidth / 2.0F, this.img.packedWidth, this.img.packedHeight, this.scale, this.scale, this.rotation);
    }

    @Override
    public void dispose() {}
}
