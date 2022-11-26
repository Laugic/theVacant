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
import theVacant.orbs.RubyOrb;
import theVacant.orbs.SapphireOrb;

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
        if (AbstractDungeon.player == null || !AbstractDungeon.player.hasRelic("SacredBark"))
            description = potionStrings.DESCRIPTIONS[0];
        else
            description = potionStrings.DESCRIPTIONS[1];
        tips.clear();
        tips.add(new PowerTip(name, description));
        tips.add(new PowerTip(BaseMod.getKeywordProper("thevacant:mine"), BaseMod.getKeywordDescription("thevacant:mine")));
        tips.add(new PowerTip(BaseMod.getKeywordProper("thevacant:ruby"), BaseMod.getKeywordDescription("thevacant:ruby")));
        tips.add(new PowerTip(BaseMod.getKeywordProper("thevacant:sapphire"), BaseMod.getKeywordDescription("thevacant:sapphire")));
    }

    @Override
    public void use(AbstractCreature target)
    {
        if (AbstractDungeon.getCurrRoom().phase == AbstractRoom.RoomPhase.COMBAT)
        {
            for(int i = 0; i < potency; ++i)
            {
                addToBot(new MineGemAction(new RubyOrb(2)));
                addToBot(new MineGemAction(new SapphireOrb(2)));
            }
        }
    }

    @Override
    public AbstractPotion makeCopy() {
        return new MiningPotion();
    }

    @Override
    public int getPotency(final int potency) {
        return 1;
    }
}
