package theVacant.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.actions.defect.ChannelAction;
import com.megacrit.cardcrawl.actions.defect.IncreaseMaxOrbAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.orbs.AbstractOrb;
import com.megacrit.cardcrawl.orbs.EmptyOrbSlot;
import com.megacrit.cardcrawl.powers.AbstractPower;
import theVacant.orbs.*;
import theVacant.powers.EtchPower;
import theVacant.powers.VoidEmbracePower;
import theVacant.powers.VoidPower;

public class MineGemAction extends AbstractGameAction
{
    private AbstractOrb orbType;
    int size = 0;

    public MineGemAction(AbstractOrb gemOrb, int size)
    {
        actionType = ActionType.SPECIAL;
        duration = Settings.ACTION_DUR_FAST;
        orbType = gemOrb;
        this.size = size;
    }

    public MineGemAction(AbstractOrb newOrbType)
    {
        actionType = ActionType.SPECIAL;
        duration = Settings.ACTION_DUR_FAST;
        orbType = newOrbType;
        size = orbType.passiveAmount;
    }

    public void update()
    {
        if(orbType == null)
            orbType = GetRandomGem();

        addToTop(new ChannelAction(orbType, false));
        addToTop(new IncreaseMaxOrbAction(1));

        isDone = true;
    }

    private AbstractOrb GetRandomGem()
    {
        int RandOrb = AbstractDungeon.cardRandomRng.random(5);
        switch (RandOrb)
        {
            case 1:
                return new AmethystOrb(size);
            case 2:
                return new SapphireOrb(size);
            case 3:
                return new OpalOrb(size);
            case 4:
                return new OnyxOrb(size);
            default:
                return new RubyOrb(size);
        }
    }
}
