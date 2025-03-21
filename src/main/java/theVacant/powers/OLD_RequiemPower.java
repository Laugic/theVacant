package theVacant.powers;

import basemod.interfaces.CloneablePowerInterface;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.evacipated.cardcrawl.mod.stslib.patches.NeutralPowertypePatch;
import com.evacipated.cardcrawl.mod.stslib.powers.interfaces.BetterOnApplyPowerPower;
import com.evacipated.cardcrawl.mod.stslib.powers.interfaces.OnReceivePowerPower;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import theVacant.VacantMod;
import theVacant.util.TextureLoader;

public class OLD_RequiemPower extends AbstractPower implements CloneablePowerInterface, BetterOnApplyPowerPower, OnReceivePowerPower
{
    public AbstractCreature source;

    public static final String POWER_ID = VacantMod.makeID(OLD_RequiemPower.class.getSimpleName());
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    private static final Texture tex84 = TextureLoader.getTexture("theVacantResources/images/powers/arrow84.png");
    private static final Texture tex32 = TextureLoader.getTexture("theVacantResources/images/powers/arrow32.png");

    public OLD_RequiemPower(final AbstractCreature owner, final AbstractCreature source, final int amount)
    {
        name = NAME;
        ID = POWER_ID;

        this.owner = owner;
        this.amount = amount;
        this.source = source;

        type = NeutralPowertypePatch.NEUTRAL;
        isTurnBased = false;

        this.region128 = new TextureAtlas.AtlasRegion(tex84, 0, 0, 84, 84);
        this.region48 = new TextureAtlas.AtlasRegion(tex32, 0, 0, 32, 32);

        updateDescription();
    }

/*
    @Override
    public void onApplyPower(AbstractPower power, AbstractCreature target, AbstractCreature source)
    {
    }
*/
    @Override
    public void updateDescription()
    {
        description = DESCRIPTIONS[0] + amount + DESCRIPTIONS[1];
    }

    @Override
    public AbstractPower makeCopy()
    {
        return new OLD_RequiemPower(owner, source, amount);
    }

    @Override
    public boolean betterOnApplyPower(AbstractPower power, AbstractCreature target, AbstractCreature source)
    {
        if(power.amount != 0 && target != owner && (power.type == PowerType.DEBUFF))
        {
            flash();
            power.amount += amount;
            power.updateDescription();
        }
        for (AbstractCard card: AbstractDungeon.player.hand.group)
            card.applyPowers();
        return true;
    }

    @Override
    public boolean onReceivePower(AbstractPower power, AbstractCreature target, AbstractCreature source) {
        if(power.amount != 0 && target == owner && source != owner && (power.type == PowerType.BUFF || power.type == PowerType.DEBUFF))
        {
            flash();
            power.amount *= amount;
            power.updateDescription();
        }
        for (AbstractCard card: AbstractDungeon.player.hand.group)
            card.applyPowers();
        return true;
    }
}
