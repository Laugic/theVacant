package theVacant.cards.Skills;

import com.megacrit.cardcrawl.actions.common.LoseHPAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.orbs.AbstractOrb;
import theVacant.VacantMod;
import theVacant.actions.ChipOrbAction;
import theVacant.cards.AbstractDynamicCard;
import theVacant.characters.TheVacant;
import theVacant.orbs.AbstractGemOrb;

import static theVacant.VacantMod.makeCardPath;

public class AwMan extends AbstractDynamicCard
{

    public static final String ID = VacantMod.makeID(AwMan.class.getSimpleName());
    public static final String IMG = makeCardPath("AwMan.png");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);

    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = TheVacant.Enums.COLOR_GOLD;

    private static final int COST = 0;

    public AwMan()
    {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        magicNumber = baseMagicNumber = 4;
        exhaust = true;
    }

    @Override
    public void use(AbstractPlayer player, AbstractMonster monster)
    {
        AbstractDungeon.actionManager.addToBottom(new SFXAction("theVacant:awman"));
        addToBot(new LoseHPAction(player, player, magicNumber));
        if(player.orbs.size() > 0)
        {
            for (AbstractOrb gem : player.orbs)
            {
                if(gem instanceof AbstractGemOrb)
                    addToBot(new ChipOrbAction(gem, 1));
            }
        }
    }

    @Override
    public void upgrade()
    {
        if (!upgraded)
        {
            upgradeName();
            upgradeMagicNumber(-2);
            upgradedMagicNumber = true;
            initializeDescription();
        }
    }
}