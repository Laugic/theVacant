package theVacant.vfx;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import theVacant.VacantMod;
import theVacant.util.TextureLoader;

public class Particle
{
    public Particle(Texture texture, Vector2 pos, Vector2 velocity, int frames){
        this(texture, pos, velocity, frames, 1, 0, 0, 1, 1, 4f / 60f, 4, true);
    }

    public Particle(Texture texture, Vector2 pos, Vector2 velocity, int frames, float acceleration, float rotation, float rotAccel, float scale, float scaleMod, float aniSpeed, float timeLeft, boolean loop)
    {
        this.texture = texture;
        Position = pos;
        FrameNum = frames;
        Velocity = velocity;
        Acceleration = acceleration;
        Rotation = rotation;
        RotAccel = rotAccel;
        Scale = scale;
        ScaleMod = scaleMod;
        Speed = aniSpeed;
        TimeLeft = timeLeft;
        Loop = loop;
    }


    public void Update()
    {
        //Position += Velocity;
        //Velocity *= Acceleration;
        Rotation += RotAccel;
        Scale *= ScaleMod;
        TimeLeft -= Gdx.graphics.getDeltaTime();
        if (Scale < .05f)
            TimeLeft = 0;
        FrameCalc();
    }

    public void Draw(SpriteBatch spriteBatch)
    {
        /* - new Vector2(Texture.Width / 2, Texture.Height / FrameNum / 2)*/
        /*
        if (TimeLeft <= 0)
            return;
        Rectangle rect = new Rectangle(0, CurrentFrame * texture.getHeight() / FrameNum, texture.getWidth(), texture.getHeight() / FrameNum);
        spriteBatch.draw(texture, Position.x, Position.y, texture.getWidth() / 2, texture.getHeight() / FrameNum / 2, rect.getWidth(), rect.getHeight(), Scale, Scale, Rotation, (int)rect.x, (int)rect.y, (int)rect.getWidth(), (int)rect.getHeight(), false, false);*/

        TextureAtlas.AtlasRegion img = new TextureAtlas.AtlasRegion(texture, 0, CurrentFrame * texture.getHeight() / FrameNum, texture.getWidth(), texture.getHeight() / FrameNum);
        spriteBatch.draw(img, Position.x, Position.y, (float)img.packedWidth / 2.0F, (float)img.packedHeight / 2.0F, (float)img.packedWidth, (float)img.packedHeight, Scale, Scale * MathUtils.random(0.6F, 1.4F), Rotation);// 61 70
        img = ImageMaster.ROOM_SHINE_2;
        spriteBatch.setBlendFunction(770, 1);// 59
        spriteBatch.setColor(new Color(1.0F, MathUtils.random(0.7F, 1.0F), 0.4F, 0.0F));
        spriteBatch.draw(img, Position.x, Position.y, (float)img.packedWidth / 2.0F, (float)img.packedHeight / 2.0F, (float)img.packedWidth, (float)img.packedHeight, Scale, Scale * MathUtils.random(0.6F, 1.4F), Rotation);// 61 70
        spriteBatch.setBlendFunction(770, 771);// 85
    }

    public void FrameCalc()
    {
        FrameCounter += Gdx.graphics.getDeltaTime();
        if (FrameCounter > Speed)
        {
            FrameCounter = 0;
            CurrentFrame++;
            if (CurrentFrame >= FrameNum)
            {
                if (Loop)
                    CurrentFrame = 0;
                else
                    TimeLeft = 0;
            }
        }
    }

    public Texture texture;
    public Vector2 Position;
    public Vector2 Velocity;
    public int FrameNum;
    public float Acceleration;
    public float Rotation;
    public float RotAccel;
    public float Scale;
    public float ScaleMod;
    public int FrameCounter;
    public int CurrentFrame;
    public float Speed;
    public float TimeLeft;
    public boolean Loop;

    public static Texture flameTexture = TextureLoader.getTexture("theVacantResources/images/particles/Flame.png");
}