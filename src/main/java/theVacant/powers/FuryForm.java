package theVacant.powers;

import basemod.interfaces.CloneablePowerInterface;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.FocusPower;
import com.megacrit.cardcrawl.powers.watcher.VigorPower;
import theVacant.VacantMod;
import theVacant.util.TextureLoader;

public class FuryForm extends FormPower implements CloneablePowerInterface
{
    public AbstractCreature source;

    public static final String POWER_ID = VacantMod.makeID(FuryForm.class.getSimpleName());
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    private static final Texture tex84 = TextureLoader.getTexture("theVacantResources/images/powers/fury_form84.png");
    private static final Texture tex32 = TextureLoader.getTexture("theVacantResources/images/powers/fury_form32.png");

    public FuryForm(final AbstractCreature owner, final AbstractCreature source, final int amount)
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
    public void update(int slot)
    {
        GetVigorAmount();
    }

    private int GetVigorAmount()
    {
        AbstractPlayer player = AbstractDungeon.player;
        int vigorAmount = 2;
        if(player.hasPower(FocusPower.POWER_ID))
            vigorAmount += player.getPower(FocusPower.POWER_ID).amount;
        amount = vigorAmount;
        return vigorAmount;
    }

    @Override
    public void updateDescription()
    {
        description = DESCRIPTIONS[0] + GetVigorAmount() + DESCRIPTIONS[1];
    }

    @Override
    public AbstractPower makeCopy()
    {
        return new FuryForm(owner, source, amount);
    }

    @Override
    public void Syphon()
    {
        flash();
        addToBot(new ApplyPowerAction(owner, owner, new VigorPower(owner, GetVigorAmount()), GetVigorAmount()));
    }
}
