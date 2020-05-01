package theVacant.powers;

import basemod.interfaces.CloneablePowerInterface;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.common.LoseHPAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardQueueItem;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import theVacant.VacantMod;
import theVacant.util.TextureLoader;

public class EmptyFormPower extends AbstractPower implements CloneablePowerInterface
{
    public AbstractCreature source;

    public static final String POWER_ID = VacantMod.makeID(EmptyFormPower.class.getSimpleName());
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    private static final Texture tex84 = TextureLoader.getTexture("theVacantResources/images/powers/placeholder_power84.png");
    private static final Texture tex32 = TextureLoader.getTexture("theVacantResources/images/powers/placeholder_power32.png");

    public EmptyFormPower(final AbstractCreature owner, final AbstractCreature source, final int amount)
    {
        name = NAME;
        ID = POWER_ID;

        this.owner = owner;
        this.amount = amount;
        this.source = source;

        type = PowerType.DEBUFF;
        isTurnBased = false;

        this.region128 = new TextureAtlas.AtlasRegion(tex84, 0, 0, 84, 84);
        this.region48 = new TextureAtlas.AtlasRegion(tex32, 0, 0, 32, 32);

        updateDescription();
    }

    @Override
    public void onUseCard(AbstractCard card, UseCardAction action)
    {
        if(card.exhaust && !card.purgeOnUse && this.amount > 0)
        {
            flash();
            for(int i = 0; i < this.amount; i++)
            {
                AbstractMonster monster = null;
                if (action.target != null)
                    monster = (AbstractMonster)action.target;
                AbstractCard tmp = card.makeSameInstanceOf();
                AbstractDungeon.player.limbo.addToBottom(tmp);
                tmp.current_x = card.current_x;
                tmp.current_y = card.current_y;
                tmp.target_x = Settings.WIDTH / 2.0F - 300.0F * Settings.scale;
                tmp.target_y = Settings.HEIGHT / 2.0F;
                if (monster != null)
                    tmp.calculateCardDamage(monster);
                tmp.purgeOnUse = true;
                AbstractDungeon.actionManager.addCardQueueItem(new CardQueueItem(tmp, monster, card.energyOnUse, true, true), true);
            }
        }
    }

    @Override
    public void updateDescription()
    {
        if (this.amount == 1)
        {
            description = DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[1];
        }
        else if (this.amount > 1)
        {
            description = DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[2];
        }
    }

    @Override
    public AbstractPower makeCopy()
    {
        return new EmptyFormPower(owner, source, amount);
    }
}
