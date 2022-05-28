package theVacant.cards.Skills;

import com.badlogic.gdx.math.MathUtils;
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
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import theVacant.VacantMod;
import theVacant.actions.ChipOrbAction;
import theVacant.cards.AbstractDynamicCard;
import theVacant.characters.TheVacant;
import theVacant.orbs.AbstractGemOrb;

import java.util.Iterator;

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
        damage = baseDamage = magicNumber = baseMagicNumber = 5;
        isMultiDamage = true;
    }

    @Override
    public void use(AbstractPlayer player, AbstractMonster monster)
    {
        AbstractDungeon.actionManager.addToBottom(new SFXAction("theVacant:awman"));
        if(upgraded)
        {
            addToBot(new DamageAction(player, new DamageInfo(player, magicNumber, DamageInfo.DamageType.THORNS), AbstractGameAction.AttackEffect.FIRE));

            //addToBot(new DamageAllEnemiesAction(player, multiDamage, DamageInfo.DamageType.THORNS, AbstractGameAction.AttackEffect.FIRE));
            AbstractDungeon.actionManager.addToBottom(new DamageAllEnemiesAction(player, DamageInfo.createDamageMatrix(magicNumber, true), DamageInfo.DamageType.THORNS, AbstractGameAction.AttackEffect.FIRE));
        }
        else
            addToBot(new DamageAction(player, new DamageInfo(player, magicNumber, DamageInfo.DamageType.THORNS), AbstractGameAction.AttackEffect.FIRE));

        if(player.orbs.size() > 0)
        {
            for (AbstractOrb gem : player.orbs)
            {
                if(gem instanceof AbstractGemOrb)
                    addToBot(new ChipOrbAction(gem, 1));
            }
        }
    }
/*
    @Override
    public void calculateDamageDisplay(AbstractMonster mo)
    {
        applyPowers();
        this.calculateCardDamage(mo);
    }

    @Override
    public void applyPowers()
    {
        magicNumber = GetCardDamage();
        initializeDescription();
    }
*/
    @Override
    public void upgrade()
    {
        if (!upgraded)
        {
            upgradeName();
            target = CardTarget.ALL;
            rawDescription = cardStrings.UPGRADE_DESCRIPTION;
            initializeDescription();
        }
    }

    private int GetCardDamage()
    {
        AbstractPlayer player = AbstractDungeon.player;
        float tmp = (float)baseMagicNumber;// 3232
        Iterator dmgIterator = player.relics.iterator();// 3235

        while(dmgIterator.hasNext()) {
            AbstractRelic r = (AbstractRelic)dmgIterator.next();
            tmp = r.atDamageModify(tmp, this);// 3236
            if (baseMagicNumber != (int)tmp) {// 3237
                this.isMagicNumberModified = true;// 3238
            }
        }

        AbstractPower p;
        for(dmgIterator = player.powers.iterator(); dmgIterator.hasNext(); tmp = p.atDamageGive(tmp, this.damageTypeForTurn, this)) {// 3244 3245
            p = (AbstractPower)dmgIterator.next();
        }

        tmp = player.stance.atDamageGive(tmp, this.damageTypeForTurn, this);// 3249
        if (baseMagicNumber != (int)tmp) {// 3250
            this.isMagicNumberModified = true;// 3251
        }

        for(dmgIterator = player.powers.iterator(); dmgIterator.hasNext(); tmp = p.atDamageFinalGive(tmp, this.damageTypeForTurn, this)) {// 3260 3261
            p = (AbstractPower)dmgIterator.next();
        }

        if (tmp < 0.0F) {// 3270
            tmp = 0.0F;// 3271
        }

        if (baseMagicNumber != MathUtils.floor(tmp))
            isMagicNumberModified = true;

        return MathUtils.floor(tmp);
    }
}