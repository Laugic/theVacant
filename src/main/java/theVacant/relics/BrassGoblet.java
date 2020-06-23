package theVacant.relics;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import com.megacrit.cardcrawl.powers.DexterityPower;
import com.megacrit.cardcrawl.powers.StrengthPower;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import theVacant.VacantMod;
import theVacant.powers.GreaterMindUpgradedPower;
import theVacant.powers.VoidPower;
import theVacant.util.TextureLoader;
import static theVacant.VacantMod.makeRelicOutlinePath;
import static theVacant.VacantMod.makeRelicPath;

public class BrassGoblet extends CustomRelic
{

    public static final String ID = VacantMod.makeID(BrassGoblet.class.getSimpleName());

    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath("brass_goblet.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(makeRelicOutlinePath("brass_goblet.png"));

    public static final int VOID_AMOUNT = 2;

    public BrassGoblet() {
        super(ID, IMG, OUTLINE, RelicTier.STARTER, LandingSound.CLINK);
    }

    @Override
    public void atTurnStart()
    {
        AbstractPlayer player = AbstractDungeon.player;
        this.flash();
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(player, player, new VoidPower(player, player, VOID_AMOUNT), VOID_AMOUNT));
    }

    @Override
    public String getUpdatedDescription()
    {
        return DESCRIPTIONS[0] + VOID_AMOUNT + DESCRIPTIONS[1];
    }

}