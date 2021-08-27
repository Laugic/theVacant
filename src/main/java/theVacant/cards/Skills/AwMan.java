package theVacant.cards.Skills;

import com.evacipated.cardcrawl.mod.stslib.actions.tempHp.AddTemporaryHPAction;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.actions.unique.RegenAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theVacant.VacantMod;
import theVacant.actions.GemDiscoveryAction;
import theVacant.cards.AbstractDynamicCard;
import theVacant.cards.Modifiers.EchoModifier;
import theVacant.cards.Modifiers.SoulforgedModifier;
import theVacant.characters.TheVacant;

import static theVacant.Enums.VacantTags.GEMS;
import static theVacant.VacantMod.makeCardPath;

public class AwMan extends AbstractDynamicCard
{

    public static final String ID = VacantMod.makeID(AwMan.class.getSimpleName());
    public static final String IMG = makeCardPath("Skill.png");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);

    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = TheVacant.Enums.COLOR_GOLD;

    private static final int COST = 1;

    public AwMan()
    {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        block = baseBlock = 5;
    }

    @Override
    public void use(AbstractPlayer player, AbstractMonster monster)
    {
        //addToBot(new GemDiscoveryAction());
        //addToBot(new DamageAction(player, new DamageInfo(player, damage, DamageInfo.DamageType.NORMAL), AbstractGameAction.AttackEffect.BLUNT_LIGHT));
        //AbstractDungeon.actionManager.addToBottom(new BetterDiscardPileToHandAction(magicNumber));
        AbstractDungeon.actionManager.addToBottom(new GainBlockAction(player, player, block));
        if(GEMS.size() > 0)
        {
            AbstractCard c = GEMS.get(AbstractDungeon.cardRandomRng.random(GEMS.size() - 1)).makeCopy();
            /*if(upgraded)
                c.setCostForTurn(0);*/
            addToBot(new MakeTempCardInHandAction(c, true));
        }
    }

    @Override
    public void upgrade()
    {
        if (!upgraded)
        {
            upgradeName();
            upgradeBaseCost(0);
            upgradedCost = true;
            initializeDescription();
        }
    }
}