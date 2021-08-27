package theVacant.characters;

import basemod.abstracts.CustomPlayer;
import basemod.animations.SpineAnimation;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.math.MathUtils;
import com.esotericsoftware.spine.AnimationState;
import com.evacipated.cardcrawl.modthespire.lib.SpireEnum;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.EnergyManager;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.*;
import com.megacrit.cardcrawl.localization.CharacterStrings;
import com.megacrit.cardcrawl.localization.KeywordStrings;
import com.megacrit.cardcrawl.screens.CharSelectInfo;
import com.megacrit.cardcrawl.unlock.UnlockTracker;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import theVacant.VacantMod;
import theVacant.cards.Attacks.*;
import theVacant.cards.Powers.Aquamarine;
import theVacant.cards.Powers.StandoPowah;
import theVacant.cards.Skills.*;
import theVacant.cards.archive.Attacks.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.badlogic.gdx.graphics.Color;
import theVacant.cards.archive.Powers.*;
import theVacant.cards.archive.Skills.*;
import theVacant.relics.BoundSoul;
import theVacant.relics.BrassGoblet;

import static theVacant.VacantMod.*;

public class TheVacant extends CustomPlayer
{
    public static final Logger logger = LogManager.getLogger(VacantMod.class.getName());

    public static class Enums
    {
        @SpireEnum
        public static AbstractPlayer.PlayerClass THE_VACANT;
        @SpireEnum(name = "VACANT_COLOR")
        public static AbstractCard.CardColor COLOR_GOLD;
        @SpireEnum(name = "VACANT_COLOR")
        public static CardLibrary.LibraryType LIBRARY_COLOR;
    }

    public static final int ENERGY_PER_TURN = 3;
    public static final int STARTING_HP = 80;
    public static final int MAX_HP = 80;
    public static final int STARTING_GOLD = 99;
    public static final int CARD_DRAW = 5;
    public static final int ORB_SLOTS = 0;


    public int fractureThreshold;
    public int millsThisTurn;
    public int releasesThisCombat;

    private static final String ID = makeID("VacantCharacter");
    private static final CharacterStrings characterStrings = CardCrawlGame.languagePack.getCharacterString(ID);
    private static final KeywordStrings keyStrings = CardCrawlGame.languagePack.getKeywordString(ID);
    private static final String[] NAMES = characterStrings.NAMES;
    private static final String[] TEXT = characterStrings.TEXT;
    public static List<String> crystalCards = Arrays.asList(ShatterAmethyst.ID, FireCrystal.ID, FireCrystal.ID);

    public static final String[] orbTextures = {
            "theVacantResources/images/char/vacantCharacter/orb/layer1.png",
            "theVacantResources/images/char/vacantCharacter/orb/layer2.png",
            "theVacantResources/images/char/vacantCharacter/orb/layer3.png",
            "theVacantResources/images/char/vacantCharacter/orb/layer4.png",
            "theVacantResources/images/char/vacantCharacter/orb/layer5.png",
            "theVacantResources/images/char/vacantCharacter/orb/layer6.png",
            "theVacantResources/images/char/vacantCharacter/orb/layer1d.png",
            "theVacantResources/images/char/vacantCharacter/orb/layer2d.png",
            "theVacantResources/images/char/vacantCharacter/orb/layer3d.png",
            "theVacantResources/images/char/vacantCharacter/orb/layer4d.png",
            "theVacantResources/images/char/vacantCharacter/orb/layer5d.png",};

    public TheVacant(String name, PlayerClass setClass)
    {
        super(name, setClass, orbTextures,
                "theVacantResources/images/char/vacantCharacter/orb/vfx.png", null,
                new SpineAnimation(
                        "theVacantResources/images/char/vacantCharacter/skeleton.atlas",
                        "theVacantResources/images/char/vacantCharacter/skeleton.json",
                        40F
                )
        );

        initializeClass(null,

                THE_VACANT_SHOULDER_1,
                THE_VACANT_SHOULDER_2,
                THE_VACANT_CORPSE,
                getLoadout(), 20.0F, -10.0F, 220.0F, 290.0F, new EnergyManager(ENERGY_PER_TURN));


        loadAnimation(
                THE_VACANT_SKELETON_ATLAS,
                THE_VACANT_SKELETON_JSON,
                1.0f);
        AnimationState.TrackEntry e = this.state.setAnimation(0, "idle", true);
        e.setTime(e.getEndTime() * MathUtils.random());


        dialogX = (drawX + 0.0F * Settings.scale);
        dialogY = (drawY + 220.0F * Settings.scale);
        fractureThreshold = 4;
        millsThisTurn = 0;
        releasesThisCombat = 0;
    }

    @Override
    public CharSelectInfo getLoadout()
    {
        return new CharSelectInfo(NAMES[0], TEXT[0],
                STARTING_HP, MAX_HP, ORB_SLOTS, STARTING_GOLD, CARD_DRAW, this, getStartingRelics(),
                getStartingDeck(), false);
    }
/*
    @Override
    public void renderPowerTips(SpriteBatch sb)
    {
        ArrayList<PowerTip> tips = new ArrayList();
        if(this.will > 0)
        {
            tips.add(new PowerTip(BaseMod.getKeywordTitle("thevacant:will"), BaseMod.getKeywordDescription("thevacant:will") + (this.will)));
        }
        if (!tips.isEmpty())
        {
            if (this.hb.cX + this.hb.width / 2.0F < TIP_X_THRESHOLD)
                TipHelper.queuePowerTips(this.hb.cX + this.hb.width / 2.0F + TIP_OFFSET_R_X, this.hb.cY + TipHelper.calculateAdditionalOffset(tips, this.hb.cY), tips);
            else
                TipHelper.queuePowerTips(this.hb.cX - this.hb.width / 2.0F + TIP_OFFSET_L_X, this.hb.cY + TipHelper.calculateAdditionalOffset(tips, this.hb.cY), tips);
        }
        super.renderPowerTips(sb);
    }
*/
    @Override
    public ArrayList<String> getStartingDeck()
    {
        ArrayList<String> startDeck = new ArrayList<>();

        logger.info("Begin loading starter Deck Strings");
        startDeck.add(VacantStarterStrike.ID);
        startDeck.add(VacantStarterStrike.ID);
        startDeck.add(VacantStarterStrike.ID);
        startDeck.add(VacantStarterStrike.ID);
        startDeck.add(VacantStarterDefend.ID);
        startDeck.add(VacantStarterDefend.ID);
        startDeck.add(VacantStarterDefend.ID);
        startDeck.add(VacantStarterDefend.ID);

        startDeck.add(SoulBash.ID);
        startDeck.add(ShatterAmethyst.ID);

        //Testing
//        startDeck.add(Gloom.ID);
//        startDeck.add(VoidEmbrace.ID);
//        startDeck.add(HolyWater.ID);
//        startDeck.add(BurdenBreak.ID);
        //startDeck.add(ReaperBlast.ID);
        //startDeck.add(Snowball.ID);
        //startDeck.add(OpalFracture.ID);
        //startDeck.add(ReaperStrike.ID);
        //startDeck.add(Banish.ID);
        //startDeck.add(DimensionTear.ID);
        return startDeck;
    }

    public ArrayList<String> getStartingRelics()
    {
        ArrayList<String> startRel = new ArrayList<>();

        startRel.add(BrassGoblet.ID);

        UnlockTracker.markRelicAsSeen(BrassGoblet.ID);
        return startRel;
    }

    @Override
    public void doCharSelectScreenSelectEffect()
    {
        CardCrawlGame.sound.playA("theVacant:skeleton", 1f);
        CardCrawlGame.screenShake.shake(ScreenShake.ShakeIntensity.LOW, ScreenShake.ShakeDur.SHORT,
                false);
    }

    @Override
    public void applyStartOfCombatLogic()
    {
        millsThisTurn = 0;
        releasesThisCombat = 0;
        super.applyStartOfCombatLogic();
    }

    @Override
    public void applyStartOfTurnRelics()
    {
        super.applyStartOfTurnRelics();
        millsThisTurn = 0;
    }

    @Override
    public String getCustomModeCharacterButtonSoundKey() {
        return "ATTACK_DAGGER_1";
    }

    @Override
    public int getAscensionMaxHPLoss() {
        return 0;
    }

    @Override
    public AbstractCard.CardColor getCardColor() {
        return Enums.COLOR_GOLD;
    }

    @Override
    public Color getCardTrailColor() {
        return VACANT_COLOR;
    }

    @Override
    public BitmapFont getEnergyNumFont() {
        return FontHelper.energyNumFontRed;
    }

    @Override
    public String getLocalizedCharacterName() {
        return NAMES[0];
    }

    @Override
    public AbstractCard getStartCardForEvent() {
        return new ShatterAmethyst();
    }

    @Override
    public String getTitle(AbstractPlayer.PlayerClass playerClass) {
        return NAMES[1];
    }

    @Override
    public AbstractPlayer newInstance() {
        return new TheVacant(name, chosenClass);
    }

    @Override
    public Color getCardRenderColor() {
        return VACANT_COLOR;
    }

    @Override
    public Color getSlashAttackColor() {
        return VACANT_COLOR;
    }

    @Override
    public AbstractGameAction.AttackEffect[] getSpireHeartSlashEffect()
    {
        return new AbstractGameAction.AttackEffect[]{
                AbstractGameAction.AttackEffect.BLUNT_HEAVY,
                AbstractGameAction.AttackEffect.BLUNT_HEAVY,
                AbstractGameAction.AttackEffect.BLUNT_HEAVY};
    }

    @Override
    public String getSpireHeartText() {
        return TEXT[1];
    }

    @Override
    public String getVampireText() {
        return TEXT[2];
    }
}
