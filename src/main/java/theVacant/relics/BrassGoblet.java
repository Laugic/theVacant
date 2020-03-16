package theVacant.relics;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.DexterityPower;
import com.megacrit.cardcrawl.powers.StrengthPower;
import com.megacrit.cardcrawl.relics.RedSkull;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import theVacant.VacantMod;
import theVacant.util.TextureLoader;
import static theVacant.VacantMod.makeRelicOutlinePath;
import static theVacant.VacantMod.makeRelicPath;

public class BrassGoblet extends CustomRelic
{

    public static final String ID = VacantMod.makeID(BrassGoblet.class.getSimpleName());

    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath("brass_goblet.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(makeRelicOutlinePath("brass_goblet.png"));

    public static final int HEALTHY_DEX = 1;
    public static final int WOUNDED_STRENGTH = 2;
    public boolean isActive = false;
    public boolean isWounded = false;
    public boolean isHealthy = false;

    public BrassGoblet() {
        super(ID, IMG, OUTLINE, RelicTier.STARTER, LandingSound.CLINK);
    }

    @Override
    public void atBattleStart()
    {
        this.isActive = false;
        AbstractDungeon.actionManager.addToBottom(new AbstractGameAction()
        {
            public void update()
            {
                if (!BrassGoblet.this.isActive)
                {
                    BrassGoblet.this.flash();
                    if(AbstractDungeon.player.isBloodied)
                    {
                        BrassGoblet.this.isWounded = true;
                        AbstractDungeon.player.addPower(new StrengthPower(AbstractDungeon.player, WOUNDED_STRENGTH));
                    }
                    else
                    {
                        BrassGoblet.this.isHealthy = true;
                        AbstractDungeon.player.addPower(new DexterityPower(AbstractDungeon.player, HEALTHY_DEX));
                    }
                    addToTop(new RelicAboveCreatureAction(AbstractDungeon.player, BrassGoblet.this));
                    BrassGoblet.this.isActive = true;
                    AbstractDungeon.onModifyPower();
                }
                this.isDone = true;
            }
        });
    }

    @Override
    public void onBloodied()
    {
        flash();
        if (!this.isWounded && (AbstractDungeon.getCurrRoom()).phase == AbstractRoom.RoomPhase.COMBAT)
        {
            AbstractPlayer player = AbstractDungeon.player;
            AbstractDungeon.actionManager.addToTop(new ApplyPowerAction(player, player, new StrengthPower(player, WOUNDED_STRENGTH), WOUNDED_STRENGTH));
            if(this.isHealthy)
                AbstractDungeon.actionManager.addToTop(new ApplyPowerAction(player, player, new DexterityPower(player, -HEALTHY_DEX), -HEALTHY_DEX));
            AbstractDungeon.actionManager.addToTop((new RelicAboveCreatureAction(AbstractDungeon.player, this)));
            this.isWounded = true;
            this.isHealthy = false;
            AbstractDungeon.player.hand.applyPowers();
        }
    }

    @Override
    public void onNotBloodied()
    {
        flash();
        if (!this.isHealthy && (AbstractDungeon.getCurrRoom()).phase == AbstractRoom.RoomPhase.COMBAT)
        {
            AbstractPlayer player = AbstractDungeon.player;
            if(this.isWounded)
                AbstractDungeon.actionManager.addToTop(new ApplyPowerAction(player, player, new StrengthPower(player, -WOUNDED_STRENGTH), -WOUNDED_STRENGTH));
            AbstractDungeon.actionManager.addToTop(new ApplyPowerAction(player, player, new DexterityPower(player, HEALTHY_DEX), HEALTHY_DEX));
            AbstractDungeon.actionManager.addToTop((new RelicAboveCreatureAction(AbstractDungeon.player, this)));
            this.isWounded = false;
            this.isHealthy = true;
            AbstractDungeon.player.hand.applyPowers();
        }
        AbstractDungeon.player.hand.applyPowers();
    }

    @Override
    public void onVictory()
    {
        this.isActive = false;
        this.isWounded = false;
        this.isHealthy = false;
    }

    @Override
    public String getUpdatedDescription()
    {
        return DESCRIPTIONS[0];
    }
}