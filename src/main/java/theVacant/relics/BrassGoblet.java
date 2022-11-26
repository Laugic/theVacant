package theVacant.relics;

import basemod.abstracts.CustomRelic;
import basemod.helpers.CardPowerTip;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import com.megacrit.cardcrawl.helpers.PowerTip;
import com.megacrit.cardcrawl.powers.DexterityPower;
import com.megacrit.cardcrawl.powers.StrengthPower;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import theVacant.VacantMod;
import theVacant.actions.VacantMillAction;
import theVacant.cards.AbstractVacantCard;
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

    public BrassGoblet()
    {
        super(ID, IMG, OUTLINE, RelicTier.STARTER, LandingSound.CLINK);
        counter = 2;
        setDescriptionWithCard();
    }

    public void setDescriptionWithCard() {
        description = DESCRIPTIONS[0] + counter + DESCRIPTIONS[1];
        tips.clear();
        tips.add(new PowerTip(this.name, this.description));
        initializeTips();
    }

    @Override
    public void atBattleStart()
    {
        flash();
        AbstractPlayer player = AbstractDungeon.player;
        AbstractDungeon.actionManager.addToTop(new ApplyPowerAction(player, player, new VoidPower(player, player, counter), counter));
    }

    public void IncreaseVoid(int amount)
    {
        counter += amount;
        setDescriptionWithCard();
    }

    @Override
    public String getUpdatedDescription()
    {
        return DESCRIPTIONS[0] + counter + DESCRIPTIONS[1];
    }

}