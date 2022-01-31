package theVacant.relics;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import com.megacrit.cardcrawl.rooms.AbstractRoom;
import theVacant.VacantMod;
import theVacant.actions.SwitchFormAction;
import theVacant.actions.SyphonAction;
import theVacant.powers.*;
import theVacant.util.TextureLoader;
import static theVacant.VacantMod.makeRelicOutlinePath;
import static theVacant.VacantMod.makeRelicPath;

public class BoundSoulOld extends CustomRelic
{

    public static final String ID = VacantMod.makeID(BoundSoulOld.class.getSimpleName());

    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath("bound_soul.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(makeRelicOutlinePath("bound_soul.png"));
    public static final int SOLEMN_FORM = 0, FURY_FORM = 1, VOID_FORM = 2, VACANT_FORM = 3;
    public static int SOUL_FORM;

    public boolean inBattle = false;
    private boolean drawPileConfirmedEmpty = false;
    private boolean turnStart = false;

    public BoundSoulOld()
    {
        super(ID, IMG, OUTLINE, RelicTier.SPECIAL, LandingSound.CLINK);
        SOUL_FORM = SOLEMN_FORM;
    }

    @Override
    public void atPreBattle()
    {
        inBattle = false;
    }

    @Override
    public void atTurnStartPostDraw()
    {
        inBattle = true;
        drawPileConfirmedEmpty = false;
        turnStart = true;
    }

    @Override
    public void onUseCard(AbstractCard targetCard, UseCardAction useCardAction)
    {
        drawPileConfirmedEmpty = false;
    }

    @Override
    public void onRefreshHand()
    {
        AbstractPlayer player = AbstractDungeon.player;
        if (AbstractDungeon.actionManager.actions.isEmpty())
        {
            if (AbstractDungeon.player.drawPile.isEmpty() && !AbstractDungeon.actionManager.turnHasEnded
                    && inBattle && !AbstractDungeon.isScreenUp && (AbstractDungeon.getCurrRoom()).phase == AbstractRoom.RoomPhase.COMBAT && !drawPileConfirmedEmpty)
            {
                if(!AbstractDungeon.player.hasPower(VoidForm.POWER_ID) )
                {
                    flash();
                    addToTop(new RelicAboveCreatureAction(AbstractDungeon.player, this));
                    drawPileConfirmedEmpty = true;
                    addToBot(new SwitchFormAction(VOID_FORM));
                }
            }
            else if (turnStart)
            {
                if (!player.isBloodied)
                    addToBot(new SwitchFormAction(SOLEMN_FORM));
                else if (player.hasPower(FuryForm.POWER_ID))
                    addToBot(new SyphonAction(1));
            }
            turnStart = false;
        }
    }

    @Override
    public void onLoseHp(int damageAmount)
    {
        addToBot(new SwitchFormAction(FURY_FORM));
    }

    public int GetCurrentForm()
    {
        AbstractPlayer player = AbstractDungeon.player;
        if(player.hasPower(FuryForm.POWER_ID))
            return FURY_FORM;
        if(player.hasPower(VoidForm.POWER_ID))
            return VOID_FORM;
        if(player.hasPower(VacantForm.POWER_ID))
            return VACANT_FORM;
        return SOLEMN_FORM;
    }

    @Override
    public String getUpdatedDescription()
    {

        return DESCRIPTIONS[0];
    }
}