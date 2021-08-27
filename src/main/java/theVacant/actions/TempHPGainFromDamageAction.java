package theVacant.actions;

import com.evacipated.cardcrawl.mod.stslib.actions.tempHp.AddTemporaryHPAction;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.HealAction;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;
import com.megacrit.cardcrawl.vfx.combat.FlashAtkImgEffect;
import com.megacrit.cardcrawl.vfx.combat.FlyingOrbEffect;

public class TempHPGainFromDamageAction extends AbstractGameAction
{
    int damage;
    public TempHPGainFromDamageAction(AbstractCreature source, AbstractCreature target, int amount, DamageInfo.DamageType type, AbstractGameAction.AttackEffect effect)
    {
        setValues(null, source, amount);
        this.damage = amount;
        this.actionType = AbstractGameAction.ActionType.DAMAGE;
        this.damageType = type;
        this.attackEffect = effect;
        this.duration = Settings.ACTION_DUR_FAST;
        this.target = target;
    }

    public void update()
    {
        tickDuration();
        if (this.isDone)
        {
            int healAmount = 0;
            if (!target.isDying && target.currentHealth > 0 && !target.isEscaping)
            {
                target.damage(new DamageInfo(this.source, this.damage, this.damageType));
                if (target.lastDamageTaken > 0)
                {
                    healAmount += target.lastDamageTaken;
                    for (int j = 0; j < target.lastDamageTaken / 2 && j < 10; j++)
                        addToBot(new VFXAction(new FlyingOrbEffect(target.hb.cX, target.hb.cY)));
                }
            }
            if (healAmount > 0)
            {
                if (!Settings.FAST_MODE)
                    addToBot(new WaitAction(0.3F));
                addToBot(new AddTemporaryHPAction(source, source, healAmount));
            }
            if ((AbstractDungeon.getCurrRoom()).monsters.areMonstersBasicallyDead())
                AbstractDungeon.actionManager.clearPostCombatActions();
            addToTop(new WaitAction(0.1F));
        }
    }
}
