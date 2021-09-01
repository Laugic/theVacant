package theVacant.cards.archive.Skills;

import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theVacant.VacantMod;
import theVacant.cards.AbstractDynamicCard;
import theVacant.characters.TheVacant;

import static theVacant.VacantMod.makeCardPath;

public class EmptyShield extends AbstractDynamicCard
{

    public static final String ID = VacantMod.makeID(EmptyShield.class.getSimpleName());
    public static final String IMG = makeCardPath("Skill.png");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);

    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = TheVacant.Enums.COLOR_GOLD;

    private static final int COST = 2;
    private static final int BLOCK = 0;

    public EmptyShield()
    {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        this.baseBlock = BLOCK;
        this.exhaust = true;
    }

    @Override
    public void use(AbstractPlayer player, AbstractMonster m)
    {
        AbstractDungeon.actionManager.addToBottom(new GainBlockAction(player, player, this.block));
    }

    @Override
    public void upgrade()
    {
        if (!upgraded)
        {
            upgradeName();
            this.exhaust = false;
            this.rawDescription = cardStrings.UPGRADE_DESCRIPTION;
            this.initializeDescription();
        }
    }
    @Override
    public void atTurnStart()
    {
        getBlock();
        getDesc();
        super.atTurnStart();
    }
    @Override
    public void applyPowers()
    {
        getBlock();
        getDesc();
        super.applyPowers();
    }

    @Override
    public void onMoveToDiscard()
    {
        this.rawDescription = this.upgraded?cardStrings.UPGRADE_DESCRIPTION:cardStrings.DESCRIPTION;
        initializeDescription();
    }

    private void getBlock()
    {
        AbstractPlayer player = AbstractDungeon.player;
        if(player != null)
            this.baseBlock = player.maxHealth - player.currentHealth;
        else
            this.baseBlock = 0;
    }

    private void getDesc()
    {
        this.rawDescription = this.upgraded?cardStrings.UPGRADE_DESCRIPTION:cardStrings.DESCRIPTION;
        int amount = this.block;
        this.rawDescription += cardStrings.EXTENDED_DESCRIPTION[0] + amount + cardStrings.EXTENDED_DESCRIPTION[1];
        initializeDescription();
    }
}