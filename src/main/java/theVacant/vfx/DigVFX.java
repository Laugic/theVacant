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
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;
import com.megacrit.cardcrawl.vfx.combat.DamageImpactLineEffect;
import theVacant.util.TextureLoader;

public class DigVFX extends AbstractGameEffect {
    private static final Texture TEXTURE = TextureLoader.getTexture("images/largeRelics/shovel.png"); //Change me to change the image used
    public static final float DURATION = 1F; //Duration of the action, can lengthen or shorten as you wish. All interpolations are relative to this, so changing this is safe
    private static final float HALF_DUR = DURATION/2F;
    private static final float THREE_QUARTER_DUR = DURATION*3/4F;
    private final float sx, sy, tx, ty, ex, ey;
    private final float sa, ta, ea;
    private final TextureAtlas.AtlasRegion img;
    private float x, y;
    private float t;
    private boolean playedSFX;
    private boolean spawnedSparks;
    private boolean bounce;

    public DigVFX() {
        super();
        this.renderBehind = false; //Render over the card
        img = new TextureAtlas.AtlasRegion(TEXTURE, 0, 0, TEXTURE.getWidth(), TEXTURE.getHeight()); //Load the image
        sx = 200 * Settings.scale - img.getRegionWidth()/2F;
        sy = Settings.HEIGHT/3F - img.getRegionHeight()/2F;
        tx = 200 * Settings.scale - img.getRegionWidth()/2F;
        ty = 250 * Settings.scale - img.getRegionHeight()/2F;
        ex = 250 * Settings.scale - img.getRegionWidth()/2F;
        ey = 300 * Settings.scale - img.getRegionHeight()/2F;
        sa = -50;
        ta = -45;
        ea = 15;
        duration = startingDuration = DURATION;
        color = new Color(1.0F, 1.0F, 1.0F, 0.0F); //Start invisible and not null
        bounce = AbstractDungeon.player.drawPile.isEmpty();
    }

    @Override
    public void update() {
        //Increment interpolation timer
        t += Gdx.graphics.getDeltaTime();
        if (t > DURATION/5F && !playedSFX) {
            playedSFX = true;
            CardCrawlGame.sound.playA("ATTACK_WHIFF_2", MathUtils.random(0.7F, 0.8F));
        }
        //Delayed fade out
        if (t >= THREE_QUARTER_DUR) {
            this.color.a = Interpolation.smooth.apply(1.0F, 0.0F, (t - THREE_QUARTER_DUR)/THREE_QUARTER_DUR);
        }
        //Second half of the animation swings back
        if (t >= HALF_DUR) {
            if (bounce) {
                if (!spawnedSparks) {
                    spawnedSparks = true;
                    CardCrawlGame.sound.playA("RELIC_DROP_ROCKY", MathUtils.random(0.7F, 0.8F));
                    for(int i = 0; i < 10; ++i) {
                        AbstractDungeon.effectsQueue.add(new DamageImpactLineEffect(tx, ty));
                    }
                }
                x = Interpolation.swingIn.apply(tx, sx, (t - HALF_DUR)/HALF_DUR);
                y = Interpolation.swingIn.apply(ty, sy, (t - HALF_DUR)/HALF_DUR);
                rotation = Interpolation.pow5In.apply(ta, sa, (t - HALF_DUR)/HALF_DUR);
            } else {
                x = Interpolation.swingIn.apply(tx, ex, (t - HALF_DUR)/HALF_DUR);
                y = Interpolation.swingIn.apply(ty, ey, (t - HALF_DUR)/HALF_DUR);
                rotation = Interpolation.pow5In.apply(ta, ea, (t - HALF_DUR)/HALF_DUR);
            }
            //this.color.a = Interpolation.exp10In.apply(1.0F, 1F, (t - HALF_DUR)/HALF_DUR);

        } else { //First half of the animation grows larger and pauses
            this.color.a = Interpolation.pow5Out.apply(0.0F, 1.0F, t/HALF_DUR);
            x = Interpolation.swingIn.apply(sx, tx, t/HALF_DUR);
            y = Interpolation.swingIn.apply(sy, ty, t/HALF_DUR);
            rotation = Interpolation.pow5Out.apply(sa, ta, t/HALF_DUR);
            //scale = Interpolation.smooth.apply(Settings.scale*1F, Settings.scale*1F, t/HALF_DUR);
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
        //Draw the image
        sb.draw(this.img, this.x - this.img.packedWidth / 2.0F, this.y - this.img.packedHeight / 2.0F, this.img.packedWidth / 2.0F, this.img.packedWidth / 2.0F, this.img.packedWidth, this.img.packedHeight, -this.scale, this.scale, this.rotation);
    }

    @Override
    public void dispose() {}
}
