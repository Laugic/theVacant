package theVacant.cards.Attacks;

import basemod.BaseMod;
import basemod.helpers.TooltipInfo;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.RitualPower;
import com.megacrit.cardcrawl.powers.VulnerablePower;
import com.megacrit.cardcrawl.vfx.combat.LightningEffect;
import theVacant.VacantMod;
import theVacant.actions.TempHPGainFromDamageAction;
import theVacant.cards.AbstractDynamicCard;
import theVacant.characters.TheVacant;
import theVacant.powers.VoidPower;
import theVacant.util.KeywordManager;

import java.util.ArrayList;
import java.util.List;

import static theVacant.VacantMod.makeCardPath;

public class StealSoul extends AbstractDynamicCard {

    public static final String ID = VacantMod.makeID(StealSoul.class.getSimpleName());
    public static final String IMG = makeCardPath("ReapSoul.png");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);

    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final CardType TYPE = CardType.ATTACK;
    public static final CardColor COLOR = TheVacant.Enums.COLOR_GOLD;

    private static final int COST = 2;
    private static final int DAMAGE = 10;

    public StealSoul()
    {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        damage = baseDamage = DAMAGE;
        exhaust = true;
    }

    @Override
    public void use(AbstractPlayer player, AbstractMonster monster)
    {
        AbstractDungeon.effectsQueue.add(new LightningEffect(monster.hb.cX, monster.hb.cY));
        addToBot( new TempHPGainFromDamageAction(player, monster, damage, damageTypeForTurn, AbstractGameAction.AttackEffect.NONE));
//        for(AbstractPower power: monster.powers)
//        {
//            if(power.type == AbstractPower.PowerType.BUFF)
//            {
//                power.owner = player;
//                addToBot(new RemoveSpecificPowerAction(monster, player, power));
//                if(power instanceof RitualPower)
//                    addToBot(new ApplyPowerAction(player, player, new RitualPower(player, power.amount, true)));
//                else
//                    addToBot(new ApplyPowerAction(player, player, power));
//            }
//        }
//        if(upgraded)
//        {
//            for(AbstractPower power: player.powers)
//            {
//                if(power.type == AbstractPower.PowerType.DEBUFF)
//                {
//                    power.owner = monster;
//                    addToBot(new RemoveSpecificPowerAction(player, monster, power));
//                    addToBot(new ApplyPowerAction(monster, player, power));
//                }
//            }
//        }
    }

    @Override
    public void upgrade()
    {
        if (!upgraded)
        {
            upgradeName();
            upgradeDamage(4);
            upgradedDamage = true;
            initializeDescription();
        }
    }
}