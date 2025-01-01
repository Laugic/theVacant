package theVacant.cards.Attacks;

import basemod.BaseMod;
import basemod.helpers.TooltipInfo;
import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.vfx.BorderFlashEffect;
import com.megacrit.cardcrawl.vfx.combat.MindblastEffect;
import com.megacrit.cardcrawl.vfx.combat.VerticalAuraEffect;
import theVacant.VacantMod;
import theVacant.actions.CurseAction;
import theVacant.cards.AbstractDynamicCard;
import theVacant.cards.Modifiers.VoidboundModifier;
import theVacant.characters.TheVacant;
import theVacant.powers.DoomPower;
import theVacant.powers.VoidPower;
import theVacant.util.KeywordManager;

import java.util.ArrayList;
import java.util.List;

import static theVacant.VacantMod.makeCardPath;

public class CursedBlast extends AbstractDynamicCard
{

    public static final String ID = VacantMod.makeID(CursedBlast.class.getSimpleName());
    public static final String IMG = makeCardPath("CursedBlast.png");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);

    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final CardType TYPE = CardType.ATTACK;
    public static final CardColor COLOR = TheVacant.Enums.COLOR_GOLD;

    private static ArrayList<TooltipInfo> Tooltip;

    private static final int COST = 1;
    private static final int DAMAGE = 9;
    private static final int UPGRADE_PLUS_DMG = 1;

    public CursedBlast()
    {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        damage = baseDamage = DAMAGE;
        magicNumber = baseMagicNumber = 2;
        isUnnate = true;
    }

    @Override
    public void use(AbstractPlayer player, AbstractMonster monster)
    {
        addToBot(new VFXAction(player, new BorderFlashEffect(Color.PURPLE), 0.3F, true));
        AbstractDungeon.actionManager.addToBottom(new SFXAction("ATTACK_HEAVY"));
        AbstractDungeon.actionManager.addToBottom(new VFXAction(player, new MindblastEffect(player.dialogX, player.dialogY, player.flipHorizontal), 0.1F));
        //addToBot(new DamageAllEnemiesAction(player, multiDamage, damageTypeForTurn, AbstractGameAction.AttackEffect.NONE));
        addToBot(new DamageAction(monster, new DamageInfo(player, damage, DamageInfo.DamageType.NORMAL), AbstractGameAction.AttackEffect.BLUNT_LIGHT));
        addToBot(new ApplyPowerAction(player, player, new VoidPower(player, player, magicNumber), magicNumber));
    }

    @Override
    public List<TooltipInfo> getCustomTooltips()
    {
        if(Tooltip == null)
        {
            Tooltip = new ArrayList<>();
            Tooltip.add(new TooltipInfo(BaseMod.getKeywordProper(KeywordManager.VOID_ID), BaseMod.getKeywordDescription(KeywordManager.VOID_ID)));
            //Tooltip.add(new TooltipInfo(BaseMod.getKeywordProper(KeywordManager.VOID_FORM_ID), BaseMod.getKeywordDescription(KeywordManager.VOID_FORM_ID)));
        }
        return Tooltip;
    }

    @Override
    public void upgrade()
    {
        if (!upgraded)
        {
            upgradeName();
            upgradeDamage(UPGRADE_PLUS_DMG);
            VoidboundModifier.Enhance(this, 1);
            initializeDescription();
        }
    }
}