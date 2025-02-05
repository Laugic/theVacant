package theVacant.powers;

import basemod.interfaces.CloneablePowerInterface;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.evacipated.cardcrawl.mod.stslib.powers.interfaces.OnPlayerDeathPower;
import com.megacrit.cardcrawl.actions.common.LoseHPAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.FocusPower;
import theVacant.VacantMod;
import theVacant.actions.EnhanceRandomInHandAction;
import theVacant.actions.VacantMillAction;
import theVacant.cards.Modifiers.SoulforgedModifier;
import theVacant.cards.Modifiers.VoidboundModifier;
import theVacant.util.TextureLoader;

public class VacantForm extends FormPower implements CloneablePowerInterface, OnPlayerDeathPower
{
    public AbstractCreature source;

    public static final String POWER_ID = VacantMod.makeID(VacantForm.class.getSimpleName());
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    private static final Texture tex84 = TextureLoader.getTexture("theVacantResources/images/powers/vacant_form84.png");
    private static final Texture tex32 = TextureLoader.getTexture("theVacantResources/images/powers/vacant_form32.png");

    public VacantForm(final AbstractCreature owner, final AbstractCreature source, final int amount)
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
    public void onRemove()
    {
        Syphon();
    }

    @Override
    public void updateDescription()
    {
        description = DESCRIPTIONS[0] + GetHPAmount() + DESCRIPTIONS[1];
    }

    private int GetHPAmount()
    {
        AbstractPlayer player = AbstractDungeon.player;
        int HP = 1;
        if(player.hasPower(FocusPower.POWER_ID))
            HP += player.getPower(FocusPower.POWER_ID).amount;
        amount = HP;
        return HP;
    }
    @Override
    public AbstractPower makeCopy()
    {
        return new VacantForm(owner, source, amount);
    }

    @Override
    public boolean onPlayerDeath(AbstractPlayer abstractPlayer, DamageInfo damageInfo)
    {
        abstractPlayer.heal(1);
        return false;
    }

    @Override
    public void Syphon()
    {
        addToBot(new LoseHPAction(owner, owner, amount));
    }
}
