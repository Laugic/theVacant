package theVacant;

import basemod.BaseMod;
import basemod.ModLabeledToggleButton;
import basemod.ModPanel;
import basemod.eventUtil.AddEventParams;
import basemod.eventUtil.EventUtils;
import basemod.interfaces.*;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.GdxRuntimeException;
import com.evacipated.cardcrawl.mod.stslib.Keyword;
import com.evacipated.cardcrawl.modthespire.lib.SpireConfig;
import com.evacipated.cardcrawl.modthespire.lib.SpireInitializer;

import java.nio.charset.StandardCharsets;
import java.util.*;

import com.google.gson.Gson;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.CardHelper;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.localization.*;
import com.megacrit.cardcrawl.powers.*;
import com.megacrit.cardcrawl.unlock.UnlockTracker;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import theVacant.cards.Attacks.*;
import theVacant.cards.Powers.*;
import theVacant.cards.Skills.*;
import theVacant.cards.Attacks.GildedPickaxe;
import theVacant.characters.TheVacant;

import com.badlogic.gdx.graphics.Color;
import theVacant.events.CavernEvent;
import theVacant.events.GobletEvent;
import theVacant.potions.InvisibilityPotion;
import theVacant.potions.MiningPotion;
import theVacant.potions.ScramblePotion;
import theVacant.potions.SwipePotion;
import theVacant.powers.DizzyPower;
import theVacant.relics.*;
import theVacant.util.TextureLoader;
import theVacant.variables.*;
import theVacant.vfx.ParticleManager;


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
    public static String modID = "theVacant";

    public static Properties theVacantDefaultSettings = new Properties();
    public static final String ENABLE_PLACEHOLDER_SETTINGS = "enablePlaceholder";
    public static boolean enablePlaceholder = true;

    private static final String MODNAME = "The Vacant";
    private static final String AUTHOR = "Laugic";
    private static final String DESCRIPTION = "A lost soul, bound to an artifact, is doomed to climb the Spire.";


    public static final Color VACANT_COLOR = CardHelper.getColor(229, 209, 105);

    public static final Color SWIPE_POTION_LIQUID = CardHelper.getColor(50, 50, 50);
    public static final Color SWIPE_POTION_HYBRID = CardHelper.getColor(25, 25, 25);
    public static final Color SWIPE_POTION_SPOTS = CardHelper.getColor(100, 50, 100);

    public static final Color MINE_POTION_LIQUID = CardHelper.getColor(50, 50, 150);
    public static final Color MINE_POTION_HYBRID = CardHelper.getColor(100, 100, 200);
    public static final Color MINE_POTION_SPOTS = CardHelper.getColor(0, 250, 250);

    public static final Color SCRAMBLE_POTION_LIQUID = CardHelper.getColor(150, 150, 150);
    public static final Color SCRAMBLE_POTION_HYBRID = CardHelper.getColor(150, 100, 50);
    public static final Color SCRAMBLE_POTION_SPOTS = CardHelper.getColor(250, 250, 200);

    public static final Color INVIS_POTION_LIQUID = CardHelper.getColor(50, 200, 200);
    public static final Color INVIS_POTION_HYBRID = CardHelper.getColor(20, 150, 150);
    public static final Color INVIS_POTION_SPOTS = new Color(0, 0, 0, 0);

//    private static final String ATTACK_VACANT = "theVacantResources/images/512/bg_attack.png";
//    private static final String SKILL_VACANT = "theVacantResources/images/512/bg_skill.png";
//    private static final String POWER_VACANT = "theVacantResources/images/512/bg_power.png";

//    private static final String ATTACK_VACANT = "theVacantResources/images/512/brassCard.png";
//    private static final String SKILL_VACANT = "theVacantResources/images/512/brassCard.png";
//    private static final String POWER_VACANT = "theVacantResources/images/512/brassCard.png";

    private static final String ATTACK_VACANT = "theVacantResources/images/512/gobletCard.png";
    private static final String SKILL_VACANT = "theVacantResources/images/512/gobletCard.png";
    private static final String POWER_VACANT = "theVacantResources/images/512/gobletCard.png";

    private static final String ENERGY_ORB_VACANT = "theVacantResources/images/512/card_default_gray_orb.png";
    private static final String CARD_ENERGY_ORB = "theVacantResources/images/512/card_small_orb.png";

//    private static final String ATTACK_VACANT_PORTRAIT = "theVacantResources/images/1024/bg_attack.png";
//    private static final String SKILL_VACANT_PORTRAIT = "theVacantResources/images/1024/bg_skill.png";
//    private static final String POWER_VACANT_PORTRAIT = "theVacantResources/images/1024/bg_power.png";

//    private static final String ATTACK_VACANT_PORTRAIT = "theVacantResources/images/1024/brassCard.png";
//    private static final String SKILL_VACANT_PORTRAIT = "theVacantResources/images/1024/brassCard.png";
//    private static final String POWER_VACANT_PORTRAIT = "theVacantResources/images/1024/brassCard.png";

    private static final String ATTACK_VACANT_PORTRAIT = "theVacantResources/images/1024/gobletCard.png";
    private static final String SKILL_VACANT_PORTRAIT = "theVacantResources/images/1024/gobletCard.png";
    private static final String POWER_VACANT_PORTRAIT = "theVacantResources/images/1024/gobletCard.png";
    private static final String ENERGY_ORB_VACANT_PORTRAIT = "theVacantResources/images/1024/card_default_gray_orb.png";

    private static final String THE_VACANT_BUTTON = "theVacantResources/images/charSelect/TheVacantButton.png";
    private static final String THE_VACANT_PORTRAIT = "theVacantResources/images/charSelect/VacantPortraitBG_New.png";
    public static final String THE_VACANT_SHOULDER_1 = "theVacantResources/images/char/vacantCharacter/shoulder.png";
    public static final String THE_VACANT_SHOULDER_2 = "theVacantResources/images/char/vacantCharacter/shoulder2.png";
    public static final String THE_VACANT_CORPSE = "theVacantResources/images/char/vacantCharacter/corpse.png";

    public static final String BADGE_IMAGE = "theVacantResources/images/Badge.png";

    public static final String THE_VACANT_SKELETON_ATLAS = "theVacantResources/images/char/vacantCharacter/skeleton.atlas";
    public static final String THE_VACANT_SKELETON_JSON = "theVacantResources/images/char/vacantCharacter/skeleton.json";



    public static List<String> IMMUNE_POWERS = new ArrayList<String>();

    public static final ParticleManager particleManager = new ParticleManager();

    public static void initialize()
    {
        logger.info("========================= Vacanting the Mod =========================");
        VacantMod vacantMod = new VacantMod();
        IMMUNE_POWERS.add(FadingPower.POWER_ID);
        IMMUNE_POWERS.add(WraithFormPower.POWER_ID);
        IMMUNE_POWERS.add(HexPower.POWER_ID);
        IMMUNE_POWERS.add(GainStrengthPower.POWER_ID);
        IMMUNE_POWERS.add(ConfusionPower.POWER_ID);
        IMMUNE_POWERS.add(BackAttackPower.POWER_ID);
        IMMUNE_POWERS.add(DrawReductionPower.POWER_ID);
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

        receiveEditPotions();

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
        BaseMod.addEvent(new AddEventParams.Builder(GobletEvent.ID, GobletEvent.class).eventType(EventUtils.EventType.NORMAL).spawnCondition(() ->
                (AbstractDungeon.player.hasRelic(BrassGoblet.ID) || AbstractDungeon.player.hasRelic((OverflowingGobletRelic.ID))) &&
                        AbstractDungeon.player instanceof TheVacant && AbstractDungeon.actNum == 1).create());

        BaseMod.addEvent(new AddEventParams.Builder(CavernEvent.ID, CavernEvent.class).eventType(EventUtils.EventType.NORMAL).spawnCondition(() ->
                AbstractDungeon.player instanceof TheVacant && AbstractDungeon.actNum == 2).create());


        IMMUNE_POWERS.add(DizzyPower.POWER_ID);
        logger.info("Done loading badge Image and mod options");
    }

    public void receiveEditPotions()
    {
        logger.info("Beginning to edit potions");
        BaseMod.addPotion(SwipePotion.class, SWIPE_POTION_LIQUID, SWIPE_POTION_HYBRID, SWIPE_POTION_SPOTS, SwipePotion.POTION_ID, TheVacant.Enums.THE_VACANT);
        BaseMod.addPotion(MiningPotion.class, MINE_POTION_LIQUID, MINE_POTION_HYBRID, MINE_POTION_SPOTS, MiningPotion.POTION_ID, TheVacant.Enums.THE_VACANT);
        BaseMod.addPotion(ScramblePotion.class, SCRAMBLE_POTION_LIQUID, SCRAMBLE_POTION_HYBRID, SCRAMBLE_POTION_SPOTS, ScramblePotion.POTION_ID, TheVacant.Enums.THE_VACANT);
        BaseMod.addPotion(InvisibilityPotion.class, INVIS_POTION_LIQUID, INVIS_POTION_HYBRID, INVIS_POTION_SPOTS, InvisibilityPotion.POTION_ID, TheVacant.Enums.THE_VACANT);
        logger.info("Done editing potions");
    }

    @Override
    public void receiveEditRelics()
    {
        logger.info("Adding relics");
        BaseMod.addRelicToCustomPool(new BrassGoblet(), TheVacant.Enums.COLOR_GOLD);
        BaseMod.addRelicToCustomPool(new OverflowingGobletRelic(), TheVacant.Enums.COLOR_GOLD);
        BaseMod.addRelicToCustomPool(new Deathbell(), TheVacant.Enums.COLOR_GOLD);
        //BaseMod.addRelicToCustomPool(new AmethystRelic(), TheVacant.Enums.COLOR_GOLD);
        BaseMod.addRelicToCustomPool(new BoundSoul(), TheVacant.Enums.COLOR_GOLD);
        BaseMod.addRelicToCustomPool(new LocketRelic(), TheVacant.Enums.COLOR_GOLD);
        BaseMod.addRelicToCustomPool(new CrystalBallRelic(), TheVacant.Enums.COLOR_GOLD);
        BaseMod.addRelicToCustomPool(new RagRelic(), TheVacant.Enums.COLOR_GOLD);
        BaseMod.addRelicToCustomPool(new TombstoneRelic(), TheVacant.Enums.COLOR_GOLD);
        BaseMod.addRelicToCustomPool(new SilkTouch(), TheVacant.Enums.COLOR_GOLD);
        BaseMod.addRelicToCustomPool(new Holly(), TheVacant.Enums.COLOR_GOLD);
        BaseMod.addRelicToCustomPool(new EnshroudedBrassGoblet(), TheVacant.Enums.COLOR_GOLD);
        BaseMod.addRelicToCustomPool(new EnshroudedOverflowingGobletRelic(), TheVacant.Enums.COLOR_GOLD);
        BaseMod.addRelicToCustomPool(new ShroudRelic(), TheVacant.Enums.COLOR_GOLD);

        UnlockTracker.markRelicAsSeen(CrystalBallRelic.ID);
        UnlockTracker.markRelicAsSeen(RagRelic.ID);
        UnlockTracker.markRelicAsSeen(TombstoneRelic.ID);
        UnlockTracker.markRelicAsSeen(LocketRelic.ID);
        //UnlockTracker.markRelicAsSeen(AmethystRelic.ID);
        UnlockTracker.markRelicAsSeen(SilkTouch.ID);
        UnlockTracker.markRelicAsSeen(Holly.ID);
        UnlockTracker.markRelicAsSeen(BoundSoul.ID);
        //UnlockTracker.markRelicAsSeen(EnshroudedBrassGoblet.ID);
        //UnlockTracker.markRelicAsSeen(EnshroudedOverflowingGobletRelic.ID);
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

        //Options
        int gemSize = 3;
//        BaseMod.addCard(new AmethystOption());
//        BaseMod.addCard(new DiamondOption());
//        BaseMod.addCard(new EmeraldOption());
//        BaseMod.addCard(new OnyxOption());
//        BaseMod.addCard(new OpalOption());
//        BaseMod.addCard(new RubyOption());
//        BaseMod.addCard(new SapphireOption());
//        BaseMod.addCard(new TopazOption());



        BaseMod.addCard(new ShatterAmethyst());
        BaseMod.addCard(new TopazFrenzy());
        BaseMod.addCard(new DiamondIsUnbreakable());
        BaseMod.addCard(new EssenceOfBismuth());
        BaseMod.addCard(new StealSoul());
        BaseMod.addCard(new EmeraldSplash());
        BaseMod.addCard(new BackInTheMine());
        BaseMod.addCard(new SapphireStrike());
        BaseMod.addCard(new SoulBash());
        BaseMod.addCard(new OpalFracture());
        BaseMod.addCard(new Corporeate());
        //Attacks

        BaseMod.addCard(new VacantStarterStrike());
        BaseMod.addCard(new CursedBlast());
        BaseMod.addCard(new EyePoke());
        BaseMod.addCard(new Fling());
        BaseMod.addCard(new GraveWave());
        BaseMod.addCard(new Jab());
        BaseMod.addCard(new ShriekingBlast());
        BaseMod.addCard(new OnyxBlaster());
        BaseMod.addCard(new BrassMagnet());
        BaseMod.addCard(new FranticBlow());
        BaseMod.addCard(new ReaperStrike());
        BaseMod.addCard(new Pickaxe());
        BaseMod.addCard(new FromTheDepths());
        BaseMod.addCard(new Thoughtseize());
        BaseMod.addCard(new Threaten());
        BaseMod.addCard(new DarkStrike());
        BaseMod.addCard(new DimensionTear());
        BaseMod.addCard(new LashOut());
        BaseMod.addCard(new Showdown());
        BaseMod.addCard(new GildedPickaxe());
        BaseMod.addCard(new Doomed());
        BaseMod.addCard(new Vlaze());

        //Skills
        BaseMod.addCard(new VacantStarterDefend());
        BaseMod.addCard(new Cower());
        BaseMod.addCard(new Rift());
        BaseMod.addCard(new Dig());
        BaseMod.addCard(new Spinshield());
        BaseMod.addCard(new BattleScars());
        BaseMod.addCard(new EmbraceDarkness());
        BaseMod.addCard(new RubyRage());
        BaseMod.addCard(new Treasure());
        BaseMod.addCard(new Enchant());
        BaseMod.addCard(new Hex());
        BaseMod.addCard(new SoulBarrage());
        BaseMod.addCard(new Sneeze());
        BaseMod.addCard(new Unearth());
        BaseMod.addCard(new EmptyShield());
        BaseMod.addCard(new Memoria());
        BaseMod.addCard(new FromNothing());
        BaseMod.addCard(new TheAnvil());
        //BaseMod.addCard(new TimeSkip());
        BaseMod.addCard(new AwMan());
        BaseMod.addCard(new ReaperBlast());
        BaseMod.addCard(new Spelunk());
        BaseMod.addCard(new Exorcise());
        BaseMod.addCard(new Polish());
        BaseMod.addCard(new Partake());
        BaseMod.addCard(new Desperation());
        BaseMod.addCard(new Voidstone());
        BaseMod.addCard(new WearTheGoblet());

        //Powers
        BaseMod.addCard(new Acceptance());
        BaseMod.addCard(new BurdenBreak());
        BaseMod.addCard(new CleanseSoul());
        BaseMod.addCard(new Gloom());
        BaseMod.addCard(new Immaterialize());
        BaseMod.addCard(new IntoTheAbyss());
        BaseMod.addCard(new Requiem());
        BaseMod.addCard(new Recover());
        BaseMod.addCard(new VoidEmbrace());
        BaseMod.addCard(new ForgeSoul());
        BaseMod.addCard(new Rejection());
        BaseMod.addCard(new VoidForm());
        BaseMod.addCard(new RunicThoughts());
        BaseMod.addCard(new CrackedReflection());
        BaseMod.addCard(new ReachThrough());

        //Temp Cards
        BaseMod.addCard(new WailOfTheShroud());
        /*
        BaseMod.addCard(new Snap());
        BaseMod.addCard(new Crackle());
        BaseMod.addCard(new Pop());*/

        logger.info("Making sure the cards are unlocked.");

        UnlockTracker.unlockCard(ShatterAmethyst.ID);
        UnlockTracker.unlockCard(TopazFrenzy.ID);
        UnlockTracker.unlockCard(DiamondIsUnbreakable.ID);
        UnlockTracker.unlockCard(EssenceOfBismuth.ID);
        UnlockTracker.unlockCard(StealSoul.ID);
        UnlockTracker.unlockCard(EmeraldSplash.ID);
        UnlockTracker.unlockCard(BackInTheMine.ID);
        UnlockTracker.unlockCard(SapphireStrike.ID);
        UnlockTracker.unlockCard(SoulBash.ID);
        UnlockTracker.unlockCard(OpalFracture.ID);
        UnlockTracker.unlockCard(Corporeate.ID);

        //Attacks
        UnlockTracker.unlockCard(VacantStarterStrike.ID);
        UnlockTracker.unlockCard(CursedBlast.ID);
        UnlockTracker.unlockCard(EyePoke.ID);
        UnlockTracker.unlockCard(Fling.ID);
        UnlockTracker.unlockCard(GraveWave.ID);
        UnlockTracker.unlockCard(Jab.ID);
        UnlockTracker.unlockCard(ShriekingBlast.ID);
        UnlockTracker.unlockCard(OnyxBlaster.ID);
        UnlockTracker.unlockCard(BrassMagnet.ID);
        UnlockTracker.unlockCard(FranticBlow.ID);
        UnlockTracker.unlockCard(ReaperStrike.ID);
        UnlockTracker.unlockCard(Pickaxe.ID);
        UnlockTracker.unlockCard(FromTheDepths.ID);
        UnlockTracker.unlockCard(Thoughtseize.ID);
        UnlockTracker.unlockCard(Threaten.ID);
        UnlockTracker.unlockCard(DarkStrike.ID);
        UnlockTracker.unlockCard(DimensionTear.ID);
        UnlockTracker.unlockCard(LashOut.ID);
        UnlockTracker.unlockCard(Showdown.ID);
        UnlockTracker.unlockCard(SoulBash.ID);
        UnlockTracker.unlockCard(GildedPickaxe.ID);
        UnlockTracker.unlockCard(Doomed.ID);
        UnlockTracker.unlockCard(Vlaze.ID);

        //Skills
        UnlockTracker.unlockCard(VacantStarterDefend.ID);
        UnlockTracker.unlockCard(Cower.ID);
        UnlockTracker.unlockCard(Rift.ID);
        UnlockTracker.unlockCard(Dig.ID);
        UnlockTracker.unlockCard(Spinshield.ID);
        UnlockTracker.unlockCard(BattleScars.ID);
        UnlockTracker.unlockCard(EmbraceDarkness.ID);
        UnlockTracker.unlockCard(RubyRage.ID);
        UnlockTracker.unlockCard(Treasure.ID);
        UnlockTracker.unlockCard(Enchant.ID);
        UnlockTracker.unlockCard(Hex.ID);
        UnlockTracker.unlockCard(SoulBarrage.ID);
        UnlockTracker.unlockCard(Sneeze.ID);
        UnlockTracker.unlockCard(Unearth.ID);
        UnlockTracker.unlockCard(EmptyShield.ID);
        UnlockTracker.unlockCard(Memoria.ID);
        UnlockTracker.unlockCard(FromNothing.ID);
        UnlockTracker.unlockCard(TheAnvil.ID);
        //UnlockTracker.unlockCard(TimeSkip.ID);
        UnlockTracker.unlockCard(AwMan.ID);
        UnlockTracker.unlockCard(ReaperBlast.ID);
        UnlockTracker.unlockCard(Spelunk.ID);
        UnlockTracker.unlockCard(Exorcise.ID);
        UnlockTracker.unlockCard(Polish.ID);
        UnlockTracker.unlockCard(Partake.ID);
        UnlockTracker.unlockCard(Desperation.ID);
        UnlockTracker.unlockCard(Voidstone.ID);
        UnlockTracker.unlockCard(WearTheGoblet.ID);

        //Powers
        UnlockTracker.unlockCard(Acceptance.ID);
        UnlockTracker.unlockCard(BurdenBreak.ID);
        UnlockTracker.unlockCard(CleanseSoul.ID);
        UnlockTracker.unlockCard(Gloom.ID);
        UnlockTracker.unlockCard(Requiem.ID);
        UnlockTracker.unlockCard(Immaterialize.ID);
        UnlockTracker.unlockCard(IntoTheAbyss.ID);
        UnlockTracker.unlockCard(Recover.ID);
        UnlockTracker.unlockCard(VoidEmbrace.ID);
        UnlockTracker.unlockCard(ForgeSoul.ID);
        UnlockTracker.unlockCard(Rejection.ID);
        UnlockTracker.unlockCard(VoidForm.ID);
        UnlockTracker.unlockCard(RunicThoughts.ID);
        UnlockTracker.unlockCard(CrackedReflection.ID);
        UnlockTracker.unlockCard(ReachThrough.ID);
        //Powers

        //UnlockTracker.unlockCard(WailOfTheShroud.ID);
        //Options
//        UnlockTracker.unlockCard(AmethystOption.ID);
//        UnlockTracker.unlockCard(DiamondOption.ID);
//        UnlockTracker.unlockCard(EmeraldOption.ID);
//        UnlockTracker.unlockCard(OnyxOption.ID);
//        UnlockTracker.unlockCard(OpalOption.ID);
//        UnlockTracker.unlockCard(RubyOption.ID);
//        UnlockTracker.unlockCard(SapphireOption.ID);
//        UnlockTracker.unlockCard(TopazOption.ID);

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


        loadStrings("eng");

        if (Settings.language != Settings.GameLanguage.ENG)
        {
            try
            {
                loadStrings(Settings.language.toString().toLowerCase());
            }
            catch (GdxRuntimeException er)
            {
                System.out.println("Vacant: Adding keywords error: Language not found, defaulted to eng.");
            }
        }


        logger.info("Done editing strings");
    }

    private void loadStrings(String langKey)
    {

        BaseMod.loadCustomStringsFile(CardStrings.class,
                getModID() + "Resources/localization/" + langKey + "/VacantMod-Card-Strings.json");
        BaseMod.loadCustomStringsFile(RelicStrings.class,
                getModID() + "Resources/localization/" + langKey + "/VacantMod-Relic-Strings.json");
        BaseMod.loadCustomStringsFile(CharacterStrings.class,
                getModID() + "Resources/localization/" + langKey + "/VacantMod-Character-Strings.json");
        BaseMod.loadCustomStringsFile(PowerStrings.class,
                getModID() + "Resources/localization/" + langKey + "/VacantMod-Power-Strings.json");
        BaseMod.loadCustomStringsFile(OrbStrings.class,
                getModID() + "Resources/localization/" + langKey + "/VacantMod-Orb-Strings.json");


        BaseMod.loadCustomStringsFile(EventStrings.class,
                getModID() + "Resources/localization/" + langKey + "/VacantMod-Event-Strings.json");

        BaseMod.loadCustomStringsFile(PotionStrings.class,
                getModID() + "Resources/localization/" + langKey + "/VacantMod-Potion-Strings.json");

        BaseMod.loadCustomStringsFile(UIStrings.class,
                getModID() + "Resources/localization/" + langKey + "/VacantMod-UI-Strings.json");
    }

    @Override
    public void receiveEditKeywords()
    {
        String lang = Settings.language.toString().toLowerCase();
        if (!Gdx.files.internal(getModID() + "Resources/localization/" + lang + "/").exists())
            lang = "eng";
        Gson gson = new Gson();

        String json = GetLocString(lang, "VacantMod-Keyword-Strings.json");
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

    private void loadKeywords(String langKey)
    {

    }


    public String GetLocString(String langKey, String jsonPath)
    {
        String json = Gdx.files.internal(getModID() + "Resources/localization/" + langKey + "/" + jsonPath).readString(String.valueOf(StandardCharsets.UTF_8));
        return json;
    }


    @Override
    public void receiveAddAudio()
    {
        BaseMod.addAudio("theVacant:awman",  getModID() + "Resources/audio/awman.ogg");
        BaseMod.addAudio("theVacant:doom",  getModID() + "Resources/audio/Doom.wav");
        BaseMod.addAudio("theVacant:ora",  getModID() + "Resources/audio/ora.ogg");
        BaseMod.addAudio("theVacant:ora2",  getModID() + "Resources/audio/ora2.ogg");
        BaseMod.addAudio("theVacant:kingCrimson",  getModID() + "Resources/audio/kingCrimson.ogg");
        BaseMod.addAudio("theVacant:skeleton",  getModID() + "Resources/audio/mc_skeleton.ogg");
        BaseMod.addAudio("theVacant:waka",  getModID() + "Resources/audio/waka.ogg");
        BaseMod.addAudio("theVacant:gem1",  getModID() + "Resources/audio/AmethystBreak1.mp3");
        BaseMod.addAudio("theVacant:gem2",  getModID() + "Resources/audio/AmethystBreak2.mp3");
        BaseMod.addAudio("theVacant:gem3",  getModID() + "Resources/audio/AmethystBreak3.mp3");
        BaseMod.addAudio("theVacant:gem4",  getModID() + "Resources/audio/AmethystBreak4.mp3");
        BaseMod.addAudio("theVacant:gemSpawn",  getModID() + "Resources/audio/AmethystClusterPlace2.mp3");
        BaseMod.addAudio("theVacant:gemSpawn2",  getModID() + "Resources/audio/AmethystClusterPlace4.mp3");
        BaseMod.addAudio("theVacant:sneezeSmall1",  getModID() + "Resources/audio/Sneeze_Small_1.wav");
        BaseMod.addAudio("theVacant:sneezeBig1",  getModID() + "Resources/audio/Sneeze_Big_1.wav");
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

    public static String makeEventPath(String resourcePath)
    {
        return getModID() + "Resources/images/events/" + resourcePath;
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
