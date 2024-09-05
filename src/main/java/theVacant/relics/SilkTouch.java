package theVacant.relics;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import theVacant.VacantMod;
import theVacant.orbs.AbstractGemOrb;
import theVacant.util.TextureLoader;

import static theVacant.VacantMod.makeRelicOutlinePath;
import static theVacant.VacantMod.makeRelicPath;

public class SilkTouch extends CustomRelic implements OnMineRelic
{
    public static final String ID = VacantMod.makeID(SilkTouch.class.getSimpleName());

    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath("silk_touch_relic.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(makeRelicOutlinePath("silk_touch_relic.png"));

    boolean firstTime = true;

    public SilkTouch() {
        super(ID, IMG, OUTLINE, RelicTier.COMMON, LandingSound.CLINK);
    }

    @Override
    public void atTurnStart() {
        firstTime = true;
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

    @Override
    public void onMine(AbstractGemOrb gem) {
        if(firstTime){
            gem.triggerPassive(gem.getAmount());
            firstTime = false;
            flash();
        }
    }
}