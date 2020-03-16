package theVacant;

import basemod.BaseMod;
import basemod.ModLabeledToggleButton;
import basemod.ModPanel;
import basemod.helpers.RelicType;
import basemod.interfaces.*;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.evacipated.cardcrawl.mod.stslib.Keyword;
import com.evacipated.cardcrawl.modthespire.lib.SpireConfig;
import com.evacipated.cardcrawl.modthespire.lib.SpireInitializer;

import java.nio.charset.StandardCharsets;
import java.util.Properties;

import com.google.gson.Gson;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.CardHelper;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.localization.*;
import com.megacrit.cardcrawl.unlock.UnlockTracker;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import theVacant.cards.Attacks.*;
import theVacant.cards.Powers.*;
import theVacant.cards.Skills.*;
import theVacant.characters.TheVacant;

import com.badlogic.gdx.graphics.Color;
import theVacant.relics.*;
import theVacant.util.TextureLoader;
import theVacant.variables.*;


@SpireInitializer
public class VacantMod implements
        EditCardsSubscriber,
        EditRelicsSubscriber,
        EditStringsSubscriber,
        EditKeywordsSubscriber,
        EditCharactersSubscriber,
        PostInitializeSubscriber
{
    public static final Logger logger = LogManager.getLogger(VacantMod.class.getName());
    private static String modID = "theVacant";

    public static Properties theVacantDefaultSettings = new Properties();
    public static final String ENABLE_PLACEHOLDER_SETTINGS = "enablePlaceholder";
    public static boolean enablePlaceholder = true;

    private static final String MODNAME = "The Vacant";
    private static final String AUTHOR = "Laugic";
    private static final String DESCRIPTION = "A broken soul is doomed to climb the Spire.";


    public static final Color VACANT_COLOR = CardHelper.getColor(64.0f, 70.0f, 70.0f);

    public static final Color PLACEHOLDER_POTION_LIQUID = CardHelper.getColor(209.0f, 53.0f, 18.0f);
    public static final Color PLACEHOLDER_POTION_HYBRID = CardHelper.getColor(255.0f, 230.0f, 230.0f);
    public static final Color PLACEHOLDER_POTION_SPOTS = CardHelper.getColor(100.0f, 25.0f, 10.0f);

    private static final String ATTACK_VACANT = "theVacantResources/images/512/bg_attack_default_gray.png";
    private static final String SKILL_VACANT = "theVacantResources/images/512/bg_skill_default_gray.png";
    private static final String POWER_VACANT = "theVacantResources/images/512/bg_power_default_gray.png";

    private static final String ENERGY_ORB_VACANT = "theVacantResources/images/512/card_default_gray_orb.png";
    private static final String CARD_ENERGY_ORB = "theVacantResources/images/512/card_small_orb.png";

    private static final String ATTACK_VACANT_PORTRAIT = "theVacantResources/images/1024/bg_attack_default_gray.png";
    private static final String SKILL_VACANT_PORTRAIT = "theVacantResources/images/1024/bg_skill_default_gray.png";
    private static final String POWER_VACANT_PORTRAIT = "theVacantResources/images/1024/bg_power_default_gray.png";
    private static final String ENERGY_ORB_VACANT_PORTRAIT = "theVacantResources/images/1024/card_default_gray_orb.png";

    private static final String THE_VACANT_BUTTON = "theVacantResources/images/charSelect/TheVacantButton.png";
    private static final String THE_VACANT_PORTRAIT = "theVacantResources/images/charSelect/DefaultCharacterPortraitBG.png";
    public static final String THE_VACANT_SHOULDER_1 = "theVacantResources/images/char/vacantCharacter/shoulder.png";
    public static final String THE_VACANT_SHOULDER_2 = "theVacantResources/images/char/vacantCharacter/shoulder2.png";
    public static final String THE_VACANT_CORPSE = "theVacantResources/images/char/vacantCharacter/corpse.png";

    public static final String BADGE_IMAGE = "theVacantResources/images/Badge.png";

    public static final String THE_VACANT_SKELETON_ATLAS = "theVacantResources/images/char/vacantCharacter/skeleton.atlas";
    public static final String THE_VACANT_SKELETON_JSON = "theVacantResources/images/char/vacantCharacter/skeleton.json";

    public static void initialize()
    {
        logger.info("========================= Vacanting the Mod =========================");
        VacantMod vacantMod = new VacantMod();
        logger.info("========================= /Mod Successfully Vacanted (The Vacant Initialized)/ =========================");
    }

    public VacantMod()
    {
        logger.info("Subscribe to BaseMod hooks");
        BaseMod.subscribe(this);
        logger.info("Done subscribing");

        logger.info("Creating the color " + TheVacant.Enums.COLOR_GOLD.toString());
        BaseMod.addColor(TheVacant.Enums.COLOR_GOLD, VACANT_COLOR, VACANT_COLOR, VACANT_COLOR,
                VACANT_COLOR, VACANT_COLOR, VACANT_COLOR, VACANT_COLOR,
                ATTACK_VACANT, SKILL_VACANT, POWER_VACANT, ENERGY_ORB_VACANT,
                ATTACK_VACANT_PORTRAIT, SKILL_VACANT_PORTRAIT, POWER_VACANT_PORTRAIT,
                ENERGY_ORB_VACANT_PORTRAIT, CARD_ENERGY_ORB);
        logger.info("Done creating the color");

        logger.info("Adding mod settings");
        theVacantDefaultSettings.setProperty(ENABLE_PLACEHOLDER_SETTINGS, "FALSE");
        try {
            SpireConfig config = new SpireConfig("vacantMod", "theVacantConfig", theVacantDefaultSettings);

            config.load();
            enablePlaceholder = config.getBool(ENABLE_PLACEHOLDER_SETTINGS);
        } catch (Exception e) {
            e.printStackTrace();
        }
        logger.info("Done adding mod settings");
    }



    @Override
    public void receiveEditCharacters()
    {
        logger.info("Beginning to edit characters. " + "Add " + TheVacant.Enums.THE_VACANT.toString());

        BaseMod.addCharacter(new TheVacant("the Vacant", TheVacant.Enums.THE_VACANT),
                THE_VACANT_BUTTON, THE_VACANT_PORTRAIT, TheVacant.Enums.THE_VACANT);

        //receiveEditPotions();
        logger.info("Added " + TheVacant.Enums.THE_VACANT.toString());
    }

    @Override
    public void receivePostInitialize() {
        logger.info("Loading badge image and mod options");
        Texture badgeTexture = TextureLoader.getTexture(BADGE_IMAGE);
        ModPanel settingsPanel = new ModPanel();
        ModLabeledToggleButton enableNormalsButton = new ModLabeledToggleButton("The Vacant",
                350.0f, 700.0f, Settings.CREAM_COLOR, FontHelper.charDescFont,
                enablePlaceholder,
                settingsPanel,
                (label) -> {
                },
                (button) -> {
                    enablePlaceholder = button.enabled;
                    try {
                        SpireConfig config = new SpireConfig("vacantMod", "theVacantConfig", theVacantDefaultSettings);
                        config.setBool(ENABLE_PLACEHOLDER_SETTINGS, enablePlaceholder);
                        config.save();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                });
        settingsPanel.addUIElement(enableNormalsButton);

        BaseMod.registerModBadge(badgeTexture, MODNAME, AUTHOR, DESCRIPTION, settingsPanel);
        //BaseMod.addEvent(IdentityCrisisEvent.ID, IdentityCrisisEvent.class, TheCity.ID);
        logger.info("Done loading badge Image and mod options");
    }

    public void receiveEditPotions()
    {
        logger.info("Beginning to edit potions");
        //BaseMod.addPotion(PlaceholderPotion.class, PLACEHOLDER_POTION_LIQUID, PLACEHOLDER_POTION_HYBRID, PLACEHOLDER_POTION_SPOTS, PlaceholderPotion.POTION_ID, TheDefault.Enums.THE_DEFAULT);
        logger.info("Done editing potions");
    }

    @Override
    public void receiveEditRelics()
    {
        logger.info("Adding relics");
        //BaseMod.addRelicToCustomPool(new VacantBossEnergyRelic(), TheVacant.Enums.COLOR_GOLD);
        BaseMod.addRelic(new VacantBossEnergyRelic(), RelicType.SHARED);
        BaseMod.addRelic(new VacantStarterRelic(), RelicType.SHARED);
        BaseMod.addRelic(new Deathbell(), RelicType.SHARED);
        BaseMod.addRelic(new BrassGoblet(), RelicType.SHARED);
        BaseMod.addRelic(new BoundSoul(), RelicType.SHARED);
        logger.info("Done adding relics!");
    }

    @Override
    public void receiveEditCards() {
        logger.info("Adding variables");
        //pathCheck();
        logger.info("Add variables");
        BaseMod.addDynamicVariable(new VacantMissingHealthGeneric());
        BaseMod.addDynamicVariable(new VacantMissingHealthDamage());
        BaseMod.addDynamicVariable(new VacantHealthDamage());
        BaseMod.addDynamicVariable(new VacantMissingHealthBlock());

        BaseMod.addDynamicVariable(new VacantGlassDamage());
        logger.info("Adding cards");

        //Attacks
        BaseMod.addCard(new VacantStarterStrike());
        BaseMod.addCard(new Crunch());
        BaseMod.addCard(new LashOut());
        BaseMod.addCard(new GraveDigger());
        BaseMod.addCard(new Vengeance());
        BaseMod.addCard(new EyePoke());
        BaseMod.addCard(new FranticBlow());
        BaseMod.addCard(new Fling());
        BaseMod.addCard(new Jab());
        BaseMod.addCard(new Frenzy());
        BaseMod.addCard(new Snowball());
        BaseMod.addCard(new SealBreak());
        BaseMod.addCard(new Slash());
        BaseMod.addCard(new GraveWave());
        BaseMod.addCard(new Whip());
        BaseMod.addCard(new SurpriseAttack());
        BaseMod.addCard(new BellToll());
        BaseMod.addCard(new ShriekingBlast());
        BaseMod.addCard(new Thoughtseize());
        BaseMod.addCard(new Threat());
        BaseMod.addCard(new Reconnect());
        BaseMod.addCard(new Throttle());
        BaseMod.addCard(new BrassMagnet());
        BaseMod.addCard(new FiendFrost());
        BaseMod.addCard(new Yeet());
        BaseMod.addCard(new Splurge());
        BaseMod.addCard(new DimensionTear());
        BaseMod.addCard(new BurdenBreak());
        BaseMod.addCard(new FinalCountdown());
        BaseMod.addCard(new Showdown());

        //Skills
        BaseMod.addCard(new VacantStarterDefend());
        BaseMod.addCard(new EmptyShield());
        BaseMod.addCard(new HealingSalve());
        BaseMod.addCard(new Dig());
        BaseMod.addCard(new Cower());
        BaseMod.addCard(new Fortify());
        BaseMod.addCard(new Return());
        BaseMod.addCard(new OnSecondThought());
        BaseMod.addCard(new Sneeze());
        BaseMod.addCard(new Expand());
        BaseMod.addCard(new Release());
        BaseMod.addCard(new Enrage());
        BaseMod.addCard(new Think());
        BaseMod.addCard(new Freedom());
        BaseMod.addCard(new FromNothing());
        BaseMod.addCard(new DoubleTeam());
        BaseMod.addCard(new Ward());
        BaseMod.addCard(new BattleScars());
        BaseMod.addCard(new Hex());
        BaseMod.addCard(new Spinshield());
        BaseMod.addCard(new Prevent());
        BaseMod.addCard(new ShutAway());
        BaseMod.addCard(new Accumulate());
        BaseMod.addCard(new Revenge());
        BaseMod.addCard(new Exorcise());
        BaseMod.addCard(new Reseal());
        BaseMod.addCard(new LastDitchEffort());
        BaseMod.addCard(new BreakFree());
        BaseMod.addCard(new Willful());
        BaseMod.addCard(new TheAnvil());

        //Powers
        BaseMod.addCard(new Gloom());
        BaseMod.addCard(new Immaterialize());
        BaseMod.addCard(new CleanseSoul());
        BaseMod.addCard(new Willpower());
        BaseMod.addCard(new Anguish());
        BaseMod.addCard(new Recover());
        BaseMod.addCard(new IronWill());
        BaseMod.addCard(new Compression());
        BaseMod.addCard(new EmptyForm());
        BaseMod.addCard(new Acceptance());
        BaseMod.addCard(new Rejection());
        BaseMod.addCard(new ShatterSoul());
        BaseMod.addCard(new VoidEmbrace());
        BaseMod.addCard(new GreaterMind());
        BaseMod.addCard(new Empowered());

        logger.info("Making sure the cards are unlocked.");

        //Attacks
        UnlockTracker.unlockCard(VacantStarterStrike.ID);
        UnlockTracker.unlockCard(Crunch.ID);
        UnlockTracker.unlockCard(LashOut.ID);
        UnlockTracker.unlockCard(GraveDigger.ID);
        UnlockTracker.unlockCard(Vengeance.ID);
        UnlockTracker.unlockCard(EyePoke.ID);
        UnlockTracker.unlockCard(FranticBlow.ID);
        UnlockTracker.unlockCard(Fling.ID);
        UnlockTracker.unlockCard(Jab.ID);
        UnlockTracker.unlockCard(Frenzy.ID);
        UnlockTracker.unlockCard(Snowball.ID);
        UnlockTracker.unlockCard(SealBreak.ID);
        UnlockTracker.unlockCard(Slash.ID);
        UnlockTracker.unlockCard(GraveWave.ID);
        UnlockTracker.unlockCard(Whip.ID);
        UnlockTracker.unlockCard(SurpriseAttack.ID);
        UnlockTracker.unlockCard(BellToll.ID);
        UnlockTracker.unlockCard(ShriekingBlast.ID);
        UnlockTracker.unlockCard(Thoughtseize.ID);
        UnlockTracker.unlockCard(Threat.ID);
        UnlockTracker.unlockCard(Reconnect.ID);
        UnlockTracker.unlockCard(Throttle.ID);
        UnlockTracker.unlockCard(BrassMagnet.ID);
        UnlockTracker.unlockCard(FiendFrost.ID);
        UnlockTracker.unlockCard(Yeet.ID);
        UnlockTracker.unlockCard(Splurge.ID);
        UnlockTracker.unlockCard(DimensionTear.ID);
        UnlockTracker.unlockCard(BurdenBreak.ID);
        UnlockTracker.unlockCard(FinalCountdown.ID);
        UnlockTracker.unlockCard(Showdown.ID);

        //Skills
        UnlockTracker.unlockCard(VacantStarterDefend.ID);
        UnlockTracker.unlockCard(EmptyShield.ID);
        UnlockTracker.unlockCard(HealingSalve.ID);
        UnlockTracker.unlockCard(Dig.ID);
        UnlockTracker.unlockCard(Cower.ID);
        UnlockTracker.unlockCard(Fortify.ID);
        UnlockTracker.unlockCard(Return.ID);
        UnlockTracker.unlockCard(OnSecondThought.ID);
        UnlockTracker.unlockCard(Sneeze.ID);
        UnlockTracker.unlockCard(Expand.ID);
        UnlockTracker.unlockCard(Release.ID);
        UnlockTracker.unlockCard(Enrage.ID);
        UnlockTracker.unlockCard(Think.ID);
        UnlockTracker.unlockCard(Freedom.ID);
        UnlockTracker.unlockCard(FromNothing.ID);
        UnlockTracker.unlockCard(DoubleTeam.ID);
        UnlockTracker.unlockCard(Ward.ID);
        UnlockTracker.unlockCard(BattleScars.ID);
        UnlockTracker.unlockCard(Hex.ID);
        UnlockTracker.unlockCard(Spinshield.ID);
        UnlockTracker.unlockCard(Prevent.ID);
        UnlockTracker.unlockCard(ShutAway.ID);
        UnlockTracker.unlockCard(Accumulate.ID);
        UnlockTracker.unlockCard(Revenge.ID);
        UnlockTracker.unlockCard(Exorcise.ID);
        UnlockTracker.unlockCard(Reseal.ID);
        UnlockTracker.unlockCard(LastDitchEffort.ID);
        UnlockTracker.unlockCard(BreakFree.ID);
        UnlockTracker.unlockCard(Willful.ID);
        UnlockTracker.unlockCard(TheAnvil.ID);

        //Powers
        UnlockTracker.unlockCard(Gloom.ID);
        UnlockTracker.unlockCard(Immaterialize.ID);
        UnlockTracker.unlockCard(CleanseSoul.ID);
        UnlockTracker.unlockCard(Willpower.ID);
        UnlockTracker.unlockCard(Anguish.ID);
        UnlockTracker.unlockCard(Recover.ID);
        UnlockTracker.unlockCard(IronWill.ID);
        UnlockTracker.unlockCard(Compression.ID);
        UnlockTracker.unlockCard(EmptyForm.ID);
        UnlockTracker.unlockCard(Acceptance.ID);
        UnlockTracker.unlockCard(Rejection.ID);
        UnlockTracker.unlockCard(ShatterSoul.ID);
        UnlockTracker.unlockCard(VoidEmbrace.ID);
        UnlockTracker.unlockCard(GreaterMind.ID);
        UnlockTracker.unlockCard(Empowered.ID);

        logger.info("Done adding cards!");
    }

    @Override
    public void receiveEditStrings() {
        logger.info("Beginning to edit strings for mod with ID: " + getModID());

        BaseMod.loadCustomStringsFile(CardStrings.class,
                getModID() + "Resources/localization/eng/VacantMod-Card-Strings.json");
        BaseMod.loadCustomStringsFile(RelicStrings.class,
                getModID() + "Resources/localization/eng/VacantMod-Relic-Strings.json");
        BaseMod.loadCustomStringsFile(CharacterStrings.class,
                getModID() + "Resources/localization/eng/VacantMod-Character-Strings.json");
        BaseMod.loadCustomStringsFile(PowerStrings.class,
                getModID() + "Resources/localization/eng/VacantMod-Power-Strings.json");
/*

        BaseMod.loadCustomStringsFile(EventStrings.class,
                getModID() + "Resources/localization/eng/VacantMod-Event-Strings.json");

        BaseMod.loadCustomStringsFile(PotionStrings.class,
                getModID() + "Resources/localization/eng/VacantMod-Potion-Strings.json");

        BaseMod.loadCustomStringsFile(OrbStrings.class,
                getModID() + "Resources/localization/eng/VacantMod-Orb-Strings.json");
*/
        logger.info("Done editing strings");
    }

    @Override
    public void receiveEditKeywords()
    {
        Gson gson = new Gson();
        String json = Gdx.files.internal(getModID() + "Resources/localization/eng/VacantMod-Keyword-Strings.json").readString(String.valueOf(StandardCharsets.UTF_8));
        com.evacipated.cardcrawl.mod.stslib.Keyword[] keywords = gson.fromJson(json, com.evacipated.cardcrawl.mod.stslib.Keyword[].class);

        if (keywords != null) {
            for (Keyword keyword : keywords) {
                BaseMod.addKeyword(getModID().toLowerCase(), keyword.PROPER_NAME, keyword.NAMES, keyword.DESCRIPTION);
            }
        }
    }

    public static String getModID()
    {
        return modID;
    }
    public static String makeCardPath(String resourcePath)
    {
        return getModID() + "Resources/images/cards/" + resourcePath;
    }
    public static String makeRelicPath(String resourcePath)
    {
        return getModID() + "Resources/images/relics/" + resourcePath;
    }
    public static String makeRelicOutlinePath(String resourcePath)
    {
        return getModID() + "Resources/images/relics/outline/" + resourcePath;
    }
    public static String makeID(String idText)
    {
        return getModID() + ":" + idText;
    }
}
