package theVacant.relics;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.evacipated.cardcrawl.mod.stslib.actions.tempHp.AddTemporaryHPAction;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import theVacant.VacantMod;
import theVacant.actions.VacantMillAction;
import theVacant.cards.AbstractVacantCard;
import theVacant.powers.DoomPower;
import theVacant.powers.ShroudPower;
import theVacant.powers.VoidPower;
import theVacant.util.TextureLoader;
import static theVacant.VacantMod.makeRelicOutlinePath;
import static theVacant.VacantMod.makeRelicPath;

public class LocketRelic extends CustomRelic
{

    public static final String ID = VacantMod.makeID(LocketRelic.class.getSimpleName());

    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath("locket_relic.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(makeRelicOutlinePath("locket_relic.png"));

    public static int BLOCK_AMOUNT = 4;

    public LocketRelic() {
        super(ID, IMG, OUTLINE, RelicTier.COMMON, LandingSound.CLINK);
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }
}