package theVacant.powers;

import basemod.interfaces.CloneablePowerInterface;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.evacipated.cardcrawl.mod.stslib.powers.interfaces.BetterOnApplyPowerPower;
import com.evacipated.cardcrawl.mod.stslib.powers.interfaces.OnReceivePowerPower;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.orbs.AbstractOrb;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.ArtifactPower;
import com.megacrit.cardcrawl.powers.BlurPower;
import com.megacrit.cardcrawl.powers.watcher.VigorPower;
import theVacant.VacantMod;
import theVacant.actions.VacantMillAction;
import theVacant.orbs.AbstractGemOrb;
import theVacant.util.TextureLoader;

import java.util.ArrayList;

import static theVacant.cards.AbstractVacantCard.getHollow;

public class AbyssPower extends AbstractPower implements CloneablePowerInterface, OnReceivePowerPower, BetterOnApplyPowerPower
{
    public AbstractCreature source;

    public static final String POWER_ID = VacantMod.makeID(AbyssPower.class.getSimpleName());
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    private static final Texture tex84 = TextureLoader.getTexture("theVacantResources/images/powers/abyss_power84.png");
    private static final Texture tex32 = TextureLoader.getTexture("theVacantResources/images/powers/abyss_power32.png");

    //public static boolean gainedBlock = false;

    private ArrayList<AbstractPower> alreadyHit;
    public AbyssPower(final AbstractCreature owner, final AbstractCreature source, final int amount)
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
        //gainedBlock= false;
        alreadyHit = new ArrayList<>();
        updateDescription();
    }

    @Override
    public void atStartOfTurn() {
        alreadyHit.clear();
    }

    @Override
    public void updateDescription()
    {
        description = DESCRIPTIONS[0] + amount + DESCRIPTIONS[1];
    }

    @Override
    public AbstractPower makeCopy()
    {
        return new AbyssPower(owner, source, amount);
    }

    @Override
    public boolean betterOnApplyPower(AbstractPower power, AbstractCreature target, AbstractCreature abstractCreature1) {
        if(power.type == PowerType.DEBUFF && !target.hasPower(ArtifactPower.POWER_ID) && !alreadyHit.contains(power)){
            alreadyHit.add(power);
            addToTop(new ApplyPowerAction(owner, owner, new TemperancePower(owner, owner, amount),amount));
        }
        return true;
    }

    @Override
    public boolean onReceivePower(AbstractPower power, AbstractCreature target, AbstractCreature abstractCreature1) {
        if(power.type == PowerType.DEBUFF && !target.hasPower(ArtifactPower.POWER_ID) && !alreadyHit.contains(power)){
            alreadyHit.add(power);
            addToTop(new ApplyPowerAction(owner, owner, new TemperancePower(owner, owner, amount),amount));
        }
        return true;
    }
}
