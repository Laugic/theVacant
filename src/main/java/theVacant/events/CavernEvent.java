package theVacant.events;

import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.events.AbstractImageEvent;
import com.megacrit.cardcrawl.helpers.CardLibrary;
import com.megacrit.cardcrawl.localization.EventStrings;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.vfx.RainingGoldEffect;
import com.megacrit.cardcrawl.vfx.cardManip.PurgeCardEffect;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndObtainEffect;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import theVacant.VacantMod;
import theVacant.cards.Skills.WailOfTheShroud;
import theVacant.relics.*;
import theVacant.vfx.HollowParticle;
import theVacant.vfx.SparkleParticle;

import static theVacant.VacantMod.makeEventPath;

public class CavernEvent extends AbstractImageEvent {

    public static final String ID = VacantMod.makeID(CavernEvent.class.getSimpleName());
    private static final EventStrings eventStrings = CardCrawlGame.languagePack.getEventString(ID);

    private static final String NAME = eventStrings.NAME;
    private static final String[] DESCRIPTIONS = eventStrings.DESCRIPTIONS;
    private static final String[] OPTIONS = eventStrings.OPTIONS;
    public static final String IMG = makeEventPath("ShroudEvent.png");
    private static final Logger logger = LogManager.getLogger(CavernEvent.class.getName());

    private int LOSE_HP_1 = 3, LOSE_HP_2 = 8, ASCENSION_EXTRA_HP = 2;

    private boolean cardSelect = false;
    private AbstractCard offeredCard;

    private static int counter = 0;

    public CavernEvent()
    {
        super(NAME, DESCRIPTIONS[0], IMG);

        if (AbstractDungeon.ascensionLevel >= 15){
            LOSE_HP_1 += ASCENSION_EXTRA_HP;
            LOSE_HP_2 += ASCENSION_EXTRA_HP;
        }

        imageEventText.setDialogOption(OPTIONS[2] + LOSE_HP_1 + OPTIONS[3]);
        imageEventText.setDialogOption(OPTIONS[1]);
    }

    @Override
    protected void buttonEffect(int buttonPressed)
    {
        switch (screenNum) {
            case 0:
                switch (buttonPressed) {
                    case 0:
                        screenNum = 1;
                        CardCrawlGame.sound.play("ATTACK_POISON");
                        AbstractDungeon.player.damage(new DamageInfo(null, LOSE_HP_1));
                        imageEventText.updateBodyText(DESCRIPTIONS[screenNum]);
                        imageEventText.updateDialogOption(0, OPTIONS[4] + LOSE_HP_1 + OPTIONS[3]);
                        imageEventText.updateDialogOption(1, OPTIONS[1]);
                        break;
                    case 1:
                        screenNum = 13;
                        imageEventText.updateBodyText(DESCRIPTIONS[screenNum]);
                        imageEventText.updateDialogOption(0, OPTIONS[0]);
                        imageEventText.removeDialogOption(1);
                        break;
                    default:
                        openMap();
                        break;
                }
                logger.info("ERROR: case " + buttonPressed + " should never be called");
                break;
            case 1:
                switch (buttonPressed) {
                    case 0:
                        screenNum = 2;
                        CardCrawlGame.sound.play("ATTACK_POISON");
                        AbstractDungeon.player.damage(new DamageInfo(null, LOSE_HP_1));
                        imageEventText.updateBodyText(DESCRIPTIONS[screenNum]);
                        imageEventText.updateDialogOption(0, OPTIONS[5] + LOSE_HP_2 + OPTIONS[6]);
                        imageEventText.updateDialogOption(1, OPTIONS[7]);
                        imageEventText.updateDialogOption(2, OPTIONS[8]);
                        break;
                    case 1:
                        screenNum = 13;
                        imageEventText.updateBodyText(DESCRIPTIONS[screenNum]);
                        imageEventText.updateDialogOption(0, OPTIONS[0]);
                        imageEventText.removeDialogOption(1);
                        break;
                    default:
                        openMap();
                        break;
                }
                logger.info("ERROR: case " + buttonPressed + " should never be called");
                break;
            case 2:
                switch (buttonPressed) {
                    case 0:
                        screenNum = 3;
                        CardCrawlGame.sound.play("ATTACK_POISON");
                        AbstractDungeon.player.damage(new DamageInfo(null, LOSE_HP_2));
                        AbstractDungeon.getCurrRoom().spawnRelicAndObtain((Settings.WIDTH / 2f), (Settings.HEIGHT / 2f), new ShroudRelic());
                        imageEventText.updateBodyText(DESCRIPTIONS[screenNum]);
                        imageEventText.updateDialogOption(0, OPTIONS[0]);
                        imageEventText.removeDialogOption(2);
                        imageEventText.removeDialogOption(1);
                        imageEventText.update();
                        break;
                    case 1:
                        screenNum = 4;
                        int goldAmount = AbstractDungeon.miscRng.random(50, 80);
                        AbstractDungeon.effectList.add(new RainingGoldEffect(goldAmount));
                        AbstractDungeon.player.gainGold(goldAmount);
                        imageEventText.updateBodyText(DESCRIPTIONS[screenNum]);
                        imageEventText.updateDialogOption(0, OPTIONS[0]);
                        imageEventText.removeDialogOption(2);
                        imageEventText.removeDialogOption(1);
                        imageEventText.update();
                        break;
                    case 2:
                        screenNum = 5;
                        if (CardGroup.getGroupWithoutBottledCards(AbstractDungeon.player.masterDeck.getPurgeableCards())
                                .size() > 0) {
                            AbstractDungeon.gridSelectScreen.open(
                                    CardGroup.getGroupWithoutBottledCards(AbstractDungeon.player.masterDeck.getPurgeableCards()), 1, OPTIONS[20], false, false, false, true);
                            this.cardSelect = true;
                            break;
                        }
                        imageEventText.updateBodyText(DESCRIPTIONS[screenNum]);
                        imageEventText.updateDialogOption(0, OPTIONS[0]);
                        imageEventText.removeDialogOption(2);
                        imageEventText.removeDialogOption(1);
                        imageEventText.update();
                        break;
                    default:
                        openMap();
                        break;
                }
                logger.info("ERROR: case " + buttonPressed + " should never be called");
                break;
            case 8:
                switch (buttonPressed) {
                    case 0:
                        screenNum = 12;
                        AbstractDungeon.player.heal(20);
                        AbstractRelic reward = AbstractDungeon.returnRandomScreenlessRelic(
                                AbstractRelic.RelicTier.COMMON);
                        AbstractDungeon.getCurrRoom().spawnRelicAndObtain((Settings.WIDTH / 2f), (Settings.HEIGHT / 2f), reward);
                        imageEventText.updateBodyText(DESCRIPTIONS[screenNum]);
                        imageEventText.updateDialogOption(0, OPTIONS[0]);
                        imageEventText.removeDialogOption(1);
                        break;
                    case 1:
                        screenNum = 9;
                        AbstractDungeon.player.loseGold(30);
                        imageEventText.updateBodyText(DESCRIPTIONS[screenNum]);
                        imageEventText.updateDialogOption(0, OPTIONS[12]);
                        if(!AbstractDungeon.player.hasRelic(BrassGoblet.ID) && !AbstractDungeon.player.hasRelic(OverflowingGobletRelic.ID))
                            imageEventText.updateDialogOption(1, OPTIONS[19], true);
                        else if(AbstractDungeon.player.gold < 30)
                            imageEventText.updateDialogOption(1, OPTIONS[18], true);
                        else
                            imageEventText.updateDialogOption(1, OPTIONS[13]);
                        break;
                    default:
                        openMap();
                        break;
                }
                logger.info("ERROR: case " + buttonPressed + " should never be called");
                break;
            case 9:
                switch (buttonPressed) {
                    case 0:
                        screenNum = 12;
                        AbstractDungeon.player.heal(25);
                        AbstractRelic reward = AbstractDungeon.returnRandomScreenlessRelic(
                                AbstractRelic.RelicTier.UNCOMMON);
                        AbstractDungeon.getCurrRoom().spawnRelicAndObtain((Settings.WIDTH / 2f), (Settings.HEIGHT / 2f), reward);
                        imageEventText.updateBodyText(DESCRIPTIONS[screenNum]);
                        imageEventText.updateDialogOption(0, OPTIONS[0]);
                        imageEventText.removeDialogOption(1);
                        break;
                    case 1:
                        screenNum = 10;
                        AbstractDungeon.player.loseGold(30);
                        imageEventText.updateBodyText(DESCRIPTIONS[screenNum]);
                        imageEventText.updateDialogOption(0, OPTIONS[14] + LOSE_HP_2 + OPTIONS[15]);
                        imageEventText.updateDialogOption(1, OPTIONS[16]);
                        break;
                    default:
                        openMap();
                        break;
                }
                logger.info("ERROR: case " + buttonPressed + " should never be called");
                break;
            case 10:
                switch (buttonPressed) {
                    case 0:
                        screenNum = 11;
                        CardCrawlGame.sound.play("ATTACK_POISON");
                        AbstractDungeon.player.damage(new DamageInfo(null, LOSE_HP_2));
                        AbstractRelic reward = AbstractDungeon.returnRandomScreenlessRelic(
                                AbstractRelic.RelicTier.RARE);
                        AbstractDungeon.getCurrRoom().spawnRelicAndObtain((Settings.WIDTH / 2f), (Settings.HEIGHT / 2f), reward);

                        AbstractRelic enshrouded = null;
                        if(AbstractDungeon.player.hasRelic(BrassGoblet.ID))
                            enshrouded = new EnshroudedBrassGoblet();
                        if(AbstractDungeon.player.hasRelic(OverflowingGobletRelic.ID))
                            enshrouded = new EnshroudedOverflowingGobletRelic();
                        AbstractDungeon.getCurrRoom().spawnRelicAndObtain((Settings.WIDTH / 2f), (Settings.HEIGHT / 2f), enshrouded);

                        imageEventText.updateBodyText(DESCRIPTIONS[screenNum]);
                        imageEventText.updateDialogOption(0, OPTIONS[17]);
                        imageEventText.removeDialogOption(1);
                        break;
                    case 1:
                        openMap();
                        break;
                }
                logger.info("ERROR: case " + buttonPressed + " should never be called");
                break;
            default:
                openMap();
                break;
        }
    }

    public void update() {
        super.update();
        if (this.cardSelect && !AbstractDungeon.gridSelectScreen.selectedCards.isEmpty())
            checkOffering();

        counter++;
        if(MathUtils.randomBoolean())
            counter++;
        if(counter % 25 == 0)
            AbstractDungeon.topLevelEffectsQueue.add(new HollowParticle(240, 280, 400, 400));
        if(counter % 70 == 0)
            AbstractDungeon.topLevelEffectsQueue.add(new SparkleParticle(240, 280, 400, 400));
    }

    private void checkOffering() {
        offeredCard = AbstractDungeon.gridSelectScreen.selectedCards.remove(0);
        setReward(offeredCard.rarity);
        AbstractDungeon.topLevelEffects.add(new PurgeCardEffect(offeredCard, (Settings.WIDTH / 2f), (Settings.HEIGHT / 2f)));
        AbstractDungeon.player.masterDeck.removeCard(offeredCard);
        cardSelect = false;
    }

    private void setReward(AbstractCard.CardRarity rarity) {
        imageEventText.removeDialogOption(2);
        imageEventText.removeDialogOption(1);
        switch (rarity) {
            case CURSE:
                AbstractDungeon.effectList.add(new ShowCardAndObtainEffect(
                        CardLibrary.getCopy(WailOfTheShroud.ID), (Settings.WIDTH / 2f), (Settings.HEIGHT / 2f)));
                screenNum = 5;
                imageEventText.updateBodyText(DESCRIPTIONS[screenNum]);
                imageEventText.updateDialogOption(0, OPTIONS[0]);
                break;
            case UNCOMMON:
                screenNum = 7;
                AbstractDungeon.player.heal(20);
                imageEventText.updateBodyText(DESCRIPTIONS[screenNum]);
                imageEventText.updateDialogOption(0, OPTIONS[9]);
                break;
            case RARE:
                screenNum = 8;
                imageEventText.updateBodyText(DESCRIPTIONS[screenNum]);
                imageEventText.updateDialogOption(0, OPTIONS[10]);
                if(AbstractDungeon.player.gold >= 30)
                    imageEventText.updateDialogOption(1, OPTIONS[11]);
                else
                    imageEventText.updateDialogOption(1, OPTIONS[18], true);
                break;
            case BASIC:
            case COMMON:
            case SPECIAL:
            default:
                screenNum = 6;
                imageEventText.updateBodyText(DESCRIPTIONS[screenNum]);
                imageEventText.updateDialogOption(0, OPTIONS[0]);
                break;
        }
    }
}

