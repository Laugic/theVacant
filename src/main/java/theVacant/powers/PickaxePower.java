package theVacant.powers;

import basemod.interfaces.CloneablePowerInterface;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import theVacant.VacantMod;
import theVacant.actions.VacantMillAction;
import theVacant.util.TextureLoader;

import java.util.Random;

import static theVacant.Enums.VacantTags.GEMS;

public class PickaxePower extends AbstractPower implements CloneablePowerInterface
{
    public AbstractCreature source;

    public static final String POWER_ID = VacantMod.makeID(PickaxePower.class.getSimpleName());
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    private static final Texture tex84 = TextureLoader.getTexture("theVacantResources/images/powers/pickaxe84.png");
    private static final Texture tex32 = TextureLoader.getTexture("theVacantResources/images/powers/pickaxe32.png");

    public PickaxePower(final AbstractCreature owner, final AbstractCreature source, final int amount)
    {
        name = NAME;
        ID = POWER_ID;

        this.owner = owner;
        this.amount = amount;
        this.source = source;

        type = PowerType.BUFF;
        isTurnBased = false;

        this.region128 = new TextureAtlas.AtlasRegion(tex84, 0, 0, 84, 84);
        this.region48 = new TextureAtlas.AtlasRegion(tex32, 0, 0, 32, 32);

        updateDescription();
    }

    @Override
    public void atStartOfTurn()
    {
        for (int i = 0; i < amount; i++)
        {
            if(GEMS.size() > 0)
            {
                Random random = new Random();
                AbstractCard c = GEMS.get(AbstractDungeon.cardRandomRng.random(GEMS.size() - 1)).makeCopy();
                addToBot(new MakeTempCardInHandAction(c, true));
            }
        }
    }

    @Override
    public void updateDescription()
    {
        description = DESCRIPTIONS[0] + amount + (amount>1?DESCRIPTIONS[2]:DESCRIPTIONS[1]);
    }

    @Override
    public AbstractPower makeCopy()
    {
        return new PickaxePower(owner, source, amount);
    }
}
