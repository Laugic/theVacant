package theVacant.relics;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import theVacant.VacantMod;
import theVacant.util.TextureLoader;
import static theVacant.VacantMod.makeRelicOutlinePath;
import static theVacant.VacantMod.makeRelicPath;

public class Deathbell extends CustomRelic
{

    public static final String ID = VacantMod.makeID(Deathbell.class.getSimpleName());

    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath("deathbell_relic.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(makeRelicOutlinePath("deathbell_relic.png"));

    public static int bonusAmount = 2;

    public Deathbell() {
        super(ID, IMG, OUTLINE, RelicTier.UNCOMMON, LandingSound.MAGICAL);
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }
}