package theVacant.relics;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.PowerTip;
import com.megacrit.cardcrawl.powers.ArtifactPower;
import org.apache.commons.lang3.StringUtils;
import theVacant.VacantMod;
import theVacant.powers.ShroudPower;
import theVacant.powers.VoidPower;
import theVacant.util.TextureLoader;

import static theVacant.VacantMod.makeRelicOutlinePath;
import static theVacant.VacantMod.makeRelicPath;

public class EnshroudedOverflowingGobletRelic extends CustomRelic implements GobletRelic
{

    public static final String ID = VacantMod.makeID(EnshroudedOverflowingGobletRelic.class.getSimpleName());

    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath("enshrouded_overflowing_goblet.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(makeRelicOutlinePath("enshrouded_overflowing_goblet.png"));

    public EnshroudedOverflowingGobletRelic()
    {
        super(ID, IMG, OUTLINE, RelicTier.BOSS, LandingSound.CLINK);
        counter = 2;
        if (AbstractDungeon.player != null && AbstractDungeon.player.hasRelic(EnshroudedBrassGoblet.ID))
        {
            for (int i = 0; i < AbstractDungeon.player.relics.size(); ++i)
            {
                if (StringUtils.equals(AbstractDungeon.player.relics.get(i).relicId, EnshroudedBrassGoblet.ID))
                {
                    counter = AbstractDungeon.player.getRelic(EnshroudedBrassGoblet.ID).counter;
                    getUpdatedDescription();
                    return;
                }
            }
        }
        if (AbstractDungeon.player != null && AbstractDungeon.player.hasRelic(OverflowingGobletRelic.ID))
        {
            for (int i = 0; i < AbstractDungeon.player.relics.size(); ++i)
            {
                if (StringUtils.equals(AbstractDungeon.player.relics.get(i).relicId, OverflowingGobletRelic.ID))
                {
                    counter = AbstractDungeon.player.getRelic(OverflowingGobletRelic.ID).counter;
                    counter++;
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
        if (AbstractDungeon.player.hasRelic(EnshroudedBrassGoblet.ID))
        {
            for (int i = 0; i < AbstractDungeon.player.relics.size(); ++i)
            {
                if (StringUtils.equals(AbstractDungeon.player.relics.get(i).relicId, EnshroudedBrassGoblet.ID))
                {
                    counter = AbstractDungeon.player.getRelic(EnshroudedBrassGoblet.ID).counter;
                    instantObtain(AbstractDungeon.player, i, true);
                    break;
                }
            }
        }
        else if (AbstractDungeon.player.hasRelic(OverflowingGobletRelic.ID))
        {
            for (int i = 0; i < AbstractDungeon.player.relics.size(); ++i)
            {
                if (StringUtils.equals(AbstractDungeon.player.relics.get(i).relicId, OverflowingGobletRelic.ID))
                {
                    counter = AbstractDungeon.player.getRelic(OverflowingGobletRelic.ID).counter;
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
    public boolean canSpawn() {
        return AbstractDungeon.player.hasRelic(EnshroudedBrassGoblet.ID);
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
    public void AtBattleStart() {
        AbstractPlayer player = AbstractDungeon.player;
        addToBot(new RelicAboveCreatureAction(AbstractDungeon.player, this));
        addToBot(new ApplyPowerAction(player, player, new VoidPower(player, player, counter), counter));
        addToBot(new ApplyPowerAction(player, player, new ArtifactPower(player, counter), counter));
        addToBot(new ApplyPowerAction(player, player, new ShroudPower(player, player, counter), counter));
        addToBot(new DrawCardAction(counter));
    }

    @Override
    public void IncreaseCounter(int amount) {
        counter++;
        getUpdatedDescription();
    }
}