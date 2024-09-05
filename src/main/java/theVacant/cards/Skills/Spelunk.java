package theVacant.cards.Skills;

import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.watcher.ChooseOneAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theVacant.VacantMod;
import theVacant.actions.MineGemAction;
import theVacant.actions.SpelunkAction;
import theVacant.cards.AbstractDynamicCard;
import theVacant.cards.Special.*;
import theVacant.characters.TheVacant;
import theVacant.orbs.SapphireOrb;

import java.util.ArrayList;

import static theVacant.VacantMod.makeCardPath;

public class Spelunk extends AbstractDynamicCard
{

    public static final String ID = VacantMod.makeID(Spelunk.class.getSimpleName());
    public static final String IMG = makeCardPath("Spelunk.png");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);

    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = TheVacant.Enums.COLOR_GOLD;

    private static final int COST = 1;

    public Spelunk()
    {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        block = baseBlock = 8;
        magicNumber = baseMagicNumber = 2;
        postMillAction = true;
    }

    @Override
    public void use(AbstractPlayer player, AbstractMonster m)
    {
        addToBot(new GainBlockAction(player, block));
        //addToBot(new SpelunkAction(magicNumber));
        addToBot(new MineGemAction(new SapphireOrb(magicNumber)));
    }

    @Override
    public void PostMillAction()
    {
        addToBot(new MineGemAction(new SapphireOrb(magicNumber)));
    }

    @Override
    public void upgrade()
    {
        if (!upgraded)
        {
            upgradeName();
            upgradeBlock(2);
            upgradeMagicNumber(1);
            initializeDescription();
        }
    }
}