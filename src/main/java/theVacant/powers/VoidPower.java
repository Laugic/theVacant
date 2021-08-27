package theVacant.powers;

import basemod.devcommands.energy.Energy;
import basemod.interfaces.CloneablePowerInterface;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageRandomEnemyAction;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.EnergyManager;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import theVacant.VacantMod;
import theVacant.actions.SwitchFormAction;
import theVacant.actions.SyphonAction;
import theVacant.actions.VacantMillAction;
import theVacant.cards.AbstractVacantCard;
import theVacant.relics.BoundSoul;
import theVacant.util.TextureLoader;

public class VoidPower extends AbstractPower implements CloneablePowerInterface
{
    public AbstractCreature source;

    public static final String POWER_ID = VacantMod.makeID(VoidPower.class.getSimpleName());
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    private static final Texture tex84 = TextureLoader.getTexture("theVacantResources/images/powers/void_power84.png");
    private static final Texture tex32 = TextureLoader.getTexture("theVacantResources/images/powers/void_power32.png");

    public VoidPower(final AbstractCreature owner, final AbstractCreature source, final int amount)
    {
        name = NAME;
        ID = POWER_ID;

        this.owner = owner;
        this.amount = amount;
        this.source = source;

        type = PowerType.BUFF;
        isTurnBased = true;

        region128 = new TextureAtlas.AtlasRegion(tex84, 0, 0, 84, 84);
        region48 = new TextureAtlas.AtlasRegion(tex32, 0, 0, 32, 32);

        updateDescription();
    }

    @Override
    public void atStartOfTurnPostDraw()
    {
        flash();
        AbstractDungeon.actionManager.addToBottom(new VacantMillAction(amount + AbstractVacantCard.GetBonusMillAmount()));
        AbstractDungeon.player.hand.applyPowers();
        updateDescription();
    }

    @Override
    public float atDamageGive(float damage, DamageInfo.DamageType type)
    {
        if (CheckDrawEmpty() && type == DamageInfo.DamageType.NORMAL && AbstractDungeon.player.hasPower(VoidFormPower.POWER_ID))
            return damage + amount * AbstractDungeon.player.getPower(VoidFormPower.POWER_ID).amount;
        return damage;
    }

    @Override
    public float modifyBlock(float blockAmount)
    {
        if (CheckDrawEmpty() && blockAmount > 0.0F && AbstractDungeon.player.hasPower(VoidFormPower.POWER_ID))
            return blockAmount + amount * AbstractDungeon.player.getPower(VoidFormPower.POWER_ID).amount;
        return blockAmount;
    }

    public boolean CheckDrawEmpty()
    {
        if (AbstractDungeon.player.drawPile.isEmpty() && !AbstractDungeon.actionManager.turnHasEnded
               && !AbstractDungeon.isScreenUp && (AbstractDungeon.getCurrRoom()).phase == AbstractRoom.RoomPhase.COMBAT)
            return true;
        return false;
    }

    @Override
    public void updateDescription()
    {
        description = DESCRIPTIONS[0] + amount +  DESCRIPTIONS[1];
    }

    @Override
    public AbstractPower makeCopy()
    {
        return new VoidPower(owner, source, amount);
    }
}
