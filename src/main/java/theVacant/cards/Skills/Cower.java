package theVacant.cards.Skills;

import basemod.devcommands.draw.Draw;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theVacant.VacantMod;
import theVacant.cards.AbstractDynamicCard;
import theVacant.characters.TheVacant;

import static theVacant.VacantMod.makeCardPath;

public class Cower extends AbstractDynamicCard
{

    public static final String ID = VacantMod.makeID(Cower.class.getSimpleName());
    public static final String IMG = makeCardPath("Cower.png");

    private static final CardRarity RARITY = CardRarity.COMMON;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = TheVacant.Enums.COLOR_GOLD;

    private static final int COST = 1;
    private static final int BLOCK = 3;
    private static final int UPGRADE_PLUS_BLOCK = 1;

    public Cower()
    {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        block = baseBlock = BLOCK;
        magicNumber = baseMagicNumber = 2;
        checkHollow = true;
    }

    @Override
    public void use(AbstractPlayer player, AbstractMonster monster)
    {
        applyPowers();
        AbstractDungeon.actionManager.addToBottom(new GainBlockAction(player, player, block));
        AbstractDungeon.actionManager.addToBottom(new GainBlockAction(player, player, block));
    }

    @Override
    public void triggerWhenDrawn() {
        applyPowers();
    }

    @Override
    public void calculateCardDamage(AbstractMonster mo) {
        super.calculateCardDamage(mo);
        applyPowers();
    }

    @Override
    public void applyPowers() {
        int realBaseBlock = baseBlock;

        if(getHollow())
            baseBlock = realBaseBlock + magicNumber;

        block = baseBlock;
        super.applyPowers();
        baseBlock = realBaseBlock;
        isBlockModified = block != baseBlock;
    }

    @Override
    public void upgrade()
    {
        if (!upgraded)
        {
            upgradeName();
            upgradeBlock(UPGRADE_PLUS_BLOCK);
            upgradeMagicNumber(1);
            initializeDescription();
        }
    }
}