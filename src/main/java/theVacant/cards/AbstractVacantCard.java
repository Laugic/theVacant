package theVacant.cards;

import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import theVacant.VacantMod;
import theVacant.cards.Powers.BurdenBreak;
import theVacant.characters.TheVacant;
import theVacant.powers.BurdenBreakPower;
import theVacant.powers.EtchPower;
import theVacant.powers.WillPower;
import theVacant.relics.Deathbell;

public abstract class AbstractVacantCard extends CustomCard
{
    public boolean isUnnate;
    public boolean isHeavy;
    public boolean rebound;
    public boolean postMillAction;
    public boolean getBonusMillToMagic;
    public boolean displayFracturedTooltip;
    public int bonusMillAmount;
    public boolean displayWill;
    public boolean hasUpgradeDesc;
    public boolean prime;
    private CardStrings cardStrings = null;
    private static final CardStrings cardStringsAbstract = CardCrawlGame.languagePack.getCardStrings(VacantMod.makeID(AbstractVacantCard.class.getSimpleName()));

    public AbstractVacantCard(final String ID,
                              final String name,
                              final String img,
                              final int cost,
                              final String rawDescription,
                              final CardType type,
                              final CardColor color,
                              final CardRarity rarity,
                              final CardTarget target) {

        super(ID, name, img, cost, rawDescription, type, color, rarity, target);
        isCostModified = false;
        isCostModifiedForTurn = false;
        isDamageModified = false;
        isBlockModified = false;
        isMagicNumberModified = false;
        isUnnate = false;
        isHeavy = false;
        rebound = false;
        postMillAction = false;
        getBonusMillToMagic = false;
        displayFracturedTooltip = false;
        bonusMillAmount = 0;
        displayWill = false;
        hasUpgradeDesc = false;
        prime = true;
        cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    }

    public void PostMillAction()
    {

    }

    @Override
    public void use(AbstractPlayer player, AbstractMonster monster)
    {
        if(this.prime)
            Prime();
    }

    public void Prime()
    {
        this.prime = false;
    }

    @Override
    public void applyPowers()
    {
        GetBonusMill();
        /*if(this.displayWill)
            GetWillTooltip();*/
        super.applyPowers();
    }
    @Override
    public void atTurnStart()
    {
        GetBonusMill();
        super.atTurnStart();
    }
    @Override
    public void calculateCardDamage(AbstractMonster monster)
    {
        GetBonusMill();
        super.calculateCardDamage(monster);
    }

    private void GetBonusMill()
    {
        this.bonusMillAmount = 0;
        AbstractPlayer player = AbstractDungeon.player;
        if(player != null)
        {
            if(player.hasRelic(Deathbell.ID) && player.currentHealth <= player.maxHealth / 2)
                this.bonusMillAmount += 2;
            if(player.hasPower(BurdenBreakPower.POWER_ID))
                this.bonusMillAmount += player.getPower(BurdenBreakPower.POWER_ID).amount;
        }
        if(this.getBonusMillToMagic)
        {
            this.magicNumber = this.baseMagicNumber;
            this.magicNumber += this.bonusMillAmount;
            if(this.magicNumber != this.baseMagicNumber)
                this.isMagicNumberModified = true;
        }
    }

    public int GetWill()
    {
        /*AbstractPlayer player = AbstractDungeon.player;
        if(player != null && player.hasPower(WillPower.POWER_ID))
                return ((player.getPower(WillPower.POWER_ID)).amount);*/
        return 0;
    }

    private void GetWillTooltip()
    {
        /*
        int count = GetWill();
        this.rawDescription = (this.upgraded && this.hasUpgradeDesc)?this.cardStrings.UPGRADE_DESCRIPTION:this.cardStrings.DESCRIPTION;
        this.rawDescription += cardStringsAbstract.EXTENDED_DESCRIPTION[0] + count + cardStringsAbstract.EXTENDED_DESCRIPTION[1];
        initializeDescription();*/
    }

    @Override
    public void onMoveToDiscard()
    {
        /*
        if(this.displayWill)
            this.rawDescription = (this.upgraded && this.hasUpgradeDesc)?this.cardStrings.UPGRADE_DESCRIPTION:this.cardStrings.DESCRIPTION;
        initializeDescription();*/
    }

    public void PreRelease()
    {
        /*if(AbstractDungeon.player instanceof TheVacant)
            ((TheVacant)AbstractDungeon.player).releasesThisCombat++;*/
    }

    public void PostRelease()
    {
        /*if(GetWill() > 0)
            addToTop(new ReducePowerAction(AbstractDungeon.player, AbstractDungeon.player, WillPower.POWER_ID, 1));*/
    }
}
