package theVacant.util;

import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.localization.Keyword;
import theVacant.VacantMod;
import theVacant.cards.Skills.AwMan;

public class KeywordManager
{
    public static final String ID = VacantMod.makeID(KeywordManager.class.getSimpleName());
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static String VOID_ID = cardStrings.EXTENDED_DESCRIPTION[0];
    public static String VOIDBOUND_ID = cardStrings.EXTENDED_DESCRIPTION[1];
    public static String MATERIALIZE_ID =  cardStrings.EXTENDED_DESCRIPTION[2];
}
