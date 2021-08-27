package theVacant.cards.archive.Attacks;

import basemod.BaseMod;
import basemod.helpers.TooltipInfo;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theVacant.VacantMod;
import theVacant.actions.EnhanceInPileAction;
import theVacant.actions.ExhaustDiscardAction;
import theVacant.actions.ExhumeAndEnhanceAction;
import theVacant.cards.AbstractDynamicCard;
import theVacant.cards.Modifiers.EchoModifier;
import theVacant.cards.Modifiers.MaterializeModifier;
import theVacant.characters.TheVacant;
import theVacant.util.KeywordManager;

import java.util.ArrayList;
import java.util.List;

import static theVacant.VacantMod.makeCardPath;

public class DimensionTear extends AbstractDynamicCard
{

    public static final String ID = VacantMod.makeID(DimensionTear.class.getSimpleName());
    public static final String IMG = makeCardPath("DimensionTear.png");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);

    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final CardType TYPE = CardType.ATTACK;
    public static final CardColor COLOR = TheVacant.Enums.COLOR_GOLD;

    private static final int COST = 2;
    private static final int DAMAGE = 12;
    private static ArrayList<TooltipInfo> Tooltip;

    public DimensionTear()
    {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        magicNumber = baseMagicNumber = 2;
        damage = baseDamage = DAMAGE;
        exhaust = true;
    }

    @Override
    public void use(AbstractPlayer player, AbstractMonster monster)
    {
        addToBot( new EnhanceInPileAction(player.exhaustPile, 9999, MaterializeModifier.ID, magicNumber));
        addToBot( new DamageAction(monster, new DamageInfo(player, damage, damageTypeForTurn), AbstractGameAction.AttackEffect.SLASH_HEAVY));
        //addToBot(new ExhumeAndEnhanceAction(player.exhaustPile.size() + 2));
        //addToBot(new ExhaustDiscardAction(-1));
    }

    @Override
    public List<TooltipInfo> getCustomTooltips()
    {
        if(Tooltip == null)
        {
            Tooltip = new ArrayList<>();
            Tooltip.add(new TooltipInfo(BaseMod.getKeywordProper(KeywordManager.MATERIALIZE_ID), BaseMod.getKeywordDescription(KeywordManager.MATERIALIZE_ID)));
        }
        return Tooltip;
    }
    @Override
    public void upgrade()
    {
        if (!upgraded)
        {
            upgradeName();
            upgradeDamage(6);
            upgradedDamage = true;
            initializeDescription();
        }
    }
}