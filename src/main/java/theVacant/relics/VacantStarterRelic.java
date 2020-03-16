package theVacant.relics;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import theVacant.VacantMod;
import theVacant.util.TextureLoader;
import static theVacant.VacantMod.makeRelicOutlinePath;
import static theVacant.VacantMod.makeRelicPath;

public class VacantStarterRelic extends CustomRelic
{

    public static final String ID = VacantMod.makeID("VacantStarterRelic");

    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath("placeholder_relic.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(makeRelicOutlinePath("placeholder_relic.png"));

    public VacantStarterRelic() {
        super(ID, IMG, OUTLINE, RelicTier.STARTER, LandingSound.MAGICAL);
    }

    @Override
    public void atBattleStartPreDraw() {
        flash();
    }

    @Override
    public void atBattleStart()
    {
        AbstractPlayer player = AbstractDungeon.player;
        if(player.currentHealth > player.maxHealth / 2)
        {
            player.decreaseMaxHealth(2);
            if(player.maxHealth < 50)
                player.increaseMaxHp(50-player.maxHealth, false);
        }
        else
            player.increaseMaxHp(4, true);
    }
    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }
}