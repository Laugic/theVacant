package theVacant.actions;

import basemod.BaseMod;
import basemod.interfaces.CloneablePowerInterface;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.vfx.ShineSparkleEffect;
import com.megacrit.cardcrawl.vfx.combat.MindblastEffect;
import theVacant.VacantMod;
import theVacant.vfx.ParticleEffect;

import java.util.ArrayList;

import static com.megacrit.cardcrawl.characters.AbstractPlayer.uiStrings;

public class ReduceRandomDebuffAction extends AbstractGameAction
{

    public static final String[] TEXT = uiStrings.TEXT;

    private float startingDuration;
    public int amount;
    public boolean random;

    public ReduceRandomDebuffAction(int amount)
    {
        this.actionType = ActionType.CARD_MANIPULATION;
        this.startingDuration = Settings.ACTION_DUR_FAST;
        this.duration = this.startingDuration;
        this.amount = amount;
    }

    public void update()
    {
        if(AbstractDungeon.player != null)
        {
            AbstractPlayer player = AbstractDungeon.player;
            ArrayList<AbstractPower> debuffs = new ArrayList<>();
            for(AbstractPower power: player.powers)
                if(power.type.equals(AbstractPower.PowerType.DEBUFF))
                    debuffs.add(power);
            if(debuffs.size() == 0)
            {
                isDone = true;
                return;
            }
            int selectedDebuff = AbstractDungeon.cardRandomRng.random(debuffs.size() - 1);
            if(debuffs.get(selectedDebuff).amount < 0 && debuffs.get(selectedDebuff) instanceof CloneablePowerInterface)
            {
                AbstractPower newPower = ((CloneablePowerInterface)debuffs.get(selectedDebuff)).makeCopy();
                newPower.amount = amount;
                addToTop(new ApplyPowerAction(player, player, newPower));
            }
            else if(debuffs.get(selectedDebuff).amount <= amount)
                addToTop(new RemoveSpecificPowerAction(player, player, debuffs.get(selectedDebuff)));
            else
                addToTop(new ReducePowerAction(player, player, debuffs.get(selectedDebuff), amount));
                for(int i = 0; i < 20; ++i)
                    AbstractDungeon.effectsQueue.add(new ShineSparkleEffect(player.hb.x + (float)Math.random() * player.hb.width, player.hb.y + (float)Math.random() * player.hb.height));
        }
        isDone = true;
    }
}
