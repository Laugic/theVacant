package theVacant.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.defect.ChannelAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.random.Random;
import theVacant.cards.Special.*;
import theVacant.orbs.*;
import theVacant.powers.InvisibleGemOrbPower;

public class MineGemAction extends AbstractGameAction
{
    private AbstractGemOrb gem;
    boolean chipOrb = false;
    int maxSize = -1;

    public MineGemAction(AbstractGemOrb newOrbType, boolean chip)
    {
        actionType = ActionType.SPECIAL;
        duration = Settings.ACTION_DUR_FAST;
        gem = newOrbType;
        chipOrb = chip;
    }

    public MineGemAction(AbstractGemOrb gemOrb, int maxSize)
    {
        actionType = ActionType.SPECIAL;
        duration = Settings.ACTION_DUR_FAST;
        gem = gemOrb;
        this.maxSize = maxSize;
    }

    public MineGemAction(AbstractGemOrb newOrbType)
    {
        actionType = ActionType.SPECIAL;
        duration = Settings.ACTION_DUR_FAST;
        gem = newOrbType;
    }

    public void update()
    {
        if(gem == null)
            gem = GetRandomGem();
        
        if(maxSize > -1 && gem.passiveAmount > maxSize)
            gem.reduceSize(gem.passiveAmount - maxSize);

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

    private AbstractGemOrb GetRandomGem()
    {
        int rand = AbstractDungeon.cardRandomRng.random(46);
        if(rand < 10)
            return new RubyOrb(AbstractDungeon.miscRng.random(3,6));
        if(rand < 20)
            return new SapphireOrb(AbstractDungeon.miscRng.random(3,6));
        if(rand < 25)
            return new OpalOrb(AbstractDungeon.miscRng.random(2,3));
        if(rand < 30)
            return new EmeraldOrb(AbstractDungeon.miscRng.random(2,3));
        if(rand < 35)
            return new OnyxOrb(AbstractDungeon.miscRng.random(2,3));
        if(rand < 40)
            return new AmethystOrb(AbstractDungeon.miscRng.random(3, 6));
        if(rand < 45)
            return new TopazOrb(AbstractDungeon.miscRng.random(2, 3));
        return new DiamondOrb(AbstractDungeon.miscRng.random(1,3));
    }
}
