package theVacant.potions;

import basemod.BaseMod;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.PowerTip;
import com.megacrit.cardcrawl.localization.PotionStrings;
import com.megacrit.cardcrawl.potions.AbstractPotion;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import theVacant.VacantMod;
import theVacant.actions.VacantMillAction;
import theVacant.powers.ShroudPower;

public class InvisibilityPotion extends AbstractPotion
{
    public static final String POTION_ID = VacantMod.makeID(InvisibilityPotion.class.getSimpleName());
    private static final PotionStrings potionStrings = CardCrawlGame.languagePack.getPotionString(POTION_ID);

    public static final String NAME = potionStrings.NAME;
    public static final String[] DESCRIPTIONS = potionStrings.DESCRIPTIONS;

    public InvisibilityPotion()
    {
        super(NAME, POTION_ID, PotionRarity.RARE, PotionSize.T, PotionColor.BLUE);
        potency = getPotency();
        description = DESCRIPTIONS[0] + potency + DESCRIPTIONS[1];
        isThrown = false;
        labOutlineColor = VacantMod.VACANT_COLOR;
        tips.add(new PowerTip(name, description));
        tips.add(new PowerTip(BaseMod.getKeywordProper("thevacant:shroud"), BaseMod.getKeywordDescription("thevacant:shroud")));
    }

    @Override
    public void use(AbstractCreature target)
    {
        if (AbstractDungeon.getCurrRoom().phase == AbstractRoom.RoomPhase.COMBAT)
        {
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new ShroudPower(
                    AbstractDungeon.player, AbstractDungeon.player, potency), potency));
        }
    }

    @Override
    public AbstractPotion makeCopy() {
        return new InvisibilityPotion();
    }

    @Override
    public int getPotency(final int potency) {
        return 6;
    }
}