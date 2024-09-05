package theVacant.relics;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.evacipated.cardcrawl.mod.stslib.actions.tempHp.AddTemporaryHPAction;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import com.megacrit.cardcrawl.rooms.AbstractRoom;
import theVacant.VacantMod;
import theVacant.cards.AbstractVacantCard;
import theVacant.util.TextureLoader;
import static theVacant.VacantMod.makeRelicOutlinePath;
import static theVacant.VacantMod.makeRelicPath;

public class Deathbell extends CustomRelic
{

    public static final String ID = VacantMod.makeID(Deathbell.class.getSimpleName());

    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath("deathbell_relic.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(makeRelicOutlinePath("deathbell_relic.png"));

    public static int bonusAmount = 2, DOOM_AMOUNT = 2, TempHP = 10;

    public Deathbell() {
        super(ID, IMG, OUTLINE, RelicTier.UNCOMMON, LandingSound.MAGICAL);
    }

    @Override
    public void onPlayerEndTurn() {
        if(!grayscale){
            flash();
            if(AbstractVacantCard.getHollow()){
                addToBot(new RelicAboveCreatureAction(AbstractDungeon.player, this));
                addToBot(new AddTemporaryHPAction(AbstractDungeon.player, AbstractDungeon.player, TempHP));
            }
            grayscale = true;
        }
    }

    @Override
    public void justEnteredRoom(AbstractRoom room)
    {
        grayscale = false;
    }

    @Override
    public void onVictory() {
        grayscale = false;
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }
}