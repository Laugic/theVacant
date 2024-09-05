package theVacant.cards.Skills;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theVacant.VacantMod;
import theVacant.actions.AddCopyOfLastExhaustedCardToHandAction;
import theVacant.actions.PlayDiscardForFreeAndExhaustAction;
import theVacant.cards.AbstractDynamicCard;
import theVacant.characters.TheVacant;
import theVacant.powers.MemoriaPower;

import static theVacant.VacantMod.makeCardPath;

public class Memoria extends AbstractDynamicCard
{

    public static final String ID = VacantMod.makeID(Memoria.class.getSimpleName());
    public static final String IMG = makeCardPath("Memoria.png");
    public static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);

    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = TheVacant.Enums.COLOR_GOLD;

    private static final int COST = 1;

    public Memoria()
    {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        magicNumber = baseMagicNumber = 1;
    }

    @Override
    public void use(AbstractPlayer player, AbstractMonster monster)
    {
        //addToBot(new PlayDiscardForFreeAndExhaustAction(1));
        addToBot(new ApplyPowerAction(player, player, new MemoriaPower(player, player, magicNumber), magicNumber));
    }
/*
    @Override
    public void applyPowers() {
        super.applyPowers();
        if(getLastExhaustedCard() != null)
            cardsToPreview = getLastExhaustedCard().makeStatEquivalentCopy();
    }
    */
/*
    public static AbstractCard getLastExhaustedCard(){
        ArrayList<AbstractCard> exhaustedCards = new ArrayList<>();
        for (AbstractCard card: AbstractDungeon.actionManager.cardsPlayedThisCombat) {
            if(AbstractDungeon.player.exhaustPile.contains(card))
                exhaustedCards.add(card);
        }
        Collections.reverse(exhaustedCards);
        if(exhaustedCards.size() > 0)
            return exhaustedCards.get(0);
        else
            return null;
    }
*/
    @Override
    public void upgrade()
    {
        if (!upgraded)
        {
            upgradeName();
            upgradeMagicNumber(1);
            initializeDescription();
        }
    }
}