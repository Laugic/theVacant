package theVacant.cards.Skills;

import basemod.BaseMod;
import basemod.helpers.TooltipInfo;
import basemod.interfaces.CloneablePowerInterface;
import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.vfx.BorderFlashEffect;
import com.megacrit.cardcrawl.vfx.combat.VerticalAuraEffect;
import theVacant.VacantMod;
import theVacant.actions.EnhanceInHandAction;
import theVacant.actions.SyphonAction;
import theVacant.actions.VacantMillAction;
import theVacant.cards.AbstractDynamicCard;
import theVacant.cards.Modifiers.MaterializeModifier;
import theVacant.characters.TheVacant;
import theVacant.powers.VoidPower;
import theVacant.util.KeywordManager;

import java.util.ArrayList;
import java.util.List;

import static theVacant.VacantMod.makeCardPath;

public class EssenceOfBismuth extends AbstractDynamicCard
{

    public static final String ID = VacantMod.makeID(EssenceOfBismuth.class.getSimpleName());
    public static final String IMG = makeCardPath("Bismuth.png");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;

    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = TheVacant.Enums.COLOR_GOLD;


    private static final int COST = 1;

    public EssenceOfBismuth()
    {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        exhaust = true;
        isUnnate = true;
    }

    @Override
    public void use(AbstractPlayer player, AbstractMonster monster)
    {
        for (AbstractMonster mo : (AbstractDungeon.getCurrRoom()).monsters.monsters)
        {
            for(AbstractPower power: mo.powers)
            {
                if(power.type == AbstractPower.PowerType.DEBUFF && !VacantMod.IMMUNE_POWERS.contains(power.ID))
                {
                    if(power instanceof CloneablePowerInterface)
                    {
                        AbstractPower newPow = ((CloneablePowerInterface) power).makeCopy();
                        if(upgraded)
                            newPow.amount *= 2;
                        addToBot(new ApplyPowerAction(mo, player, newPow));
                    }
                    else{
                        addToBot(new ApplyPowerAction(mo, player, power));
                        if(upgraded)
                            addToBot(new ApplyPowerAction(mo, player, power));
                    }
                }
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