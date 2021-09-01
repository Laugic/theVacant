package theVacant.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import theVacant.powers.*;
import theVacant.relics.BoundSoul;

public class SwitchFormAction extends AbstractGameAction
{
    public int FORM_ID;
    public BoundSoul soul;
    public SwitchFormAction(int FORM_ID)
    {
        this.FORM_ID = FORM_ID;
        if(AbstractDungeon.player.hasRelic(BoundSoul.ID));
            soul = (BoundSoul) AbstractDungeon.player.getRelic(BoundSoul.ID);
    }

    @Override
    public void update()
    {
        if(soul == null)
        {
            isDone = true;
            return;
        }
        SwapForms(FORM_ID);
        isDone = true;
    }

    public void SwapForms(int swapToForm)
    {
        AbstractPlayer player = AbstractDungeon.player;
        if(player == null)
            return;
        if(player.hasPower(SolidSoulPower.POWER_ID) && player.getPower(SolidSoulPower.POWER_ID).amount > 0 && GetCurrentForm() != swapToForm)
        {
            player.getPower(SolidSoulPower.POWER_ID).reducePower(1);
            if(player.getPower(SolidSoulPower.POWER_ID).amount > 0)
                player.getPower(SolidSoulPower.POWER_ID).flash();
            else
                addToBot(new RemoveSpecificPowerAction(player, player, SolidSoulPower.POWER_ID));
            return;
        }
        if(swapToForm == BoundSoul.FURY_FORM && player.hasPower(VacantForm.POWER_ID))
            return;
        MakePowersMatchForm(swapToForm);
    }

    private void MakePowersMatchForm(int swapToForm)
    {
        AbstractPlayer player = AbstractDungeon.player;
        if(player == null)
            return;
        switch (swapToForm)
        {
            case BoundSoul.FURY_FORM:
                if(!player.hasPower(FuryForm.POWER_ID))
                {
                    addToTop(new RemoveSpecificPowerAction(player, player, SolemnForm.POWER_ID));
                    addToTop(new RemoveSpecificPowerAction(player, player, VacantForm.POWER_ID));
                    addToTop(new RemoveSpecificPowerAction(player, player, VoidForm.POWER_ID));
                    addToTop(new ApplyPowerAction(player, player, new FuryForm(player, player, 1)));
                    soul.flash();
                }
                break;
            case BoundSoul.VOID_FORM:
                if(!player.hasPower(VoidForm.POWER_ID))
                {
                    addToTop(new RemoveSpecificPowerAction(player, player, SolemnForm.POWER_ID));
                    addToTop(new RemoveSpecificPowerAction(player, player, VacantForm.POWER_ID));
                    addToTop(new RemoveSpecificPowerAction(player, player, FuryForm.POWER_ID));
                    addToTop(new ApplyPowerAction(player, player, new VoidForm(player, player, 1)));
                    soul.flash();
                }
                break;
            case BoundSoul.VACANT_FORM:
                if(!player.hasPower(VacantForm.POWER_ID))
                {
                    addToTop(new RemoveSpecificPowerAction(player, player, SolemnForm.POWER_ID));
                    addToTop(new RemoveSpecificPowerAction(player, player, FuryForm.POWER_ID));
                    addToTop(new RemoveSpecificPowerAction(player, player, VoidForm.POWER_ID));
                    addToTop(new ApplyPowerAction(player, player, new VacantForm(player, player, 1)));
                    soul.flash();
                }
                break;
            default:
                if(!player.hasPower(SolemnForm.POWER_ID))
                {
                    addToTop(new RemoveSpecificPowerAction(player, player, FuryForm.POWER_ID));
                    addToTop(new RemoveSpecificPowerAction(player, player, VacantForm.POWER_ID));
                    addToTop(new RemoveSpecificPowerAction(player, player, VoidForm.POWER_ID));
                    addToTop(new ApplyPowerAction(player, player, new SolemnForm(player, player, 1)));
                    soul.flash();
                }
                break;
        }
    }

    private int GetCurrentForm()
    {
        AbstractPlayer player = AbstractDungeon.player;
        if(player.hasPower(VacantForm.POWER_ID))
            return BoundSoul.VACANT_FORM;
        if(player.hasPower(FuryForm.POWER_ID))
            return BoundSoul.FURY_FORM;
        if(player.hasPower(VoidForm.POWER_ID))
            return BoundSoul.VOID_FORM;
        return BoundSoul.SOLEMN_FORM;
    }
}