package theVacant.vfx;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.ParticleEmitter;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.input.InputHelper;
import theVacant.VacantMod;
import theVacant.cards.AbstractDynamicCard;

import java.util.ArrayList;
import java.util.Iterator;

public class ParticlePatchClass {

    @SpirePatch2(clz = AbstractDungeon.class, method = "render")
    public static class ParticlePatch
    {
        public static void Postfix(SpriteBatch sb)
        {
            /*ParticleManager.updateParticles();
            ParticleManager.drawParticles(sb);
            ParticleManager.addParticle(new Particle(Particle.flameTexture, new Vector2(InputHelper.mX, InputHelper.mY), Vector2.Zero, 7));
            VacantMod.logger.info("Attempted to create particle at position "+ InputHelper.mX +", " + InputHelper.mY);*/
        }
    }
}
