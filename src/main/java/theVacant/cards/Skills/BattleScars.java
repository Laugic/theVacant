package theVacant.cards.Skills;

import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.StrengthPower;
import com.megacrit.cardcrawl.powers.watcher.VigorPower;
import com.megacrit.cardcrawl.vfx.combat.InflameEffect;
import com.megacrit.cardcrawl.vfx.combat.VerticalAuraEffect;
import theVacant.VacantMod;
import theVacant.cards.AbstractDynamicCard;
import theVacant.characters.TheVacant;
import theVacant.powers.TemperancePower;

import static theVacant.VacantMod.makeCardPath;

public class BattleScars extends AbstractDynamicCard
{

    public static final String ID = VacantMod.makeID(BattleScars.class.getSimpleName());
    public static final String IMG = makeCardPath("BattleScars.png");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);

    private static final CardRarity RARITY = CardRarity.COMMON;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = TheVacant.Enums.COLOR_GOLD;

    private static final int COST = 0;

    public BattleScars()
    {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        magicNumber = baseMagicNumber = 4;
        checkHollow = true;
    }

    @Override
    public void use(AbstractPlayer player, AbstractMonster monster)
    {
        addToBot(new VFXAction(player, new InflameEffect(player), .5F));
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(player, player, new VigorPower(player, magicNumber), magicNumber));
        if(getHollow())
        {
            addToBot(new VFXAction(player, new VerticalAuraEffect(new Color(0.25f, .75f, 1, .5f), player.hb.cX, player.hb.cY), 0.25F));
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(player, player, new TemperancePower(player, player, magicNumber), magicNumber));
        }
    }

    @Override
    public void upgrade()
    {
        if (!upgraded)
        {
            upgradeName();
            upgradeMagicNumber(2);
            upgradedMagicNumber = true;
            initializeDescription();
        }
    }
}