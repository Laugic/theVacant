package theVacant.cards;

import basemod.abstracts.CustomCard;
import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import theVacant.VacantMod;
import theVacant.powers.BurdenBreakPower;
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
    public boolean checkWounded;
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
        checkWounded = false;
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
    public void triggerOnGlowCheck() {
        if(checkWounded)
        {
            if(getWounded())
                glowColor = Color.PURPLE;
            else
                glowColor = AbstractCard.BLUE_BORDER_GLOW_COLOR;
        }
    }

    public static boolean getWounded()
    {
        if(AbstractDungeon.player != null)
        {
            int numDebuffs = 0;
            for (AbstractPower power: AbstractDungeon.player.powers) {
                if(power.type == AbstractPower.PowerType.DEBUFF)
                    numDebuffs++;
            }
            if(AbstractDungeon.player.isBloodied || numDebuffs >= 2)
                return true;
        }
        return false;
    }
}
