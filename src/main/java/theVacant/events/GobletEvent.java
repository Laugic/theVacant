package theVacant.events;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.cards.colorless.Apotheosis;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.events.AbstractImageEvent;
import com.megacrit.cardcrawl.helpers.RelicLibrary;
import com.megacrit.cardcrawl.helpers.ScreenShake;
import com.megacrit.cardcrawl.localization.EventStrings;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.vfx.cardManip.PurgeCardEffect;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndObtainEffect;
import theVacant.VacantMod;
import theVacant.relics.BrassGoblet;
import theVacant.relics.OverflowingGobletRelic;
import theVacant.util.TextureLoader;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static theVacant.VacantMod.makeEventPath;

public class GobletEvent extends AbstractImageEvent {

    public static final String ID = VacantMod.makeID(GobletEvent.class.getSimpleName());
    private static final EventStrings eventStrings = CardCrawlGame.languagePack.getEventString(ID);

    private static final String NAME = eventStrings.NAME;
    private static final String[] DESCRIPTIONS = eventStrings.DESCRIPTIONS;
    private static final String[] OPTIONS = eventStrings.OPTIONS;
    public static final String IMG = makeEventPath("GobletEvent.png");
    private static final Logger logger = LogManager.getLogger(GobletEvent.class.getName());

    private int LOSE_HP = 6, BUTTON_PRESSES = 0;

    public GobletEvent()
    {
        super(NAME, DESCRIPTIONS[0], IMG);

        if (AbstractDungeon.ascensionLevel >= 15)
            LOSE_HP += 2;

        imageEventText.setDialogOption(OPTIONS[0] + LOSE_HP * (BUTTON_PRESSES + 1) + OPTIONS[1]);
        imageEventText.setDialogOption(OPTIONS[2]);
    }

    @Override
    protected void buttonEffect(int buttonPressed)
    {
        switch (screenNum) {
            case 0:
                switch (buttonPressed) {
                    case 0:
                        AbstractDungeon.player.damage(new DamageInfo(null, LOSE_HP * (++BUTTON_PRESSES)));
                        if(AbstractDungeon.player.hasRelic(BrassGoblet.ID))
                            AbstractDungeon.player.getRelic(BrassGoblet.ID).counter++;
                        if(AbstractDungeon.player.hasRelic(OverflowingGobletRelic.ID))
                            AbstractDungeon.player.getRelic(OverflowingGobletRelic.ID).counter++;
                        CardCrawlGame.sound.play("ATTACK_POISON");
                        imageEventText.updateBodyText(DESCRIPTIONS[2]);
                        imageEventText.updateDialogOption(0, OPTIONS[0] + LOSE_HP * (BUTTON_PRESSES + 1) + OPTIONS[1]);
                        break;
                    case 1:
                        imageEventText.updateBodyText(DESCRIPTIONS[3]);
                        imageEventText.updateDialogOption(0, OPTIONS[3]);
                        imageEventText.removeDialogOption(1);
                        screenNum = 1;
                        break;
                }
                logger.info("ERROR: case " + buttonPressed + " should never be called");
                break;
            case 1:
                openMap();
                break;
        }
    }

    public void update() {
        super.update();
    }
}
