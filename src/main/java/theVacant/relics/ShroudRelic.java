package theVacant.relics;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import theVacant.VacantMod;
import theVacant.cards.AbstractVacantCard;
import theVacant.powers.ShroudPower;
import theVacant.util.TextureLoader;

import static theVacant.VacantMod.makeRelicOutlinePath;
import static theVacant.VacantMod.makeRelicPath;

public class ShroudRelic extends CustomRelic
{

    public static final String ID = VacantMod.makeID(ShroudRelic.class.getSimpleName());

    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath("shroud_relic.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(makeRelicOutlinePath("shroud_relic.png"));


    public ShroudRelic() {
        super(ID, IMG, OUTLINE, RelicTier.SPECIAL, LandingSound.MAGICAL);
    }
    
    @Override
    public void atBattleStart()
    {
        flash();

        AbstractPlayer player = AbstractDungeon.player;
        addToBot(new RelicAboveCreatureAction(AbstractDungeon.player, this));
        addToBot(new ApplyPowerAction(player, player, new ShroudPower(player, player, 3), 3));
        grayscale = true;
    }

    @Override
    public void justEnteredRoom(AbstractRoom room)
    {
        grayscale = false;
    }
    
    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }
}