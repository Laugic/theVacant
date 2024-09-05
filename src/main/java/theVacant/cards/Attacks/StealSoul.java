package theVacant.cards.Attacks;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.ArtifactPower;
import com.megacrit.cardcrawl.powers.StrengthPower;
import com.megacrit.cardcrawl.relics.ChemicalX;
import com.megacrit.cardcrawl.ui.panels.EnergyPanel;
import com.megacrit.cardcrawl.vfx.combat.FlyingOrbEffect;
import com.megacrit.cardcrawl.vfx.combat.InflameEffect;
import com.megacrit.cardcrawl.vfx.combat.LightningEffect;
import theVacant.VacantMod;
import theVacant.actions.StealSoulAction;
import theVacant.actions.TempHPGainFromDamageAction;
import theVacant.cards.AbstractDynamicCard;
import theVacant.characters.TheVacant;
import theVacant.powers.AntifactPower;

import static theVacant.VacantMod.makeCardPath;

public class StealSoul extends AbstractDynamicCard
{
    public static final String ID = VacantMod.makeID(StealSoul.class.getSimpleName());
    public static final String IMG = makeCardPath("ReapSoul.png");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);

    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final CardType TYPE = CardType.ATTACK;
    public static final CardColor COLOR = TheVacant.Enums.COLOR_GOLD;

    private static final int COST = -1;
    private static final int DAMAGE = 7;

    public StealSoul()
    {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        damage = baseDamage = DAMAGE;
        magicNumber = baseMagicNumber = 0;
        exhaust = true;
    }

    @Override
    public void use(AbstractPlayer player, AbstractMonster monster)
    {
        AbstractDungeon.effectsQueue.add(new LightningEffect(monster.hb.cX, monster.hb.cY));
        CardCrawlGame.sound.playA("ORB_LIGHTNING_EVOKE", 0.9F);
        addToBot(new DamageAction(monster, new DamageInfo(player, damage, damageTypeForTurn), AbstractGameAction.AttackEffect.NONE));


        int effect = this.energyOnUse;
        effect += magicNumber;
        if (player.hasRelic("Chemical X")) {
            effect += ChemicalX.BOOST;
            player.getRelic("Chemical X").flash();
        }

        if(effect > 0)
            addToBot(new StealSoulAction(monster, effect));

        if (!this.freeToPlayOnce) {
            player.energy.use(EnergyPanel.totalCount);
        }

        /*if(monster.hasPower(ArtifactPower.POWER_ID))
        {
            int artNum = monster.getPower(ArtifactPower.POWER_ID).amount;
            addToBot(new RemoveSpecificPowerAction(monster, player, monster.getPower(ArtifactPower.POWER_ID)));
            for (int j = 0; j < 5; j++)
                addToBot(new VFXAction(new FlyingOrbEffect(monster.hb.cX, monster.hb.cY)));
            addToBot(new VFXAction(player, new InflameEffect(player), 1.0F));
            addToBot(new ApplyPowerAction(player, player, new ArtifactPower(player, artNum)));
        }
        if(monster.hasPower(StrengthPower.POWER_ID))
        {
            int strNum = monster.getPower(StrengthPower.POWER_ID).amount;
            if(strNum > 0)
            {
                addToBot(new RemoveSpecificPowerAction(monster, player, monster.getPower(StrengthPower.POWER_ID)));
                for (int j = 0; j < 5; j++)
                    addToBot(new VFXAction(new FlyingOrbEffect(monster.hb.cX, monster.hb.cY)));
                addToBot(new VFXAction(player, new InflameEffect(player), 1.0F));
                addToBot(new ApplyPowerAction(player, player, new StrengthPower(player, strNum)));
            }
        }*/
//        if(upgraded)
//            addToBot(new ApplyPowerAction(monster, player, new AntifactPower(monster, player, magicNumber), magicNumber));
        /*
        if(getWounded())
            addToBot( new TempHPGainFromDamageAction(player, monster, damage, damageTypeForTurn, AbstractGameAction.AttackEffect.NONE));
        else
            addToBot(new DamageAction(monster, new DamageInfo(player, damage, damageTypeForTurn), AbstractGameAction.AttackEffect.NONE));*/
    }

    @Override
    public void upgrade()
    {
        if (!upgraded)
        {
            upgradeName();
            upgradeDamage(4);
            initializeDescription();
        }
    }
}