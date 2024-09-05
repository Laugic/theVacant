package theVacant.potions;

import basemod.BaseMod;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.PowerTip;
import com.megacrit.cardcrawl.localization.PotionStrings;
import com.megacrit.cardcrawl.potions.AbstractPotion;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import theVacant.VacantMod;
import theVacant.actions.MineGemAction;
import theVacant.actions.SpelunkAction;
import theVacant.actions.VacantMillAction;
import theVacant.orbs.*;

public class MiningPotion extends AbstractPotion
{
    public static final String POTION_ID = VacantMod.makeID(MiningPotion.class.getSimpleName());
    private static final PotionStrings potionStrings = CardCrawlGame.languagePack.getPotionString(POTION_ID);

    public static final String NAME = potionStrings.NAME;
    public static final String[] DESCRIPTIONS = potionStrings.DESCRIPTIONS;

    public MiningPotion()
    {
        super(NAME, POTION_ID, PotionRarity.COMMON, PotionSize.BOLT, PotionColor.BLUE);
        potency = getPotency();
        description = DESCRIPTIONS[0];
        labOutlineColor = VacantMod.VACANT_COLOR;
        isThrown = false;
    }

    @Override
    public void initializeData()
    {
        potency = getPotency();
        description = DESCRIPTIONS[0] + potency + DESCRIPTIONS[1];
        tips.clear();
        tips.add(new PowerTip(name, description));
        tips.add(new PowerTip(BaseMod.getKeywordProper("thevacant:mine"), BaseMod.getKeywordDescription("thevacant:mine")));
        tips.add(new PowerTip(BaseMod.getKeywordProper("thevacant:emerald"), BaseMod.getKeywordDescription("thevacant:emerald")));
        tips.add(new PowerTip(BaseMod.getKeywordProper("thevacant:opal"), BaseMod.getKeywordDescription("thevacant:opal")));
        tips.add(new PowerTip(BaseMod.getKeywordProper("thevacant:chip"), BaseMod.getKeywordDescription("thevacant:chip")));
    }

    @Override
    public void use(AbstractCreature target)
    {
        if (AbstractDungeon.getCurrRoom().phase == AbstractRoom.RoomPhase.COMBAT)
        {
            addToBot(new MineGemAction(new EmeraldOrb(potency), true));
            addToBot(new MineGemAction(new OpalOrb(potency), true));
            /*for(int i = 0; i < potency; ++i)
                MineRandomGem();*/
        }
    }
/*
    private void MineRandomGem() {
        int gemRoll = AbstractDungeon.cardRandomRng.random(90);
        if(gemRoll < 15)
            addToBot(new MineGemAction(new RubyOrb(1), true));
        else if(gemRoll < 30)
            addToBot(new MineGemAction(new SapphireOrb(1), true));
        else if(gemRoll < 45)
            addToBot(new MineGemAction(new AmethystOrb(1), true));
        else if(gemRoll < 55)
            addToBot(new MineGemAction(new EmeraldOrb(1), true));
        else if(gemRoll < 65)
            addToBot(new MineGemAction(new OpalOrb(1), true));
        else if(gemRoll < 75)
            addToBot(new MineGemAction(new TopazOrb(1), true));
        else if(gemRoll < 85)
            addToBot(new MineGemAction(new OnyxOrb(1), true));
        else
            addToBot(new MineGemAction(new DiamondOrb(1), true));

    }
*/
    @Override
    public AbstractPotion makeCopy() {
        return new MiningPotion();
    }

    @Override
    public int getPotency(final int potency) {
        return 2;
    }
}
