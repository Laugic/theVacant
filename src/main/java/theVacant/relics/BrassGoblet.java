package theVacant.relics;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import com.megacrit.cardcrawl.helpers.PowerTip;
import theVacant.VacantMod;
import theVacant.powers.VoidPower;
import theVacant.util.TextureLoader;
import static theVacant.VacantMod.makeRelicOutlinePath;
import static theVacant.VacantMod.makeRelicPath;

public class BrassGoblet extends CustomRelic implements GobletRelic
{

    public static final String ID = VacantMod.makeID(BrassGoblet.class.getSimpleName());

    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath("brass_goblet.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(makeRelicOutlinePath("brass_goblet.png"));

    public BrassGoblet()
    {
        super(ID, IMG, OUTLINE, RelicTier.STARTER, LandingSound.CLINK);
        counter = 2;
        getUpdatedDescription();
    }

    @Override
    public void atBattleStart()
    {
        flash();
        AtBattleStart();
    }

    @Override
    public void IncreaseCounter(int amount)
    {
        counter += amount;
        getUpdatedDescription();
    }

    @Override
    public String getUpdatedDescription()
    {
        description =  DESCRIPTIONS[0] + counter + DESCRIPTIONS[1];
        tips.clear();
        tips.add(new PowerTip(name, description));
        initializeTips();
        return description;
    }

    @Override
    public void AtBattleStart() {
        AbstractPlayer player = AbstractDungeon.player;
        addToBot(new RelicAboveCreatureAction(AbstractDungeon.player, this));
        addToBot(new ApplyPowerAction(player, player, new VoidPower(player, player, counter), counter));
    }
}