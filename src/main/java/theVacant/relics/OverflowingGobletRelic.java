package theVacant.relics;

import basemod.abstracts.CustomRelic;
import basemod.helpers.CardPowerTip;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import com.megacrit.cardcrawl.helpers.PowerTip;
import com.megacrit.cardcrawl.powers.ArtifactPower;
import com.megacrit.cardcrawl.powers.DexterityPower;
import com.megacrit.cardcrawl.powers.StrengthPower;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import org.apache.commons.lang3.StringUtils;
import theVacant.VacantMod;
import theVacant.actions.VacantMillAction;
import theVacant.cards.AbstractVacantCard;
import theVacant.powers.GreaterMindUpgradedPower;
import theVacant.powers.VoidPower;
import theVacant.util.TextureLoader;
import static theVacant.VacantMod.makeRelicOutlinePath;
import static theVacant.VacantMod.makeRelicPath;

public class OverflowingGobletRelic extends CustomRelic implements GobletRelic
{

    public static final String ID = VacantMod.makeID(OverflowingGobletRelic.class.getSimpleName());

    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath("overflowing_goblet.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(makeRelicOutlinePath("overflowing_goblet.png"));

    public OverflowingGobletRelic()
    {
        super(ID, IMG, OUTLINE, RelicTier.BOSS, LandingSound.CLINK);
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
    public void obtain()
    {
        if (AbstractDungeon.player.hasRelic(BrassGoblet.ID))
        {
            for (int i = 0; i < AbstractDungeon.player.relics.size(); ++i)
            {
                if (StringUtils.equals(AbstractDungeon.player.relics.get(i).relicId, BrassGoblet.ID))
                {
                    counter = AbstractDungeon.player.getRelic(BrassGoblet.ID).counter;
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
        return AbstractDungeon.player.hasRelic(BrassGoblet.ID);
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
        addToBot(new DrawCardAction(counter));
        addToBot(new ApplyPowerAction(player, player, new ArtifactPower(player, counter), counter));
    }
}