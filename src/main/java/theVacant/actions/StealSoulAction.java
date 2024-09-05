package theVacant.actions;

import basemod.interfaces.CloneablePowerInterface;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.ArtifactPower;
import com.megacrit.cardcrawl.powers.StrengthPower;
import com.megacrit.cardcrawl.vfx.combat.FlyingOrbEffect;
import com.megacrit.cardcrawl.vfx.combat.InflameEffect;
import theVacant.VacantMod;
import theVacant.powers.AntifactPower;

public class StealSoulAction extends AbstractGameAction
{
    private AbstractMonster monster;

    public StealSoulAction(AbstractMonster target, int energy)
    {
        this.actionType = ActionType.DEBUFF;
        monster = target;
        amount = energy;
    }

    public void update()
    {
        AbstractPlayer player = AbstractDungeon.player;

        int artNum = 0;
        if(monster.hasPower(ArtifactPower.POWER_ID))
            artNum = monster.getPower(ArtifactPower.POWER_ID).amount;
        if(artNum > 0)
            addToBot(new ReducePowerAction(monster, player, monster.getPower(ArtifactPower.POWER_ID), amount));
        if(artNum < amount)
            addToBot(new ApplyPowerAction(monster, player, new AntifactPower(monster, player, amount - artNum),amount - artNum));

        addToBot(new ApplyPowerAction(monster, player, new StrengthPower(monster, -amount)));

        for (int j = 0; j < 5; j++)
            addToBot(new VFXAction(new FlyingOrbEffect(monster.hb.cX, monster.hb.cY)));
        addToBot(new VFXAction(player, new InflameEffect(player), 1.0F));

        addToBot(new ApplyPowerAction(player, player, new ArtifactPower(player, amount)));
        addToBot(new ApplyPowerAction(player, player, new StrengthPower(player, amount)));
        isDone = true;
    }
}
