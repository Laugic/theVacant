package theVacant.vfx;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.ArrayList;
import java.util.List;

public class ParticleManager
{
    public static ArrayList<Particle> particles;

    public ParticleManager()
    {
        particles = new ArrayList<>();
    }

    public static void updateParticles()
    {
        List<Particle> partToRemove = new ArrayList<>();
        for(Particle particle: particles)
        {
            particle.Update();
            if (particle.TimeLeft <= 0)
                partToRemove.add(particle);
        }
        for(Particle particle: partToRemove)
        {
            if (particles.contains(particle))
                particles.remove(particle);
        }
    }

    public static void drawParticles(SpriteBatch spriteBatch)
    {
        for(Particle particle: particles)
            particle.Draw(spriteBatch);
    }

    public static void addParticle(Particle particle)
    {
        particles.add(particle);
    }
}