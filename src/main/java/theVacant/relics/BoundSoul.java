package theVacant.relics;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.DexterityPower;
import com.megacrit.cardcrawl.powers.StrengthPower;
import com.megacrit.cardcrawl.relics.RedSkull;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import theVacant.VacantMod;
import theVacant.powers.WillPower;
import theVacant.util.TextureLoader;
import static theVacant.VacantMod.makeRelicOutlinePath;
import static theVacant.VacantMod.makeRelicPath;

public class BoundSoul extends CustomRelic
{

    public static final String ID = VacantMod.makeID(BoundSoul.class.getSimpleName());

    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath("bound_soul.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(makeRelicOutlinePath("bound_soul.png"));

    private boolean activated;

    public BoundSoul()
    {
        super(ID, IMG, OUTLINE, RelicTier.SPECIAL, LandingSound.CLINK);
        this.activated = false;
    }

    @Override
    public void atBattleStart()
    {
        BoundSoul.this.flash();
        AbstractDungeon.player.addPower(new WillPower(AbstractDungeon.player, AbstractDungeon.player, 1));
        this.activated = false;
    }

    @Override
    public void atTurnStart()
    {
        if(AbstractDungeon.player.isBloodied && !this.activated)
        {
            BoundSoul.this.flash();
            this.activated = true;
            AbstractDungeon.player.addPower(new WillPower(AbstractDungeon.player, AbstractDungeon.player, 1));
        }
    }

    @Override
    public String getUpdatedDescription()
    {
        return DESCRIPTIONS[0];
    }
}