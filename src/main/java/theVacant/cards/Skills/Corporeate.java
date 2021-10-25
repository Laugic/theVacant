package theVacant.cards.Skills;

import basemod.BaseMod;
import basemod.helpers.TooltipInfo;
import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.actions.defect.EvokeAllOrbsAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.DexterityPower;
import com.megacrit.cardcrawl.powers.StrengthPower;
import com.megacrit.cardcrawl.powers.watcher.VigorPower;
import com.megacrit.cardcrawl.vfx.BorderFlashEffect;
import com.megacrit.cardcrawl.vfx.combat.FlyingOrbEffect;
import com.megacrit.cardcrawl.vfx.combat.VerticalAuraEffect;
import theVacant.VacantMod;
import theVacant.actions.SyphonAction;
import theVacant.actions.VacantMillAction;
import theVacant.cards.AbstractDynamicCard;
import theVacant.characters.TheVacant;
import theVacant.powers.TemperancePower;
import theVacant.powers.VoidPower;
import theVacant.util.KeywordManager;

import java.util.ArrayList;
import java.util.List;

import static theVacant.VacantMod.makeCardPath;

public class Corporeate extends AbstractDynamicCard
{

    public static final String ID = VacantMod.makeID(Corporeate.class.getSimpleName());
    public static final String IMG = makeCardPath("Skill.png");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;

    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = TheVacant.Enums.COLOR_GOLD;


    private static final int COST = 3;

    public Corporeate()
    {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        exhaust = true;
    }

    @Override
    public void use(AbstractPlayer player, AbstractMonster monster)
    {
        addToBot(new EvokeAllOrbsAction());
        /*
        ArrayList<AbstractPower> PowersToRemove = new ArrayList<AbstractPower>();
        for (AbstractPower power: player.powers)
        {
            if(power.type == AbstractPower.PowerType.DEBUFF)
                PowersToRemove.add(power);
        }
        if(PowersToRemove != null && PowersToRemove.size() > 0)
        {
            for(AbstractPower power: PowersToRemove)
            {
                addToBot(new RemoveSpecificPowerAction(player, player, power));
                addToBot(new VFXAction(new FlyingOrbEffect(player.hb.cX, player.hb.cY)));
            }
        }*/
        /*
        if(player.hasPower(VigorPower.POWER_ID) && player.getPower(VigorPower.POWER_ID).amount > 0)
        {
            int vigorAmount = player.getPower(VigorPower.POWER_ID).amount;
            addToBot(new RemoveSpecificPowerAction(player, player, VigorPower.POWER_ID));
            addToBot(new ApplyPowerAction(player, player, new StrengthPower(player, vigorAmount), vigorAmount));
        }
        if(player.hasPower(TemperancePower.POWER_ID) && player.getPower(TemperancePower.POWER_ID).amount > 0)
        {
            int temperanceAmount = player.getPower(TemperancePower.POWER_ID).amount;
            addToBot(new RemoveSpecificPowerAction(player, player, TemperancePower.POWER_ID));
            addToBot(new ApplyPowerAction(player, player, new DexterityPower(player, temperanceAmount), temperanceAmount));
        }*/
    }

    @Override
    public void upgrade()
    {
        if (!upgraded)
        {
            upgradeName();
            exhaust = false;
            rawDescription = cardStrings.UPGRADE_DESCRIPTION;
            initializeDescription();
        }
    }
}