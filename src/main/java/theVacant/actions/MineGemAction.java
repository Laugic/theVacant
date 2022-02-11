package theVacant.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.defect.ChannelAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.orbs.AbstractOrb;
import com.megacrit.cardcrawl.random.Random;
import theVacant.orbs.*;
import theVacant.powers.InvisibleGemOrbPower;

public class MineGemAction extends AbstractGameAction
{
    private AbstractOrb gem;
    int size = 0;
    boolean chipOrb = false;

    public MineGemAction(AbstractOrb newOrbType, boolean chip)
    {
        actionType = ActionType.SPECIAL;
        duration = Settings.ACTION_DUR_FAST;
        gem = newOrbType;
        size = gem.passiveAmount;
        chipOrb = chip;
    }

    public MineGemAction(AbstractOrb gemOrb, int size)
    {
        actionType = ActionType.SPECIAL;
        duration = Settings.ACTION_DUR_FAST;
        gem = gemOrb;
        this.size = size;
    }

    public MineGemAction(AbstractOrb newOrbType)
    {
        actionType = ActionType.SPECIAL;
        duration = Settings.ACTION_DUR_FAST;
        gem = newOrbType;
        size = gem.passiveAmount;
    }

    public void update()
    {
        if(gem == null)
            gem = GetRandomGem();

        addToTop(new ChannelAction(gem, false));

        if(!AbstractDungeon.player.hasPower(InvisibleGemOrbPower.POWER_ID))
            AbstractDungeon.player.powers.add(new InvisibleGemOrbPower(AbstractDungeon.player, AbstractDungeon.player, 1));

        AbstractDungeon.player.increaseMaxOrbSlots(1, false);
        Random rand = new Random();
        if(rand.randomBoolean())
            addToTop(new SFXAction("theVacant:gemSpawn"));
        else
            addToTop(new SFXAction("theVacant:gemSpawn2"));
        if(chipOrb)
            addToBot(new ChipOrbAction(gem, 1));
        isDone = true;
    }

    private AbstractOrb GetRandomGem()
    {
        int rand = AbstractDungeon.cardRandomRng.random(15);
        if(rand == 0 || rand == 1)
            return new EmeraldOrb(size);
        if(rand == 2 || rand == 3)
            return new OpalOrb(size);
        if(rand <= 7)
            return new SapphireOrb(size);
        if(rand <= 11)
            return new AmethystOrb(size);
        return new RubyOrb(size);
    }
}
