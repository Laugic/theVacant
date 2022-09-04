package theVacant.actions;

import basemod.interfaces.CloneablePowerInterface;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import theVacant.VacantMod;
import theVacant.powers.EtchPower;
import theVacant.powers.VoidEmbracePower;
import theVacant.powers.VoidPower;

public class CurseAction extends AbstractGameAction
{
    private float startingDuration;
    private AbstractMonster monster;
    private boolean all;

    public CurseAction(AbstractMonster target)
    {
        this.actionType = ActionType.DEBUFF;
        this.startingDuration = Settings.ACTION_DUR_FAST;
        this.duration = this.startingDuration;
        monster = target;
    }
    public CurseAction(boolean allEnemies)
    {
        this.actionType = ActionType.DEBUFF;
        this.startingDuration = Settings.ACTION_DUR_FAST;
        this.duration = this.startingDuration;
        this.all = allEnemies;
        monster = null;
    }

    public void update()
    {
        AbstractPlayer player = AbstractDungeon.player;
        if(player != null && player.powers.size() > 0)
        {
            for(AbstractPower power: player.powers)
            {
                if(power.type.equals(AbstractPower.PowerType.DEBUFF) && !VacantMod.IMMUNE_POWERS.contains(power.ID) && power instanceof CloneablePowerInterface)
                {
                    if(all)
                    {
                        for (AbstractMonster mo : (AbstractDungeon.getCurrRoom()).monsters.monsters)
                        {
                            AbstractPower newPower = ((CloneablePowerInterface) power).makeCopy();
                            newPower.owner = mo;
                            addToBot(new ApplyPowerAction(mo, player, newPower));
                        }
                    }
                    else if(monster != null)
                    {
                        AbstractPower newPower = ((CloneablePowerInterface) power).makeCopy();
                        newPower.owner = monster;
                        addToBot(new ApplyPowerAction(monster, player, newPower));
                    }
                }
            }
        }

        this.isDone = true;
    }
}
