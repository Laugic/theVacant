package theVacant.vfx;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.orbs.AbstractOrb;
import com.megacrit.cardcrawl.random.Random;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;
import com.megacrit.cardcrawl.vfx.ShineSparkleEffect;
import com.megacrit.cardcrawl.vfx.combat.DamageImpactLineEffect;
import theVacant.orbs.AbstractGemOrb;
import theVacant.util.TextureLoader;

public class PolishVFX extends AbstractGameEffect {
    private static final Texture t = TextureLoader.getTexture("theVacantResources/images/vfx/Rag.png");
    private final AbstractOrb orb;
    private final AtlasRegion img;
    private float x, y, dist, ang;
    public static final float DURATION = 0.4F;

    public PolishVFX(AbstractOrb orb, float x, float y)
    {
        this.orb = orb;
        img = new AtlasRegion(t, 0, 0, t.getWidth(), t.getHeight());
        duration = startingDuration = DURATION;
        rotation = ang = -45 + (float)Math.random() * 90;
        dist = 0;
        color = new Color(1.0F, 1.0F, 1.0F, 1.0F);
    }

    @Override
    public void update() {
        if (duration == startingDuration) {
            CardCrawlGame.sound.playA("ATTACK_WHIFF_1", MathUtils.random(0.7F, 0.8F));
        }
        duration -= Gdx.graphics.getDeltaTime();
        if (duration < startingDuration / 4.0F) {
            color.a = duration / (startingDuration / 2.0F);
        }
        rotation = Interpolation.swing.apply(ang, -ang / 2, (startingDuration - duration) / DURATION);
        dist = Interpolation.swing.apply(-20, 20, (startingDuration - duration) / DURATION);

        getOrbPos();
        scale = Interpolation.swingOut.apply(Settings.scale*1.3F, Settings.scale*1.5F, (startingDuration - duration) / DURATION);

        if (duration < 0.0F) {
            isDone = true;
            polishSFX();
            color.a = 0.0F;
            for(int i = 0; i < 8; ++i) {
                AbstractDungeon.effectsQueue.add(new ShineSparkleEffect(orb.hb.cX, orb.hb.cY));
            }
        }
    }

    private void polishSFX()
    {
        Random rand = new Random();
        int chip = rand.random(4);
        switch (chip)
        {
            case 0:
                CardCrawlGame.sound.play("theVacant:gem1", 0);
                break;
            case 1:
                CardCrawlGame.sound.play("theVacant:gem2", 0);
                break;
            case 2:
                CardCrawlGame.sound.play("theVacant:gem3", 0);
                break;
            default:
                CardCrawlGame.sound.play("theVacant:gem4", 0);
        }
    }

    private void getOrbPos()
    {
        x = orb.hb.cX;// - img.getRegionWidth()/2F * Settings.scale;
        y = orb.hb.cY;// + img.getRegionHeight()/2F * Settings.scale;
        x += Math.cos((ang + 90) * Math.PI/ 180) * dist;
        y += Math.sin((ang + 90) * Math.PI/ 180) * dist;
    }

    @Override
    public void render(SpriteBatch sb) {
        sb.setColor(color);
        sb.draw(this.img, this.x - this.img.packedWidth / 2.0F, this.y - this.img.packedHeight / 2.0F, this.img.packedWidth / 2.0F, this.img.packedWidth / 2.0F, this.img.packedWidth, this.img.packedHeight, this.scale, this.scale, this.rotation);
    }

    @Override
    public void dispose() {}
}
