package theVacant.cards.Skills;

import com.evacipated.cardcrawl.mod.stslib.actions.tempHp.AddTemporaryHPAction;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.combat.LightningEffect;
import theVacant.VacantMod;
import theVacant.cards.AbstractDynamicCard;
import theVacant.characters.TheVacant;
import theVacant.powers.TemperancePower;

import static theVacant.VacantMod.makeCardPath;

public class HolyWater extends AbstractDynamicCard
{

    public static final String ID = VacantMod.makeID(HolyWater.class.getSimpleName());
    public static final String IMG = makeCardPath("HolyWater.png");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);

    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = TheVacant.Enums.COLOR_GOLD;

    private static final int COST = 2, TEMPERANCE = 8;

    public HolyWater()
    {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        magicNumber = baseMagicNumber = 12;
        this.exhaust = true;
    }

    @Override
    public void use(AbstractPlayer player, AbstractMonster monster)
    {
        AbstractDungeon.effectsQueue.add(new LightningEffect(AbstractDungeon.player.hb.cX, AbstractDungeon.player.hb.cY));
        addToBot(new AddTemporaryHPAction(player, player, magicNumber));
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(player, player, new TemperancePower(player, player, TEMPERANCE),  TEMPERANCE));
    }

    @Override
    public void upgrade()
    {
        if (!upgraded)
        {
            upgradeName();
            upgradeMagicNumber(4);
            this.upgradedMagicNumber = true;
            initializeDescription();
        }
    }
}