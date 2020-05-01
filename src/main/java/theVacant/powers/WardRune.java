package theVacant.powers;

import basemod.interfaces.CloneablePowerInterface;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.LoseStrengthPower;
import com.megacrit.cardcrawl.powers.NextTurnBlockPower;
import com.megacrit.cardcrawl.powers.StrengthPower;
import com.megacrit.cardcrawl.powers.watcher.VigorPower;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.vfx.combat.LightningEffect;
import theVacant.VacantMod;
import theVacant.util.TextureLoader;

public class WardRune extends EtchPower implements CloneablePowerInterface
{
    public AbstractCreature source;

    public static final String POWER_ID = VacantMod.makeID(WardRune.class.getSimpleName());
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    private static final Texture tex84 = TextureLoader.getTexture("theVacantResources/images/powers/placeholder_power84.png");
    private static final Texture tex32 = TextureLoader.getTexture("theVacantResources/images/powers/placeholder_power32.png");

    public WardRune(final AbstractCreature owner, final AbstractCreature source, final int amount)
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
    public void wasHPLost(DamageInfo info, int damageAmount)
    {
        if ((AbstractDungeon.getCurrRoom()).phase == AbstractRoom.RoomPhase.COMBAT && damageAmount > 0)
        {
            flash();
            addToTop(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new NextTurnBlockPower(AbstractDungeon.player, this.amount, this.name), this.amount));
        }
    }

    @Override
    public void Release()
    {
        flash();
        addToBot(new GainBlockAction(this.owner, this.owner, this.amount));
        addToBot(new GainBlockAction(this.owner, this.owner, this.amount));
    }

    @Override
    public void updateDescription()
    {
        description = DESCRIPTIONS[0] + amount + DESCRIPTIONS[1] + amount + DESCRIPTIONS[2];
    }

    @Override
    public AbstractPower makeCopy()
    {
        return new WardRune(owner, source, amount);
    }
}
