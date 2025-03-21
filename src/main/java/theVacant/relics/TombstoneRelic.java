package theVacant.relics;

import basemod.abstracts.CustomRelic;
import basemod.helpers.CardPowerTip;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import com.megacrit.cardcrawl.helpers.PowerTip;
import com.megacrit.cardcrawl.powers.DexterityPower;
import com.megacrit.cardcrawl.powers.StrengthPower;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import theVacant.VacantMod;
import theVacant.actions.DelayedTombstoneAction;
import theVacant.actions.VacantMillAction;
import theVacant.cards.AbstractVacantCard;
import theVacant.powers.GreaterMindUpgradedPower;
import theVacant.powers.VoidPower;
import theVacant.util.TextureLoader;
import static theVacant.VacantMod.makeRelicOutlinePath;
import static theVacant.VacantMod.makeRelicPath;

public class TombstoneRelic extends CustomRelic
{

    public static final String ID = VacantMod.makeID(TombstoneRelic.class.getSimpleName());

    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath("tombstone_relic.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(makeRelicOutlinePath("tombstone_relic.png"));

    public TombstoneRelic()
    {
        super(ID, IMG, OUTLINE, RelicTier.BOSS, LandingSound.SOLID);
    }

    @Override
    public void atBattleStart()
    {
        AbstractDungeon.actionManager.addToBottom(new DelayedTombstoneAction(10));
        flash();
    }

    public void onShuffle() {
        flash();
        addToBot(new RelicAboveCreatureAction(AbstractDungeon.player, this));
        addToBot(new GainEnergyAction(1));
    }
//    @Override
//    public void obtain() {
//        super.obtain();
//        for (AbstractRelic relic: AbstractDungeon.player.relics) {
//            if(relic instanceof GobletRelic)
//                ((GobletRelic)relic).IncreaseCounter(2);
//        }
//    }

//    @Override
//    public void onVictory() {
//        grayscale = false;
//    }

//    @Override
//    public void justEnteredRoom(AbstractRoom room)
//    {
//        grayscale = false;
//    }

    @Override
    public String getUpdatedDescription()
    {
        return DESCRIPTIONS[0];
    }

}