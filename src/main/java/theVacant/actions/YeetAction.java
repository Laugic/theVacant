package theVacant.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.AttackDamageRandomEnemyAction;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.vfx.combat.FlickCoinEffect;

public class YeetAction extends AttackDamageRandomEnemyAction
{
    public YeetAction(AbstractCard card)
    {
        super(card);
    }

    public void update()
    {
        if (!Settings.FAST_MODE)
            addToTop(new WaitAction(0.1F));
        super.update();
        if (this.target != null)
            AbstractDungeon.actionManager.addToTop(new VFXAction(new FlickCoinEffect(AbstractDungeon.player.hb.cX, AbstractDungeon.player.hb.cY, this.target.hb.cX, this.target.hb.cY), 0.2F));
    }
}