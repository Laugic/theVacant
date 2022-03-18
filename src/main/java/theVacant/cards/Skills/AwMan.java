package theVacant.cards.Skills;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.actions.common.LoseHPAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
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
    private static final CardTarget TARGET = CardTarget.NONE;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = TheVacant.Enums.COLOR_GOLD;

    private static final int COST = 0;

    public AwMan()
    {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        damage = baseDamage = magicNumber = baseMagicNumber = 5;
        isMultiDamage = true;
    }

    @Override
    public void use(AbstractPlayer player, AbstractMonster monster)
    {
        AbstractDungeon.actionManager.addToBottom(new SFXAction("theVacant:awman"));

        addToBot(new DamageAction(player, new DamageInfo(player, damage, damageTypeForTurn), AbstractGameAction.AttackEffect.FIRE));
        if(upgraded)
            AbstractDungeon.actionManager.addToBottom(new DamageAllEnemiesAction(player, DamageInfo.createDamageMatrix(magicNumber, false), DamageInfo.DamageType.NORMAL, AbstractGameAction.AttackEffect.FIRE));

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
            rawDescription = cardStrings.UPGRADE_DESCRIPTION;
            initializeDescription();
        }
    }
}