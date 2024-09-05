package theVacant.relics;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.PowerTip;
import org.apache.commons.lang3.StringUtils;
import theVacant.VacantMod;
import theVacant.powers.ShroudPower;
import theVacant.powers.VoidPower;
import theVacant.util.TextureLoader;

import static theVacant.VacantMod.makeRelicOutlinePath;
import static theVacant.VacantMod.makeRelicPath;

public class EnshroudedBrassGoblet extends CustomRelic implements GobletRelic
{

    public static final String ID = VacantMod.makeID(EnshroudedBrassGoblet.class.getSimpleName());

    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath("enshrouded_goblet.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(makeRelicOutlinePath("enshrouded_overflowing_goblet.png"));

    public EnshroudedBrassGoblet()
    {
        super(ID, IMG, OUTLINE, RelicTier.SPECIAL, LandingSound.CLINK);
        counter = 2;
        if (AbstractDungeon.player != null && AbstractDungeon.player.hasRelic(BrassGoblet.ID))
        {
            for (int i = 0; i < AbstractDungeon.player.relics.size(); ++i)
            {
                if (StringUtils.equals(AbstractDungeon.player.relics.get(i).relicId, BrassGoblet.ID))
                {
                    counter = AbstractDungeon.player.getRelic(BrassGoblet.ID).counter;
                    getUpdatedDescription();
                    return;
                }
            }
        }
        getUpdatedDescription();
    }

    @Override
    public void atBattleStart()
    {
        flash();
        AtBattleStart();
    }

    @Override
    public void obtain()
    {
        if (AbstractDungeon.player.hasRelic(BrassGoblet.ID))
        {
            for (int i = 0; i < AbstractDungeon.player.relics.size(); ++i)
            {
                if (StringUtils.equals(AbstractDungeon.player.relics.get(i).relicId, BrassGoblet.ID))
                {
                    counter = AbstractDungeon.player.getRelic(BrassGoblet.ID).counter;
                    counter++;
                    instantObtain(AbstractDungeon.player, i, true);
                    break;
                }
            }
        }
        else
            super.obtain();
        getUpdatedDescription();
    }

    @Override
    public String getUpdatedDescription()
    {
        description = DESCRIPTIONS[0] + counter + DESCRIPTIONS[1] + counter + DESCRIPTIONS[2];
        tips.clear();
        tips.add(new PowerTip(name, description));
        initializeTips();
        return description;
    }

    @Override
    public void IncreaseCounter(int amount)
    {
        counter += amount;
        getUpdatedDescription();
    }

    @Override
    public void AtBattleStart() {
        AbstractPlayer player = AbstractDungeon.player;
        addToBot(new RelicAboveCreatureAction(AbstractDungeon.player, this));
        addToBot(new ApplyPowerAction(player, player, new VoidPower(player, player, counter), counter));
        addToBot(new ApplyPowerAction(player, player, new ShroudPower(player, player, counter), counter));
    }
}