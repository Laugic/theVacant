package theVacant.powers;

import basemod.interfaces.CloneablePowerInterface;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import theVacant.VacantMod;
import theVacant.util.TextureLoader;

public class ShroudPower extends AbstractPower implements CloneablePowerInterface
{
    public AbstractCreature source;

    public static final String POWER_ID = VacantMod.makeID(ShroudPower.class.getSimpleName());
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    private static final Texture tex84 = TextureLoader.getTexture("theVacantResources/images/powers/shroud84.png");
    private static final Texture tex32 = TextureLoader.getTexture("theVacantResources/images/powers/shroud32.png");

    public ShroudPower(final AbstractCreature owner, final AbstractCreature source, final int amount)
    {
        name = NAME;
        ID = POWER_ID;

        this.owner = owner;
        this.amount = amount;
        this.source = source;

        type = PowerType.BUFF;
        isTurnBased = true;

        this.region128 = new TextureAtlas.AtlasRegion(tex84, 0, 0, 84, 84);
        this.region48 = new TextureAtlas.AtlasRegion(tex32, 0, 0, 32, 32);

        updateDescription();
    }

    @Override
    public void atStartOfTurn()
    {
        flash();
        //CheckGloom();
        addToBot(new ReducePowerAction(owner, owner, this, 1));
    }

    @Override
    public void onUseCard(AbstractCard card, UseCardAction action)
    {
        if (card.type == AbstractCard.CardType.ATTACK)
        {
            flash();
            //CheckGloom();
            addToBot(new ReducePowerAction(owner, owner, this, 1));
        }
    }
/*
    private void CheckGloom()
    {
        if(owner.hasPower(GloomPower.POWER_ID) && owner.getPower(GloomPower.POWER_ID).amount > 0)
        {
            if (!AbstractDungeon.getMonsters().areMonstersBasicallyDead())
            {
                flash();
                addToBot(new DamageAllEnemiesAction(null,
                        DamageInfo.createDamageMatrix(owner.getPower(GloomPower.POWER_ID).amount, true), DamageInfo.DamageType.THORNS, AbstractGameAction.AttackEffect.LIGHTNING));
            }
        }
    }*/
    @Override
    public float atDamageReceive(float damage, DamageInfo.DamageType type)
    {
        if (type == DamageInfo.DamageType.NORMAL)
            return damage * 0.5F;
        return damage;
    }
/*
    @Override
    public int onAttacked(DamageInfo info, int damageAmount)
    {
        if(info.type != DamageInfo.DamageType.NORMAL)
        {
            if(damageAmount % 2 == 1)
                damageAmount--;
            return damageAmount / 2;
        }
        return damageAmount;
    }*/

    @Override
    public void updateDescription()
    {
        description = DESCRIPTIONS[0];
    }

    @Override
    public AbstractPower makeCopy()
    {
        return new ShroudPower(owner, source, amount);
    }
}
