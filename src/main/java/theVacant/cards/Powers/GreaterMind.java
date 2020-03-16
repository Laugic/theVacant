package theVacant.cards.Powers;

import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.BorderLongFlashEffect;
import com.megacrit.cardcrawl.vfx.combat.VerticalAuraEffect;
import theVacant.VacantMod;
import theVacant.cards.AbstractDynamicCard;
import theVacant.characters.TheVacant;
import com.badlogic.gdx.graphics.Color;
import theVacant.powers.GreaterMindPower;

import static theVacant.VacantMod.makeCardPath;


public class GreaterMind extends AbstractDynamicCard
{

    public static final String ID = VacantMod.makeID(GreaterMind.class.getSimpleName());
    public static final String IMG = makeCardPath("Power.png");

    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;

    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.POWER;
    public static final CardColor COLOR = TheVacant.Enums.COLOR_GOLD;

    private static final int COST = 3;

    public GreaterMind()
    {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        this.magicNumber = this.baseMagicNumber = 3;
        this.getBonusMillToMagic = true;
    }

    @Override
    public void use(AbstractPlayer player, AbstractMonster monster)
    {
        addToBot(new VFXAction(player, new VerticalAuraEffect(Color.BLACK, player.hb.cX, player.hb.cY), 0.33F));
        addToBot(new VFXAction(player, new BorderLongFlashEffect(Color.BLACK), 0.0F, true));
        addToBot(new VFXAction(player, new BorderLongFlashEffect(Color.CYAN), 0.0F, true));
        addToBot(new VFXAction(player, new BorderLongFlashEffect(Color.WHITE), 0.0F, true));
        if(!player.hasPower(GreaterMindPower.POWER_ID))
        {
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(player, player, new GreaterMindPower(player, player, 1), 1));
            for (AbstractCard card:player.hand.group)
            {
                if(card.rarity == CardRarity.COMMON || card.rarity == CardRarity.BASIC)
                    card.setCostForTurn(-9);
            }
        }
    }

    @Override
    public void upgrade()
    {
        if (!upgraded)
        {
            upgradeName();
            upgradeBaseCost(2);
            initializeDescription();
        }
    }
}