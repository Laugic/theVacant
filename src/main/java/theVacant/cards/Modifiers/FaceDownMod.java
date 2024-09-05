package theVacant.cards.Modifiers;

import basemod.abstracts.AbstractCardModifier;
import basemod.helpers.CardModifierManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.ArtifactPower;
import com.megacrit.cardcrawl.powers.watcher.VigorPower;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;
import theVacant.powers.TemperancePower;
import theVacant.powers.VoidEmbracePower;
import theVacant.powers.VoidPower;
import theVacant.util.TextureLoader;
import theVacant.vfx.DizzyVFX;

import java.util.ArrayList;
import java.util.Random;

import static basemod.helpers.CardModifierManager.addModifier;
import static basemod.helpers.CardModifierManager.getModifiers;
import static theVacant.VacantMod.makeID;
import static theVacant.VacantMod.modID;

public class FaceDownMod extends AbstractCardModifier
{
    private int amount;
    public static String ID = makeID(FaceDownMod.class.getSimpleName());

    public FaceDownMod()
    {
        priority = 100;
    }
    private float rotDir = 1, miniScale = .5f, rotSpeed, curAngle;

    @Override
    public String modifyDescription(String rawDescription, AbstractCard card) {
        if(card.isFlipped)
            return "";
        return rawDescription;
    }

    @Override
    public void onUse(AbstractCard card, AbstractCreature target, UseCardAction action) {
        card.isFlipped = false;
        card.initializeDescription();
    }

    @Override
    public void onInitialApplication(AbstractCard card) {
        Random random = new Random();
        rotDir = random.nextBoolean()?-1:1;
        rotSpeed = random.nextFloat();
        miniScale = .5f;
        curAngle = random.nextFloat() * 360f;
        AbstractCard newCard = card.makeCopy();
        newCard.uuid = card.uuid;
        newCard.isFlipped = false;
        AbstractDungeon.topLevelEffectsQueue.add(new DizzyVFX(newCard));
        card.isFlipped = true;
    }

    @Override
    public void onRemove(AbstractCard card) {
        card.isFlipped = false;
        for (AbstractGameEffect effect: AbstractDungeon.topLevelEffectsQueue)
            if(effect instanceof DizzyVFX && ((DizzyVFX) effect).card.uuid == card.uuid)
                effect.isDone = true;

        for (AbstractGameEffect effect: AbstractDungeon.topLevelEffects)
            if(effect instanceof DizzyVFX && ((DizzyVFX) effect).card.uuid == card.uuid)
                effect.isDone = true;

        for (AbstractGameEffect effect: AbstractDungeon.effectsQueue)
            if(effect instanceof DizzyVFX && ((DizzyVFX) effect).card.uuid == card.uuid)
                effect.isDone = true;

        for (AbstractGameEffect effect: AbstractDungeon.effectList)
            if(effect instanceof DizzyVFX && ((DizzyVFX) effect).card.uuid == card.uuid)
                effect.isDone = true;
    }

    @Override
    public boolean removeAtEndOfTurn(AbstractCard card) {
        return true;
    }

    @Override
    public boolean removeOnCardPlayed(AbstractCard card) {
        return true;
    }


    /*
        @Override
        public void onRender(AbstractCard card, SpriteBatch sb) {
            float prevX = card.current_x;
            float prevY = card.current_y;
            float prevScale = card.drawScale;
            float prevRotation = card.angle;
            card.current_y += 250;
            card.drawScale = miniScale;
            card.angle = curAngle;
            curAngle += rotSpeed * rotDir;
            card.isFlipped = false;

            card.initializeDescription();

            card.render(sb);

            card.current_x = prevX;
            card.current_y = prevY;
            card.drawScale = prevScale;
            card.angle = prevRotation;
            card.isFlipped = true;
            card.initializeDescription();
        }
    */
    @Override
    public String identifier(AbstractCard card)
    {
        return ID;
    }

    @Override
    public AbstractCardModifier makeCopy()
    {
        return new FaceDownMod();
    }
}