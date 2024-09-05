package theVacant.actions;

import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theVacant.vfx.HorizontalThrowVFX;

public class SoulBarrageAction extends AbstractGameAction {
    private DamageInfo info;

    private Color color;
    AbstractCard card;

    public SoulBarrageAction(int num, AbstractCreature target, DamageInfo info, Color color) {
        amount = num;
        this.duration = Settings.ACTION_DUR_XFAST;
        this.info = info;
        this.actionType = ActionType.DAMAGE;
        this.target = target;
        this.color = color;
        card = null;
    }

    public SoulBarrageAction(Color color, AbstractCard card){
        target = null;
        amount = 1;
        this.actionType = ActionType.DAMAGE;
        this.color = color;
        this.card = card.makeCopy();
    }

    public void update() {
        if(target == null)
            target = AbstractDungeon.getMonsters().getRandomMonster(null, true, AbstractDungeon.cardRandomRng);
        if(target == null || target.isDeadOrEscaped() || target.isDying){
            isDone = true;
            return;
        }
        if (card != null) {
            card.calculateCardDamage((AbstractMonster)target);
            info = new DamageInfo(AbstractDungeon.player, card.damage, card.damageTypeForTurn);
        }
        for (int i = 0; i < amount; i++) {
                addToTop(new DamageAction(target, info, true));
                if (target.hb != null)
                    addToTop(new VFXAction(new HorizontalThrowVFX(this.target.hb.cX, this.target.hb.cY, color)));
        }
        isDone = true;
    }
}