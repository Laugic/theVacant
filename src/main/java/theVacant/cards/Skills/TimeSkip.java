package theVacant.cards.Skills;

import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.ExhaustAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.BorderLongFlashEffect;
import com.megacrit.cardcrawl.vfx.combat.VerticalAuraEffect;
import theVacant.VacantMod;
import theVacant.actions.TimeSkipAction;
import theVacant.cards.AbstractDynamicCard;
import theVacant.characters.TheVacant;
import theVacant.powers.DelayedExhumePower;
import theVacant.powers.GreaterMindPower;
import theVacant.powers.NecraRune;
import theVacant.vfx.NecraVFX;

import static theVacant.VacantMod.makeCardPath;

public class TimeSkip extends AbstractDynamicCard
{

    public static final String ID = VacantMod.makeID(TimeSkip.class.getSimpleName());
    public static final String IMG = makeCardPath("Skill.png");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);

    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = TheVacant.Enums.COLOR_GOLD;

    private static final int COST = 3;

    public TimeSkip()
    {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        this.magicNumber = this.baseMagicNumber = 2;
        this.exhaust = true;
    }

    @Override
    public void use(AbstractPlayer player, AbstractMonster monster)
    {
        addToBot(new VFXAction(player, new VerticalAuraEffect(Color.BLACK, player.hb.cX, player.hb.cY), 0.33F));
        addToBot(new VFXAction(player, new BorderLongFlashEffect(Color.BLACK), 0.0F, true));
        addToBot(new VFXAction(player, new BorderLongFlashEffect(Color.CYAN), 0.0F, true));
        addToBot(new VFXAction(player, new BorderLongFlashEffect(Color.WHITE), 0.0F, true));
        if(this.upgraded)
        {
            AbstractDungeon.actionManager.addToBottom(new DrawCardAction(player, this.magicNumber));
        }
        AbstractDungeon.actionManager.addToBottom(new TimeSkipAction());
    }

    @Override
    public void upgrade()
    {
        if (!upgraded)
        {
            upgradeName();
            this.rawDescription = cardStrings.UPGRADE_DESCRIPTION;
            initializeDescription();
        }
    }
}