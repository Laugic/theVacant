package theVacant.cards.Skills;

import com.evacipated.cardcrawl.mod.stslib.actions.tempHp.AddTemporaryHPAction;
import com.megacrit.cardcrawl.actions.common.LoseHPAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theVacant.VacantMod;
import theVacant.cards.AbstractDynamicCard;
import theVacant.characters.TheVacant;

import static theVacant.VacantMod.makeCardPath;

public class Expand extends AbstractDynamicCard
{

    public static final String ID = VacantMod.makeID(Expand.class.getSimpleName());
    public static final String IMG = makeCardPath("Expand.png");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);

    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = TheVacant.Enums.COLOR_GOLD;

    private static final int COST = 1;

    public Expand()
    {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        magicNumber = baseMagicNumber = 4;
        exhaust = true;
        //purgeOnUse = true;
        //tags.add(AbstractCard.CardTags.HEALING);
    }

    @Override
    public void use(AbstractPlayer player, AbstractMonster m)
    {
        //player.increaseMaxHp(magicNumber, true);
        AbstractDungeon.actionManager.addToBottom(new LoseHPAction(player, player, magicNumber));
        if(player.discardPile.size() > 0)
            addToBot(new AddTemporaryHPAction(player, player, player.discardPile.size()));
    }

    @Override
    public void upgrade()
    {
        if (!upgraded)
        {
            upgradeName();
            ricochet = true;
            rawDescription = cardStrings.UPGRADE_DESCRIPTION;
            upgradeMagicNumber(-2);
            upgradedMagicNumber = true;
            initializeDescription();
        }
    }
}