package theVacant.util;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import java.util.HashMap;
import com.badlogic.gdx.utils.GdxRuntimeException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class TextureLoader
{
    private static HashMap<String, Texture> textures = new HashMap<String, Texture>();
    public static final Logger logger = LogManager.getLogger(TextureLoader.class.getName());

    /**
     * @param textureString - String path to the texture you want to load relative to resources,
     *                      Example: "theDefaultResources/images/ui/missing_texture.png"
     * @return <b>com.badlogic.gdx.graphics.Texture</b> - The texture from the path provided
     */
    public static Texture getTexture(final String textureString)
    {
        if (textures.get(textureString) == null) {
            try {
                loadTexture(textureString);
            } catch (GdxRuntimeException e) {
                logger.error("Could not find texture: " + textureString);
                return getTexture("theDefaultResources/images/ui/missing_texture.png");
            }
        }
        return textures.get(textureString);
    }

    /**
     * Creates an instance of the texture, applies a linear filter to it, and places it in the HashMap
     *
     * @param textureString - String path to the texture you want to load relative to resources,
     *                      Example: "img/ui/missingtexture.png"
     * @throws GdxRuntimeException
     */
    private static void loadTexture(final String textureString) throws GdxRuntimeException
    {
        logger.info("DefaultMod | Loading Texture: " + textureString);
        Texture texture = new Texture(textureString);
        texture.setFilter(Texture.TextureFilter.Linear, TextureFilter.Linear);
        textures.put(textureString, texture);
    }
}
