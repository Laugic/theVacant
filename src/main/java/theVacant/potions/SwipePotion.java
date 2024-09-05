package theVacant.potions;

import basemod.BaseMod;
import basemod.helpers.TooltipInfo;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.GameDictionary;
import com.megacrit.cardcrawl.helpers.PowerTip;
import com.megacrit.cardcrawl.helpers.TipHelper;
import com.megacrit.cardcrawl.localization.PotionStrings;
import com.megacrit.cardcrawl.potions.AbstractPotion;
import com.megacrit.cardcrawl.powers.LoseStrengthPower;
import com.megacrit.cardcrawl.powers.StrengthPower;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import theVacant.VacantMod;
import theVacant.actions.VacantMillAction;
import theVacant.powers.VoidPower;
import theVacant.util.KeywordManager;

public class SwipePotion extends AbstractPotion
{
    public static final String POTION_ID = VacantMod.makeID(SwipePotion.class.getSimpleName());
    private static final PotionStrings potionStrings = CardCrawlGame.languagePack.getPotionString(POTION_ID);

    public static final String NAME = potionStrings.NAME;
    public static final String[] DESCRIPTIONS = potionStrings.DESCRIPTIONS;

    public SwipePotion()
    {
        super(NAME, POTION_ID, PotionRarity.UNCOMMON, PotionSize.SPHERE, PotionColor.SMOKE);
        potency = getPotency();
        description = DESCRIPTIONS[0] + potency * 4 + DESCRIPTIONS[1] + potency + DESCRIPTIONS[2];
        isThrown = false;
        labOutlineColor = VacantMod.VACANT_COLOR;
        tips.add(new PowerTip(name, description));
        tips.add(new PowerTip(BaseMod.getKeywordProper("thevacant:mill"), BaseMod.getKeywordDescription("thevacant:mill")));
        tips.add(new PowerTip(BaseMod.getKeywordProper("thevacant:void"), BaseMod.getKeywordDescription("thevacant:void")));
    }

    @Override
    public void use(AbstractCreature target)
    {
        if (AbstractDungeon.getCurrRoom().phase == AbstractRoom.RoomPhase.COMBAT)
        {
            AbstractDungeon.actionManager.addToBottom(new VacantMillAction(potency * 4));
            addToBot(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new VoidPower(AbstractDungeon.player, AbstractDungeon.player, potency), potency));
        }
    }

    @Override
    public AbstractPotion makeCopy() {
        return new SwipePotion();
    }

    @Override
    public int getPotency(final int potency) {
        return 2;
    }
}
