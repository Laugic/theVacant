package theVacant.powers;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.evacipated.cardcrawl.mod.stslib.powers.interfaces.InvisiblePower;
import com.evacipated.cardcrawl.mod.stslib.powers.interfaces.OnReceivePowerPower;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import theVacant.VacantMod;
import theVacant.util.TextureLoader;

public class InvisibleDebuffTracker extends AbstractPower implements InvisiblePower, OnReceivePowerPower
{
    public AbstractCreature source;

    public static final String POWER_ID = VacantMod.makeID(InvisibleDebuffTracker.class.getSimpleName());
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    private static final Texture tex84 = TextureLoader.getTexture("theVacantResources/images/powers/husk_power84.png");
    private static final Texture tex32 = TextureLoader.getTexture("theVacantResources/images/powers/husk_power32.png");

    public static int numDebuffs = 0, debuffsThisTurn = 0, debuffsLastTurn = 0;

    public InvisibleDebuffTracker(final AbstractCreature owner, final AbstractCreature source, final int amount)
    {
        name = NAME;
        ID = POWER_ID;

        this.owner = owner;
        this.amount = amount;
        this.source = source;

        type = PowerType.valueOf("NEUTRAL");
        isTurnBased = false;
        numDebuffs = 0;
        debuffsThisTurn = 0;
        debuffsLastTurn = 0;

        this.region128 = new TextureAtlas.AtlasRegion(tex84, 0, 0, 84, 84);
        this.region48 = new TextureAtlas.AtlasRegion(tex32, 0, 0, 32, 32);

        updateDescription();
    }

    @Override
    public void atEndOfTurn(boolean isPlayer) {
        if(isPlayer){
            debuffsLastTurn = debuffsThisTurn;
            debuffsThisTurn = 0;
        }
    }

    @Override
    public boolean onReceivePower(AbstractPower power, AbstractCreature target, AbstractCreature source) {
        if(power.type == PowerType.DEBUFF && target == owner){
            debuffsThisTurn++;
            numDebuffs++;
        }
        return true;
    }
}
