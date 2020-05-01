package theVacant.powers;

import basemod.interfaces.CloneablePowerInterface;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.LoseStrengthPower;
import com.megacrit.cardcrawl.powers.StrengthPower;
import com.megacrit.cardcrawl.powers.watcher.VigorPower;
import theVacant.VacantMod;
import theVacant.actions.VacantMillAction;
import theVacant.util.TextureLoader;

public class FerocityRune extends EtchPower implements CloneablePowerInterface
{
    public AbstractCreature source;

    public static final String POWER_ID = VacantMod.makeID(FerocityRune.class.getSimpleName());
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    private static final Texture tex84 = TextureLoader.getTexture("theVacantResources/images/powers/placeholder_power84.png");
    private static final Texture tex32 = TextureLoader.getTexture("theVacantResources/images/powers/placeholder_power32.png");

    public FerocityRune(final AbstractCreature owner, final AbstractCreature source, final int amount)
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
    public void atStartOfTurn()
    {
        if(this.amount > 0)
        {
            flash();
            AbstractDungeon.actionManager.addToTop(new ApplyPowerAction(this.owner, this.owner, new VigorPower(this.owner, this.amount), this.amount));
        }
    }

    @Override
    public void Release()
    {
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(this.owner, this.owner, new StrengthPower(this.owner, this.amount), this.amount));
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(this.owner, this.owner, new LoseStrengthPower(this.owner, this.amount), this.amount));
    }

    @Override
    public void updateDescription()
    {
        description = DESCRIPTIONS[0] + amount + DESCRIPTIONS[1] + amount + DESCRIPTIONS[2];
    }

    @Override
    public AbstractPower makeCopy()
    {
        return new FerocityRune(owner, source, amount);
    }
}
