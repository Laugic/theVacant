package theVacant.cards.Special;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theVacant.VacantMod;
import theVacant.cards.AbstractDynamicCard;

import static theVacant.VacantMod.makeCardPath;
/*
public class GemOption extends AbstractDynamicCard
{
    public static final String ID = VacantMod.makeID(GemOption.class.getSimpleName());
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);

    public static final int SMALL_SIZE = 2, BIG_SIZE = 3;
    public static boolean big = false;

    public GemOption(String id, String img, CardType type, CardColor color, CardRarity rarity, CardTarget target)
    {
        super(id, img, -2, type, color, rarity, target);
        if(AbstractDungeon.miscRng != null)
            big = (AbstractDungeon.miscRng.randomBoolean() && AbstractDungeon.miscRng.randomBoolean());
        magicNumber = baseMagicNumber = GetSize(big);
        name = big?(cardStrings.EXTENDED_DESCRIPTION[0] + name):name;
    }

    private int GetSize(boolean isBig) {
        if(isBig)
            return GetBigSize();
        return GetSmallSize();
    }

    public int GetSmallSize(){
        return SMALL_SIZE;
    }

    public int GetBigSize(){
        return BIG_SIZE;
    }

    @Override
    public void upgrade() {

    }

    @Override
    public void use(AbstractPlayer abstractPlayer, AbstractMonster abstractMonster) {

    }
}
*/