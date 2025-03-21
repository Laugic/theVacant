package theVacant.cards.Special;

import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theVacant.VacantMod;
import theVacant.actions.MineGemAction;
import theVacant.cards.AbstractDynamicCard;
import theVacant.orbs.RubyOrb;
import theVacant.orbs.TopazOrb;

import static theVacant.VacantMod.makeCardPath;

//public class TopazOption extends GemOption
//{
//    public static final String ID = VacantMod.makeID(TopazOption.class.getSimpleName());
//    public static final String IMG = makeCardPath("TopazFrenzy.png");
//    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
//
//    private static final CardRarity RARITY = CardRarity.SPECIAL;
//    private static final CardTarget TARGET = CardTarget.NONE;
//    private static final CardType TYPE = CardType.ATTACK;
//    public static final CardColor COLOR = CardColor.COLORLESS;
//
//    public static final int SMALL_SIZE = 2, BIG_SIZE = 3;
//
//    public TopazOption()
//    {
//        super(ID, IMG, TYPE, COLOR, RARITY, TARGET);
//    }
//
//    @Override
//    public int GetSmallSize(){
//        return SMALL_SIZE;
//    }
//
//    @Override
//    public int GetBigSize(){
//        return BIG_SIZE;
//    }
//
//    public void use(AbstractPlayer p, AbstractMonster m)
//    {
//        onChoseThisOption();
//    }
//
//    @Override
//    public void onChoseThisOption()
//    {
//        addToTop(new MineGemAction(new TopazOrb(magicNumber)));
//    }
//
//    @Override
//    public void upgrade()
//    {
//        if (!upgraded)
//        {
//            upgradeName();
//            upgradeMagicNumber(1);
//            upgradedMagicNumber = true;
//            initializeDescription();
//        }
//    }
//}
