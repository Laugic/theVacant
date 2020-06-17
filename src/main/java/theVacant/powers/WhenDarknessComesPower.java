package theVacant.powers;

import basemod.BaseMod;
import basemod.interfaces.CloneablePowerInterface;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.unique.ExhumeAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import theVacant.VacantMod;
import theVacant.actions.ExhumeAndEnhanceAction;
import theVacant.actions.RandomExhumeAction;
import theVacant.actions.VacantMillAction;
import theVacant.util.TextureLoader;

public class WhenDarknessComesPower extends AbstractPower implements CloneablePowerInterface
{
    public AbstractCreature source;

    public static final String POWER_ID = VacantMod.makeID(WhenDarknessComesPower.class.getSimpleName());
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    private static final Texture tex84 = TextureLoader.getTexture("theVacantResources/images/powers/placeholder_power84.png");
    private static final Texture tex32 = TextureLoader.getTexture("theVacantResources/images/powers/placeholder_power32.png");

    public WhenDarknessComesPower(final AbstractCreature owner, final AbstractCreature source, final int amount)
    {
        name = NAME;
        ID = POWER_ID;

        this.owner = owner;
        this.amount = amount;
        this.source = source;

        type = PowerType.BUFF;
        isTurnBased = false;

        this.region128 = new TextureAtlas.AtlasRegion(tex84, 0, 0, 84, 84);
        this.region48 = new TextureAtlas.AtlasRegion(tex32, 0, 0, 32, 32);

        updateDescription();
    }

    @Override
    public void atStartOfTurnPostDraw()
    {
        flash();
        AbstractDungeon.actionManager.addToBottom(new RandomExhumeAction(this.amount));
    }

    @Override
    public void updateDescription()
    {
        description = DESCRIPTIONS[0] + this.amount + (this.amount==1?DESCRIPTIONS[1]:DESCRIPTIONS[2]);
    }

    @Override
    public AbstractPower makeCopy()
    {
        return new WhenDarknessComesPower(owner, source, amount);
    }
}
