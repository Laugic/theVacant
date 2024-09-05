package theVacant.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;
import theVacant.vfx.ShowCardAndMillEffect;

public class MillWaitAction extends AbstractGameAction {
    @Override
    public void update() {
        for (AbstractGameEffect effect: AbstractDungeon.topLevelEffects) {
            if(effect instanceof ShowCardAndMillEffect)
                return;
        }
        for (AbstractGameEffect effect: AbstractDungeon.effectList) {
            if(effect instanceof ShowCardAndMillEffect)
                return;
        }
        isDone = true;
    }
}
