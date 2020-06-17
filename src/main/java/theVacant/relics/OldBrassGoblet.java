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
import theVacant.util.TextureLoader;
import static theVacant.VacantMod.makeRelicOutlinePath;
import static theVacant.VacantMod.makeRelicPath;

public class OldBrassGoblet extends CustomRelic
{
    public static final String ID = VacantMod.makeID(BrassGoblet.class.getSimpleName());

    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath("brass_goblet.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(makeRelicOutlinePath("brass_goblet.png"));

    public static final int HEALTHY_DEX = 1;
    public static final int WOUNDED_STRENGTH = 2;

    public OldBrassGoblet() {
        super(ID, IMG, OUTLINE, RelicTier.STARTER, LandingSound.CLINK);
    }

    @Override
    public void atBattleStart()
    {
        AbstractDungeon.actionManager.addToBottom(new AbstractGameAction()
        {
            public void update()
            {
                OldBrassGoblet.this.flash();
                if(AbstractDungeon.player.isBloodied)
                    AbstractDungeon.player.addPower(new StrengthPower(AbstractDungeon.player, WOUNDED_STRENGTH));
                else
                    AbstractDungeon.player.addPower(new DexterityPower(AbstractDungeon.player, HEALTHY_DEX));
                addToTop(new RelicAboveCreatureAction(AbstractDungeon.player, OldBrassGoblet.this));
                AbstractDungeon.onModifyPower();
                this.isDone = true;
            }
        });
    }

    @Override
    public String getUpdatedDescription()
    {
        return DESCRIPTIONS[0];
    }
}