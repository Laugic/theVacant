package theVacant.powers;

import basemod.interfaces.CloneablePowerInterface;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.LoseStrengthPower;
import com.megacrit.cardcrawl.powers.StrengthPower;
import com.megacrit.cardcrawl.powers.watcher.VigorPower;
import com.megacrit.cardcrawl.vfx.combat.LightningEffect;
import theVacant.VacantMod;
import theVacant.util.TextureLoader;

public class NecraRune extends EtchPower implements CloneablePowerInterface
{
    public AbstractCreature source;

    public static final String POWER_ID = VacantMod.makeID(NecraRune.class.getSimpleName());
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    private static final Texture tex84 = TextureLoader.getTexture("theVacantResources/images/powers/necra_power84.png");
    private static final Texture tex32 = TextureLoader.getTexture("theVacantResources/images/powers/necra_power32.png");

    public NecraRune(final AbstractCreature owner, final AbstractCreature source, final int amount)
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
    public void onVictory()
    {
        if (this.owner.currentHealth > 0)
            this.owner.heal(this.amount);
    }

    @Override
    public void Release()
    {
        AbstractDungeon.effectsQueue.add(new LightningEffect(AbstractDungeon.player.hb.cX, AbstractDungeon.player.hb.cY));
        addToBot(new DamageAction(AbstractDungeon.player, new DamageInfo(AbstractDungeon.player, this.amount, DamageInfo.DamageType.NORMAL), AbstractGameAction.AttackEffect.NONE));
    }

    @Override
    public void updateDescription()
    {
        description = DESCRIPTIONS[0] + amount + DESCRIPTIONS[1] + amount + DESCRIPTIONS[2];
    }

    @Override
    public AbstractPower makeCopy()
    {
        return new NecraRune(owner, source, amount);
    }
}
