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
    private static final Texture TEXTURE = TextureLoader.getTexture("theVacantResources/images/vfx/DeathSickle.png");
    private static final Texture shade = TextureLoader.getTexture("theVacantResources/images/vfx/blackBack.jpg");
    private static final float DURATION = 1.0f;
    private static final float HALF_DUR = DURATION/2F;
    private final float sx, sy, tx, ty;
    private final float sa, ta, ea;
    private final TextureAtlas.AtlasRegion img;
    private float x, y;
    private float t;

    public ReapVFX() {
        super();
        this.renderBehind = false;
        img = new TextureAtlas.AtlasRegion(TEXTURE, 0, 0, TEXTURE.getWidth(), TEXTURE.getHeight());
        sx = Settings.WIDTH/3F - img.getRegionWidth()/2F;
        sy = Settings.HEIGHT/2F - img.getRegionHeight()/2F;
        tx = Settings.WIDTH*3/5F - img.getRegionWidth()/2F;
        ty = Settings.HEIGHT*3/5F - img.getRegionHeight()/2F;
        sa = -20;
        ta = 40;
        ea = -150;
        duration = startingDuration = DURATION;
        color = new Color(1.0F, 1.0F, 1.0F, 0.0F);
    }

    @Override
    public void update() {
        if (duration == startingDuration) {
            CardCrawlGame.sound.playA("HEART_SIMPLE", MathUtils.random(0.7F, 0.8F));
        }
        t += Gdx.graphics.getDeltaTime();
        if (t >= HALF_DUR) {
            this.color.a = Interpolation.pow5In.apply(1.0F, 0.0F, (t - HALF_DUR)/HALF_DUR);
            x = Interpolation.pow5In.apply(tx, sx, (t - HALF_DUR)/HALF_DUR);
            y = Interpolation.pow5In.apply(ty, sy, (t - HALF_DUR)/HALF_DUR);
            rotation = Interpolation.pow5In.apply(ta, ea, (t - HALF_DUR)/HALF_DUR);
        } else {
            this.color.a = Interpolation.pow5Out.apply(0.0F, 1.0F, t/HALF_DUR);
            x = Interpolation.pow5Out.apply(sx, tx, t/HALF_DUR);
            y = Interpolation.pow5Out.apply(sy, ty, t/HALF_DUR);
            rotation = Interpolation.pow5Out.apply(sa, ta, t/HALF_DUR);
            scale = Interpolation.smooth.apply(Settings.scale*5F, Settings.scale*15F, t/HALF_DUR);
        }
        //rotation = Interpolation.swing.apply(10, -150, t/DURATION);
        //x = Interpolation.swingIn.apply(tx, Settings.WIDTH/2F-img.getRegionWidth()/2F, t/DURATION);
        //y = Interpolation.swingIn.apply(ty, Settings.HEIGHT/2F, t/DURATION);
        //scale = Interpolation.pow5In.apply(Settings.scale*5F, Settings.scale*20F, t/DURATION);

        duration -= Gdx.graphics.getDeltaTime();
        if (duration < 0.0F) {
            isDone = true;
        }
    }

    @Override
    public void render(SpriteBatch sb) {
        sb.setColor(color);
        //sb.setBlendFunction(GL20.GL_SRC_ALPHA, GL20.GL_ONE);
        sb.draw(shade, 0.0F, 0.0F, (float)Settings.WIDTH, (float)Settings.HEIGHT);
        //sb.setBlendFunction(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
        sb.draw(this.img, this.x - this.img.packedWidth / 2.0F, this.y - this.img.packedHeight / 2.0F, this.img.packedWidth / 2.0F, this.img.packedWidth / 2.0F, this.img.packedWidth, this.img.packedHeight, this.scale, this.scale, this.rotation);
    }

    @Override
    public void dispose() {}
}
