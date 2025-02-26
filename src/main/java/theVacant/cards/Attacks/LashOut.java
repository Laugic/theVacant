package theVacant.cards.Attacks;

import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.LoseHPAction;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.WeakPower;
import com.megacrit.cardcrawl.vfx.BorderFlashEffect;
import com.megacrit.cardcrawl.vfx.combat.HemokinesisEffect;
import theVacant.VacantMod;
import theVacant.cards.AbstractDynamicCard;
import theVacant.characters.TheVacant;

import static theVacant.VacantMod.makeCardPath;

public class LashOut extends AbstractDynamicCard
{

    public static final String ID = VacantMod.makeID(LashOut.class.getSimpleName());
    public static final String IMG = makeCardPath("LashOut.png");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);

    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final CardType TYPE = CardType.ATTACK;
    public static final CardColor COLOR = TheVacant.Enums.COLOR_GOLD;

    private static final int COST = 1;
    private static final int DAMAGE = 0;

    public LashOut()
    {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        baseDamage = DAMAGE;
        magicNumber = baseMagicNumber = 4;
        exhaust = true;
    }

    @Override
    public void use(AbstractPlayer player, AbstractMonster monster)
    {
        if(player.isBloodied)
            addToBot(new VFXAction(player, new BorderFlashEffect(new Color(0.35F, 0.0F, 0.0F, 1.0F)), 0.3F, true));
        addToBot(new VFXAction(new HemokinesisEffect(player.hb.cX, player.hb.cY, monster.hb.cX, monster.hb.cY), (float)player.currentHealth/(float)player.maxHealth));
        addToBot(new WaitAction(.1f));
        addToBot(new DamageAction(monster, new DamageInfo(player, this.damage, damageTypeForTurn),
                player.isBloodied?AbstractGameAction.AttackEffect.BLUNT_HEAVY:AbstractGameAction.AttackEffect.BLUNT_LIGHT));

        addToBot(new LoseHPAction(player, player, magicNumber));
        //addToBot(new ApplyPowerAction(player, player, new WeakPower(player, magicNumber, false), magicNumber));
    }

    @Override
    public void upgrade()
    {
        if (!upgraded)
        {
            upgradeName();
            upgradeMagicNumber(-1);
            exhaust = false;
            rawDescription = cardStrings.UPGRADE_DESCRIPTION;
            initializeDescription();
        }
    }
    @Override
    public void applyPowers()
    {
        getDamage();
        getDesc();
        super.applyPowers();
    }
    @Override
    public void atTurnStart()
    {
        getDamage();
        getDesc();
        super.atTurnStart();
    }

    private void getDamage()
    {
        AbstractPlayer player = AbstractDungeon.player;
        if(player != null)
            this.baseDamage = player.maxHealth - player.currentHealth;
        else
            this.baseDamage = 0;
//        if(upgraded)
//            baseDamage += 3;
    }

    private void getDesc()
    {
        rawDescription = (upgraded?cardStrings.UPGRADE_DESCRIPTION:cardStrings.DESCRIPTION) + cardStrings.EXTENDED_DESCRIPTION[0];
        initializeDescription();
    }

    @Override
    public void onMoveToDiscard()
    {
        rawDescription = upgraded?cardStrings.UPGRADE_DESCRIPTION:cardStrings.DESCRIPTION;
        initializeDescription();
    }
}