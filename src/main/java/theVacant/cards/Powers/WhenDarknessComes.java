package theVacant.cards.Powers;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theVacant.VacantMod;
import theVacant.cards.AbstractDynamicCard;
import theVacant.characters.TheVacant;
import theVacant.powers.DoomPower;

import static theVacant.VacantMod.makeCardPath;


public class WhenDarknessComes extends AbstractDynamicCard
{

    public static final String ID = VacantMod.makeID(WhenDarknessComes.class.getSimpleName());
    public static final String IMG = makeCardPath("Power.png");

    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;

    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.POWER;
    public static final CardColor COLOR = TheVacant.Enums.COLOR_GOLD;

    private static final int COST = 2;
    private static final int DOOM_AMOUNT = 2;

    public WhenDarknessComes()
    {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        this.magicNumber = this.baseMagicNumber = 1;
    }

    @Override
    public void use(AbstractPlayer player, AbstractMonster monster)
    {
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(player, player, new DoomPower(player, player, DOOM_AMOUNT), DOOM_AMOUNT));
        for (AbstractMonster mo : (AbstractDungeon.getCurrRoom()).monsters.monsters)
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(mo, player, new DoomPower(mo, mo, DOOM_AMOUNT), DOOM_AMOUNT, true, AbstractGameAction.AttackEffect.NONE));
        //AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(player, player, new WhenDarknessComesPower(player, player, this.magicNumber), this.magicNumber));
    }

    @Override
    public void upgrade()
    {
        if (!upgraded)
        {
            upgradeName();
            upgradeMagicNumber(1);
            this.upgradedMagicNumber = true;
            initializeDescription();
        }
    }
}