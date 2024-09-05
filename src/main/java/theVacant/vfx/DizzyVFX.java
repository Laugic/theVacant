package theVacant.vfx;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.ui.buttons.EndTurnButton;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;
import com.megacrit.cardcrawl.vfx.combat.DamageImpactLineEffect;
import com.sun.javafx.stage.ScreenHelper;
import theVacant.util.TextureLoader;

import java.util.Set;

public class DizzyVFX  extends AbstractGameEffect {

    // Settings

    // Textures.
    /*private static final Texture texture_red = TextureLoader.getTexture(SnowpunkMod.makeImagePath("vfx/Snowflake1.png"));
    private static final Texture texture_green = TextureLoader.getTexture(SnowpunkMod.makeImagePath("vfx/Snowflake2.png"));
    private static final Texture texture_blue = TextureLoader.getTexture(SnowpunkMod.makeImagePath("vfx/Snowflake3.png"));
    private static final Texture texture_back = TextureLoader.getTexture(SnowpunkMod.makeImagePath("vfx/Snowflake4.png"));
*/
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
    private float direction = 270.0F;

    // How move
    private float rotation;
    private float rotation_speed;
    private float flip_speed;
    private float flip_counter;
    private float scale, spin, spinY, delay;
    public AbstractCard card;

    public DizzyVFX(AbstractCard card) {

        this.card = card;
        FinalTexture = card.getCardBgAtlas().getTexture();
        renderBehind = false;
        rotation = MathUtils.random(0.0F, 360.0F);
        spin = (float) (MathUtils.random(0.0F, 360.0F) / Math.PI);
        spinY = (float) (MathUtils.random(0.0F, 360.0F) / Math.PI);
        rotation_speed = MathUtils.random(40.0F, 80.0F) * Settings.scale;
        if(MathUtils.random.nextBoolean())
            rotation_speed *= -1;
        flip_speed = MathUtils.random(-2.0F, 2.0F) * Settings.scale;
        flip_counter = MathUtils.random(0.0F, 6.4F);
        speed = MathUtils.random(200.0F, 300.0F) * Settings.scale;
        scale = Settings.scale * .25f;

        delay = MathUtils.random.nextFloat();

        // Location
        this.y = AbstractDungeon.player.hb.cY + (float)Math.sin(spinY) * AbstractDungeon.player.hb.height / 8.0f;// + MathUtils.random(-200f, 200f) * Settings.yScale;
        this.x = AbstractDungeon.player.hb.cX + (float)Math.cos(spin / 2.0f) * AbstractDungeon.player.hb.width / 100 * .5f;// + MathUtils.random(-200f, 200f) * Settings.xScale;

        this.color = new Color(1, 1, 1, 1F);
    }

    @Override
    public void render(SpriteBatch sb) {
        sb.setColor(this.color);

        card.current_x = x;
        card.current_y = y;

        if(scale <= 0)
        {
            isDone = true;
            return;
        }
        card.drawScale = scale;
        card.angle = rotation;

        if(delay > 1.5f)
            card.render(sb);
        /*
        final int w = this.FinalTexture.getWidth();
        final int h = this.FinalTexture.getHeight();
        final int w2 = this.FinalTexture.getWidth();
        final int h2 = this.FinalTexture.getHeight();
        sb.draw(this.FinalTexture, x - w2 / 2f, y - h2 / 2f,
                w / 2f, h / 2f,
                w2, h2,
                (float) (this.scale) * Settings.scale * this.totalscale, 1.0F * Settings.scale * this.totalscale,
                this.rotation,
                0, 0,
                w2, h2,
                false, false);*/
    }

    @Override
    public void dispose() {
    }

    @Override
    public void update() {
        final float dt = Gdx.graphics.getDeltaTime();
        spin += dt * 2f;
        spinY += dt * 3;
        delay += dt;



        this.y = AbstractDungeon.player.hb.cY + (float)Math.sin(spinY) * AbstractDungeon.player.hb.height / 8.0f;
        this.x = AbstractDungeon.player.hb.cX + (float)Math.cos(spin / 2.0f) * AbstractDungeon.player.hb.width * .5f;

        this.rotation += this.rotation_speed * dt;
        this.flip_counter += this.flip_speed * dt;

        //this.scale = MathUtils.sin(this.flip_counter);

        //if (this.scale > 0.0F) {
            this.FinalTexture = this.TextureUsed;
        //} else
        //    this.FinalTexture = texture_back;
/*
        this.duration -= dt;
        if (this.duration < 0.0F) {
            this.isDone = true;
        }*/

        if(AbstractDungeon.getMonsters().areMonstersDead() || AbstractDungeon.getMonsters().areMonstersBasicallyDead() || AbstractDungeon.getMonsters().haveMonstersEscaped())
            isDone = true;
    }
}
