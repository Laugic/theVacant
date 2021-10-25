package theVacant;

import basemod.BaseMod;
import basemod.ModLabeledToggleButton;
import basemod.ModPanel;
import basemod.interfaces.*;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.evacipated.cardcrawl.mod.stslib.Keyword;
import com.evacipated.cardcrawl.modthespire.lib.SpireConfig;
import com.evacipated.cardcrawl.modthespire.lib.SpireInitializer;

import java.nio.charset.StandardCharsets;
import java.util.*;

import com.google.gson.Gson;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.CardHelper;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.localization.*;
import com.megacrit.cardcrawl.powers.FadingPower;
import com.megacrit.cardcrawl.unlock.UnlockTracker;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import theVacant.cards.Attacks.*;
import theVacant.cards.Powers.Aquamarine;
import theVacant.cards.Powers.StandoPowah;
import theVacant.cards.Skills.*;
import theVacant.cards.archive.Attacks.*;
import theVacant.cards.archive.Powers.*;
import theVacant.cards.archive.Skills.*;
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
        PostInitializeSubscriber,
        AddAudioSubscriber
{
    public static final Logger logger = LogManager.getLogger(VacantMod.class.getName());
    private static String modID = "theVacant";

    public static Properties theVacantDefaultSettings = new Properties();
    public static final String ENABLE_PLACEHOLDER_SETTINGS = "enablePlaceholder";
    public static boolean enablePlaceholder = true;

    private static final String MODNAME = "The Vacant";
    private static final String AUTHOR = "Laugic";
    private static final String DESCRIPTION = "A lost soul, bound to an artifact, is doomed to climb the Spire.";


    public static final Color VACANT_COLOR = CardHelper.getColor(175, 125, 60);

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
    private static final String THE_VACANT_PORTRAIT = "theVacantResources/images/charSelect/VacantPortraitBG.png";
    public static final String THE_VACANT_SHOULDER_1 = "theVacantResources/images/char/vacantCharacter/shoulder.png";
    public static final String THE_VACANT_SHOULDER_2 = "theVacantResources/images/char/vacantCharacter/shoulder2.png";
    public static final String THE_VACANT_CORPSE = "theVacantResources/images/char/vacantCharacter/corpse.png";

    public static final String BADGE_IMAGE = "theVacantResources/images/Badge.png";

    public static final String THE_VACANT_SKELETON_ATLAS = "theVacantResources/images/char/vacantCharacter/skeleton.atlas";
    public static final String THE_VACANT_SKELETON_JSON = "theVacantResources/images/char/vacantCharacter/skeleton.json";



    //Keywords
    public static Map<String, Keyword> keywords = new HashMap<>();
    public static final String VOIDBOUND = "thevacant:Voidbound";
    public static List<String> IMMUNE_POWERS = new ArrayList<String>();



    public static void initialize()
    {
        logger.info("========================= Vacanting the Mod =========================");
        VacantMod vacantMod = new VacantMod();
        IMMUNE_POWERS.add(FadingPower.POWER_ID);
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
        BaseMod.addRelicToCustomPool(new BrassGoblet(), TheVacant.Enums.COLOR_GOLD);
        BaseMod.addRelicToCustomPool(new Deathbell(), TheVacant.Enums.COLOR_GOLD);
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
        //BaseMod.addCard(new VacantStarterStrike());
        //BaseMod.addCard(new Barrage());
        //BaseMod.addCard(new Crunch());

        //Skills
        //BaseMod.addCard(new VacantStarterDefend());
        //BaseMod.addCard(new Dig());
        //BaseMod.addCard(new Spinshield());
        BaseMod.addCard(new ShatterAmethyst());
        BaseMod.addCard(new TopazFrenzy());
        BaseMod.addCard(new DiamondIsUnbreakable());
        BaseMod.addCard(new EssenceOfBismuth());
        BaseMod.addCard(new Banish());
        BaseMod.addCard(new StealSoul());
        BaseMod.addCard(new EmeraldSplash());
        BaseMod.addCard(new BackInTheMine());
        BaseMod.addCard(new SapphireStrike());
        BaseMod.addCard(new SoulBash());
        BaseMod.addCard(new OpalFracture());
        BaseMod.addCard(new Release());
        //Attacks

        BaseMod.addCard(new VacantStarterStrike());
        BaseMod.addCard(new CursedBlast());
        BaseMod.addCard(new EyePoke());
        BaseMod.addCard(new Fling());
        BaseMod.addCard(new GraveDigger());
        BaseMod.addCard(new GraveWave());
        BaseMod.addCard(new Jab());
        BaseMod.addCard(new ShriekingBlast());
        BaseMod.addCard(new OnyxBlaster());
        BaseMod.addCard(new BrassMagnet());
        BaseMod.addCard(new FranticBlow());
        BaseMod.addCard(new ReaperStrike());
        BaseMod.addCard(new Snowball());
        BaseMod.addCard(new SurpriseAttack());
        BaseMod.addCard(new Thoughtseize());
        BaseMod.addCard(new Threat());
        BaseMod.addCard(new DarkStrike());
        BaseMod.addCard(new DimensionTear());
        BaseMod.addCard(new FiendFrost());
        BaseMod.addCard(new LashOut());
        BaseMod.addCard(new Showdown());

        //Skills
        BaseMod.addCard(new VacantStarterDefend());
        BaseMod.addCard(new Cower());
        BaseMod.addCard(new Dig());
        BaseMod.addCard(new Spinshield());
        BaseMod.addCard(new Soothe());
        BaseMod.addCard(new Corporeate());
        BaseMod.addCard(new BattleScars());
        BaseMod.addCard(new EmbraceDarkness());
        BaseMod.addCard(new RubyRage());
        BaseMod.addCard(new Expand());
        BaseMod.addCard(new Fortify());
        BaseMod.addCard(new Hex());
        BaseMod.addCard(new HolyWater());
        BaseMod.addCard(new Barrage());
        BaseMod.addCard(new OnSecondThought());
        BaseMod.addCard(new Sneeze());
        BaseMod.addCard(new OpalShine());
        BaseMod.addCard(new Unearth());
        BaseMod.addCard(new EmptyShield());
        BaseMod.addCard(new Exorcise());
        BaseMod.addCard(new FromNothing());
        BaseMod.addCard(new TheAnvil());
        BaseMod.addCard(new TimeSkip());
        BaseMod.addCard(new Partake());
        BaseMod.addCard(new StoreSoul());
        BaseMod.addCard(new AwMan());
        BaseMod.addCard(new ReaperBlast());

        //Powers
        BaseMod.addCard(new Acceptance());
        BaseMod.addCard(new BurdenBreak());
        BaseMod.addCard(new CleanseSoul());
        BaseMod.addCard(new Gloom());
        BaseMod.addCard(new GotOurPickaxe());
        BaseMod.addCard(new Immaterialize());
        BaseMod.addCard(new IntoTheAbyss());
        BaseMod.addCard(new StandoPowah());
        BaseMod.addCard(new Recover());
        BaseMod.addCard(new VoidEmbrace());
        BaseMod.addCard(new ForgeSoul());
        BaseMod.addCard(new Rejection());
        BaseMod.addCard(new VoidForm());
        BaseMod.addCard(new RunicThoughts());
        BaseMod.addCard(new Aquamarine());

        //Temp Cards
        /*
        BaseMod.addCard(new Snap());
        BaseMod.addCard(new Crackle());
        BaseMod.addCard(new Pop());*/

        logger.info("Making sure the cards are unlocked.");

        UnlockTracker.unlockCard(ShatterAmethyst.ID);
        UnlockTracker.unlockCard(TopazFrenzy.ID);
        UnlockTracker.unlockCard(DiamondIsUnbreakable.ID);
        UnlockTracker.unlockCard(EssenceOfBismuth.ID);
        UnlockTracker.unlockCard(Banish.ID);
        UnlockTracker.unlockCard(StealSoul.ID);
        UnlockTracker.unlockCard(EmeraldSplash.ID);
        UnlockTracker.unlockCard(BackInTheMine.ID);
        UnlockTracker.unlockCard(SapphireStrike.ID);
        UnlockTracker.unlockCard(SoulBash.ID);
        UnlockTracker.unlockCard(OpalFracture.ID);
        UnlockTracker.unlockCard(Release.ID);

        //Attacks
        UnlockTracker.unlockCard(VacantStarterStrike.ID);
        UnlockTracker.unlockCard(CursedBlast.ID);
        UnlockTracker.unlockCard(EyePoke.ID);
        UnlockTracker.unlockCard(Fling.ID);
        UnlockTracker.unlockCard(GraveDigger.ID);
        UnlockTracker.unlockCard(GraveWave.ID);
        UnlockTracker.unlockCard(Jab.ID);
        UnlockTracker.unlockCard(ShriekingBlast.ID);
        UnlockTracker.unlockCard(OnyxBlaster.ID);
        UnlockTracker.unlockCard(BrassMagnet.ID);
        UnlockTracker.unlockCard(FranticBlow.ID);
        UnlockTracker.unlockCard(ReaperStrike.ID);
        UnlockTracker.unlockCard(Snowball.ID);
        UnlockTracker.unlockCard(SurpriseAttack.ID);
        UnlockTracker.unlockCard(Thoughtseize.ID);
        UnlockTracker.unlockCard(Threat.ID);
        UnlockTracker.unlockCard(DarkStrike.ID);
        UnlockTracker.unlockCard(DimensionTear.ID);
        UnlockTracker.unlockCard(FiendFrost.ID);
        UnlockTracker.unlockCard(LashOut.ID);
        UnlockTracker.unlockCard(Showdown.ID);
        UnlockTracker.unlockCard(SoulBash.ID);

        //Skills
        UnlockTracker.unlockCard(VacantStarterDefend.ID);
        UnlockTracker.unlockCard(Cower.ID);
        UnlockTracker.unlockCard(Corporeate.ID);
        UnlockTracker.unlockCard(Dig.ID);
        UnlockTracker.unlockCard(Spinshield.ID);
        UnlockTracker.unlockCard(Soothe.ID);
        UnlockTracker.unlockCard(BattleScars.ID);
        UnlockTracker.unlockCard(EmbraceDarkness.ID);
        UnlockTracker.unlockCard(RubyRage.ID);
        UnlockTracker.unlockCard(Expand.ID);
        UnlockTracker.unlockCard(Fortify.ID);
        UnlockTracker.unlockCard(Hex.ID);
        UnlockTracker.unlockCard(HolyWater.ID);
        UnlockTracker.unlockCard(Barrage.ID);
        UnlockTracker.unlockCard(OnSecondThought.ID);
        UnlockTracker.unlockCard(Sneeze.ID);
        UnlockTracker.unlockCard(OpalShine.ID);
        UnlockTracker.unlockCard(Unearth.ID);
        UnlockTracker.unlockCard(EmptyShield.ID);
        UnlockTracker.unlockCard(Exorcise.ID);
        UnlockTracker.unlockCard(FromNothing.ID);
        UnlockTracker.unlockCard(TheAnvil.ID);
        UnlockTracker.unlockCard(TimeSkip.ID);
        UnlockTracker.unlockCard(Partake.ID);
        UnlockTracker.unlockCard(StoreSoul.ID);
        UnlockTracker.unlockCard(AwMan.ID);
        UnlockTracker.unlockCard(ReaperBlast.ID);

        //Powers
        UnlockTracker.unlockCard(Acceptance.ID);
        UnlockTracker.unlockCard(BurdenBreak.ID);
        UnlockTracker.unlockCard(CleanseSoul.ID);
        UnlockTracker.unlockCard(Gloom.ID);
        UnlockTracker.unlockCard(GotOurPickaxe.ID);
        UnlockTracker.unlockCard(StandoPowah.ID);
        UnlockTracker.unlockCard(Immaterialize.ID);
        UnlockTracker.unlockCard(IntoTheAbyss.ID);
        UnlockTracker.unlockCard(Recover.ID);
        UnlockTracker.unlockCard(VoidEmbrace.ID);
        UnlockTracker.unlockCard(ForgeSoul.ID);
        UnlockTracker.unlockCard(Rejection.ID);
        UnlockTracker.unlockCard(VoidForm.ID);
        UnlockTracker.unlockCard(RunicThoughts.ID);
        UnlockTracker.unlockCard(Aquamarine.ID);
        //Powers

        logger.info("Making sure the cards are unlocked.");
/*
        //Attacks
        UnlockTracker.unlockCard(VacantStarterStrike.ID);
        UnlockTracker.unlockCard(Crunch.ID);
        UnlockTracker.unlockCard(Barrage.ID);

        //NEW
        UnlockTracker.unlockCard(FireCrystal.ID);

        //Skills
        UnlockTracker.unlockCard(VacantStarterDefend.ID);
        UnlockTracker.unlockCard(Dig.ID);
        UnlockTracker.unlockCard(Spinshield.ID);
*/
        //Powers

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
        BaseMod.loadCustomStringsFile(OrbStrings.class,
                getModID() + "Resources/localization/eng/VacantMod-Orb-Strings.json");
/*

        BaseMod.loadCustomStringsFile(EventStrings.class,
                getModID() + "Resources/localization/eng/VacantMod-Event-Strings.json");

        BaseMod.loadCustomStringsFile(PotionStrings.class,
                getModID() + "Resources/localization/eng/VacantMod-Potion-Strings.json");

*/
        logger.info("Done editing strings");
    }

    @Override
    public void receiveEditKeywords()
    {
        Gson gson = new Gson();
        String json = Gdx.files.internal(getModID() + "Resources/localization/eng/VacantMod-Keyword-Strings.json").readString(String.valueOf(StandardCharsets.UTF_8));
        com.evacipated.cardcrawl.mod.stslib.Keyword[] keywords = gson.fromJson(json, com.evacipated.cardcrawl.mod.stslib.Keyword[].class);

        if (keywords != null)
        {
            for (Keyword keyword : keywords)
            {
                BaseMod.addKeyword(getModID().toLowerCase(), keyword.PROPER_NAME, keyword.NAMES, keyword.DESCRIPTION);
                logger.info("Keyword added: " + keyword.PROPER_NAME);
            }
        }
    }

    @Override
    public void receiveAddAudio()
    {
        BaseMod.addAudio("theVacant:ora",  getModID() + "Resources/audio/ora.ogg");
        BaseMod.addAudio("theVacant:kingCrimson",  getModID() + "Resources/audio/kingCrimson.ogg");
        BaseMod.addAudio("theVacant:skeleton",  getModID() + "Resources/audio/mc_skeleton.ogg");
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

    public static String makeOrbPath(String resourcePath)
    {
    return getModID() + "Resources/images/orbs/" + resourcePath;
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
