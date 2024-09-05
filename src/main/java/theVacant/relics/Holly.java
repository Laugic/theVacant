package theVacant.relics;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.evacipated.cardcrawl.mod.stslib.relics.OnReceivePowerRelic;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;
import theVacant.VacantMod;
import theVacant.actions.ReduceRandomDebuffAction;
import theVacant.util.TextureLoader;

import static theVacant.VacantMod.makeRelicOutlinePath;
import static theVacant.VacantMod.makeRelicPath;

public class Holly extends CustomRelic implements OnReceivePowerRelic
{

    public static final String ID = VacantMod.makeID(Holly.class.getSimpleName());

    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath("holly_relic.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(makeRelicOutlinePath("holly_relic.png"));

    public Holly() {
        super(ID, IMG, OUTLINE, RelicTier.RARE, LandingSound.MAGICAL);
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

    @Override
    public boolean onReceivePower(AbstractPower power, AbstractCreature target) {
        if(power.owner == AbstractDungeon.player && power.type == AbstractPower.PowerType.DEBUFF){
            flash();
            if(Math.abs(power.amount) != 1){
                if(power.amount > 0){
                    power.amount = Math.max(1, power.amount - 1);
                }
                else
                    power.amount = Math.min(-1, power.amount + 1);
            }
            power.updateDescription();
        }
        return true;
    }

    @Override
    public int onReceivePowerStacks(AbstractPower power, AbstractCreature source, int stackAmount) {
        if(power.owner == AbstractDungeon.player && power.type == AbstractPower.PowerType.DEBUFF)
        {
            flash();
            int amount = stackAmount;
            if(Math.abs(stackAmount) != 1){
                if(stackAmount > 0){
                    amount = Math.max(1, stackAmount - 1);
                }
                else
                    amount = Math.min(-1, stackAmount + 1);
            }
            power.updateDescription();
            return amount;
        }
        return OnReceivePowerRelic.super.onReceivePowerStacks(power, source, stackAmount);
    }
}