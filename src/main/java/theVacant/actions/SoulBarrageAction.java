package theVacant.actions;

import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.Settings;
import theVacant.vfx.HorizontalThrowVFX;

public class SoulBarrageAction extends AbstractGameAction {
    private DamageInfo info;

    private Color color;

    public SoulBarrageAction(int num, AbstractCreature target, DamageInfo info, Color color) {
        amount = num;
        this.duration = Settings.ACTION_DUR_XFAST;
        this.info = info;
        this.actionType = AbstractGameAction.ActionType.BLOCK;
        this.target = target;
        this.color = color;
    }

    public void update() {
        for (int i = 0; i < amount; i++) {
                addToTop(new DamageAction(this.target, this.info, true));
                if (this.target != null && this.target.hb != null)
                    addToTop(new VFXAction(new HorizontalThrowVFX(this.target.hb.cX, this.target.hb.cY, color)));
        }
        this.isDone = true;
    }
}