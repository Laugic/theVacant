package theVacant.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.LoseHPAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.vfx.combat.InflameEffect;
import theVacant.cards.AbstractDynamicCard;
import theVacant.characters.TheVacant;
import theVacant.powers.*;

public class SyphonAction  extends AbstractGameAction
{
    public SyphonAction(int timesToSyphon)
    {
        amount = timesToSyphon;
    }

    public void update()
    {
        AbstractPlayer player = AbstractDungeon.player;
        if(amount > 0)
        {
            for(AbstractPower power: player.powers)
            {
                if(power instanceof FormPower)
                {
                    for (int i = 0; i < amount; i++)
                        ((FormPower) power).Syphon();
                }
            }
        }
        isDone = true;
    }
}
