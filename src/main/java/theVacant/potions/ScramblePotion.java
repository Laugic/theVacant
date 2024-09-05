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
import theVacant.actions.ScrambleAction;
import theVacant.actions.VacantMillAction;

public class ScramblePotion extends AbstractPotion
{
    public static final String POTION_ID = VacantMod.makeID(ScramblePotion.class.getSimpleName());
    private static final PotionStrings potionStrings = CardCrawlGame.languagePack.getPotionString(POTION_ID);

    public static final String NAME = potionStrings.NAME;
    public static final String[] DESCRIPTIONS = potionStrings.DESCRIPTIONS;

    public ScramblePotion()
    {
        super(NAME, POTION_ID, PotionRarity.UNCOMMON, PotionSize.H, PotionColor.ANCIENT);
        potency = getPotency();
        description = DESCRIPTIONS[0] + potency + DESCRIPTIONS[1];
        isThrown = false;
        labOutlineColor = VacantMod.VACANT_COLOR;
        tips.add(new PowerTip(name, description));
    }

    @Override
    public void use(AbstractCreature target)
    {
        if (AbstractDungeon.getCurrRoom().phase == AbstractRoom.RoomPhase.COMBAT)
            addToBot(new ScrambleAction(potency));
    }

    @Override
    public AbstractPotion makeCopy() {
        return new ScramblePotion();
    }

    @Override
    public int getPotency(final int potency) {
        return 2;
    }
}
