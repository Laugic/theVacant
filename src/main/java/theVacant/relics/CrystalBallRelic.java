package theVacant.relics;

import basemod.abstracts.CustomRelic;
import basemod.cardmods.EtherealMod;
import basemod.cardmods.ExhaustMod;
import basemod.helpers.CardModifierManager;
import com.badlogic.gdx.graphics.Texture;
import com.evacipated.cardcrawl.mod.stslib.actions.tempHp.AddTemporaryHPAction;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import theVacant.VacantMod;
import theVacant.actions.EnhanceInPileAction;
import theVacant.actions.VacantMillAction;
import theVacant.cards.AbstractVacantCard;
import theVacant.cards.Modifiers.MaterializeModifier;
import theVacant.powers.DoomPower;
import theVacant.powers.ShroudPower;
import theVacant.powers.VoidPower;
import theVacant.util.TextureLoader;
import static theVacant.VacantMod.makeRelicOutlinePath;
import static theVacant.VacantMod.makeRelicPath;

public class CrystalBallRelic extends CustomRelic
{

    public static final String ID = VacantMod.makeID(CrystalBallRelic.class.getSimpleName());

    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath("crystal_ball_relic.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(makeRelicOutlinePath("crystal_ball_relic.png"));

    public boolean copied;

    public CrystalBallRelic() {
        super(ID, IMG, OUTLINE, RelicTier.SHOP, LandingSound.MAGICAL);
        copied = false;
    }

    @Override
    public void atTurnStart() {
        beginLongPulse();
        copied = false;
    }

    @Override
    public void onUseCard(AbstractCard targetCard, UseCardAction useCardAction) {
        if(!targetCard.purgeOnUse && !copied)
        {
            addToBot(new RelicAboveCreatureAction(AbstractDungeon.player, this));
            flash();
            AbstractCard copy = targetCard.makeStatEquivalentCopy();
            CardModifierManager.addModifier(copy, new EtherealMod());
            if(!copy.exhaust && copy.type != AbstractCard.CardType.POWER)
                CardModifierManager.addModifier(copy, new ExhaustMod());
            addToTop(new MakeTempCardInHandAction(copy));
            stopPulse();
            copied = true;
        }
    }

/*
    @Override
    public void onExhaust(AbstractCard card)
    {
        flash();
        MaterializeModifier.Enhance(card, MATERIALIZE_AMOUNT);
        card.initializeDescription();
        card.applyPowers();
    }*/

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }
}