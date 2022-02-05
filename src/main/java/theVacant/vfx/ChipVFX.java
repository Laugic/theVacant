package theVacant.vfx;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;
import com.megacrit.cardcrawl.vfx.combat.DamageImpactLineEffect;
import theVacant.util.TextureLoader;

public class ChipVFX extends AbstractGameEffect {
    private static final Texture t = TextureLoader.getTexture("theVacantResources/images/vfx/Pickaxe.png");
    private final float ax, ay, tx, ty; //Actual xy (for sparks vfx), Target xy (for pickaxe aiming)
    private final AtlasRegion img;
    private float x, y;
    private boolean playedSFX;

    public ChipVFX(float x, float y) {
        super();
        img = new AtlasRegion(t, 0, 0, t.getWidth(), t.getHeight());
        ax = x;
        ay = y;
        tx = x - img.getRegionWidth()/2F;
        ty = y + img.getRegionHeight()/2F;
        duration = startingDuration = Settings.ACTION_DUR_MED;
        color = new Color(1.0F, 1.0F, 1.0F, 1.0F);
    }

    @Override
    public void update() {
        if (duration == startingDuration) {
            CardCrawlGame.sound.playA("ATTACK_WHIFF_2", MathUtils.random(0.7F, 0.8F));
        }
        duration -= Gdx.graphics.getDeltaTime();
        if (duration < startingDuration / 4.0F) {
            color.a = duration / (startingDuration / 2.0F);
            if (!playedSFX) {
                playedSFX = true;
                //CardCrawlGame.sound.playA("RELIC_DROP_ROCKY", MathUtils.random(0.7F, 0.8F));
            }
        }
        rotation = Interpolation.swing.apply(90, -25, (startingDuration - duration) * 2);
        x = Interpolation.swing.apply(tx-30*Settings.scale, tx, (startingDuration - duration) * 2);
        y = Interpolation.swing.apply(ty+30*Settings.scale, ty, (startingDuration - duration) * 2);
        scale = Interpolation.swingOut.apply(Settings.scale*1.3F, Settings.scale*1.5F, (startingDuration - duration) * 2);

        if (duration < 0.0F) {
            isDone = true;
            color.a = 0.0F;
            CardCrawlGame.sound.playA("RELIC_DROP_ROCKY", MathUtils.random(0.7F, 0.8F));
            for(int i = 0; i < 3; ++i) {
                AbstractDungeon.effectsQueue.add(new DamageImpactLineEffect(ax, ay));
            }
        }
    }

    @Override
    public void render(SpriteBatch sb) {
        sb.setColor(color);
        sb.draw(this.img, this.x - this.img.packedWidth / 2.0F, this.y - this.img.packedHeight / 2.0F, this.img.packedWidth / 2.0F, this.img.packedWidth / 2.0F, this.img.packedWidth, this.img.packedHeight, this.scale, this.scale, this.rotation);
    }

    @Override
    public void dispose() {}
}
