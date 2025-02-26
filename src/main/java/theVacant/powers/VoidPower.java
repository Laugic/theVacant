package theVacant.powers;

import basemod.interfaces.CloneablePowerInterface;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import theVacant.VacantMod;
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

    int cardsThisTurn = 0;

    public VoidPower(final AbstractCreature owner, final AbstractCreature source, final int amount)
    {
        name = NAME;
        ID = POWER_ID;

        this.owner = owner;
        this.amount = amount;
        this.source = source;

        type = PowerType.BUFF;
        isTurnBased = false;

        region128 = new TextureAtlas.AtlasRegion(tex84, 0, 0, 84, 84);
        region48 = new TextureAtlas.AtlasRegion(tex32, 0, 0, 32, 32);

        updateDescription();
    }

    @Override
    public void atStartOfTurn() {
        cardsThisTurn = 0;
    }

    @Override
    public float atDamageGive(float damage, DamageInfo.DamageType type)
    {
        if ((CheckDrawEmpty() || (owner.hasPower(ShardPower.POWER_ID) && cardsThisTurn < owner.getPower(ShardPower.POWER_ID).amount))
                && type == DamageInfo.DamageType.NORMAL)
            return damage + amount;
        return damage;
    }

    @Override
    public float modifyBlock(float blockAmount)
    {
        if ((CheckDrawEmpty() || (owner.hasPower(ShardPower.POWER_ID) && cardsThisTurn < owner.getPower(ShardPower.POWER_ID).amount))
                && blockAmount > 0.0F)
            return blockAmount + amount;
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
    public void onAfterUseCard(AbstractCard card, UseCardAction action) {
        if(!card.purgeOnUse)
            cardsThisTurn++;
    }

    @Override
    public void updateDescription()
    {
        description = DESCRIPTIONS[0] + amount +  DESCRIPTIONS[1] + amount + DESCRIPTIONS[2];
    }

    @Override
    public AbstractPower makeCopy()
    {
        return new VoidPower(owner, source, amount);
    }
}
