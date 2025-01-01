package theVacant.cards.Skills;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.BetterDiscardPileToHandAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theVacant.VacantMod;
import theVacant.actions.MineGemAction;
import theVacant.actions.ReturnAction;
import theVacant.cards.AbstractDynamicCard;
import theVacant.characters.TheVacant;
import theVacant.orbs.SapphireOrb;

import static theVacant.VacantMod.makeCardPath;

public class Unearth extends AbstractDynamicCard
{

    public static final String ID = VacantMod.makeID(Unearth.class.getSimpleName());
    public static final String IMG = makeCardPath("Unearth.png");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);

    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = TheVacant.Enums.COLOR_GOLD;

    private static final int COST = -2;

    public Unearth()
    {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        magicNumber = baseMagicNumber = 2;
        isUnnate = true;
        postMillAction = true;
    }

    @Override
    public boolean canUse(AbstractPlayer p, AbstractMonster m) {
        return false;
    }

    @Override
    public void PostMillAction()
    {
        addToBot(new AbstractGameAction() {
            @Override
            public void update() {
                addToBot(new BetterDiscardPileToHandAction(magicNumber));
                isDone = true;
            }
        });
    }

    @Override
    public void use(AbstractPlayer player, AbstractMonster monster)
    {
//        if(player.drawPile.size() == 0)
//            AbstractDungeon.actionManager.addToBottom(new ShuffleDiscardToBottomOfDrawAction());
//        AbstractDungeon.actionManager.addToBottom(new DrawFromBottom(magicNumber));
//        addToBot(new SpelunkAction(upgraded?4:3));

        //AbstractDungeon.actionManager.addToBottom(new ReturnAction(magicNumber));

    }

    @Override
    public void upgrade()
    {
        if (!upgraded)
        {
            upgradeName();
            isHeavy = true;
            rawDescription = cardStrings.UPGRADE_DESCRIPTION;
            initializeDescription();
        }
    }
}