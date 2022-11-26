package theVacant.actions;

import basemod.ReflectionHacks;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.input.InputHelper;
import com.megacrit.cardcrawl.orbs.AbstractOrb;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;
import com.megacrit.cardcrawl.vfx.cardManip.ExhaustCardEffect;
import theVacant.orbs.AbstractGemOrb;
import theVacant.util.TextureLoader;
import theVacant.vfx.ParticleEffect;
import theVacant.vfx.ShowCardAndExhumeEffect;

import java.util.ArrayList;
import java.util.Random;

public class DimensionTearAction  extends AbstractGameAction
{
    private static Texture texture =  TextureLoader.getTexture("theVacantResources/images/particles/DimensionTear.png");
    ParticleEffect particle;
    int damage;
    AbstractCard card;
    public DimensionTearAction(AbstractCard card, AbstractCreature target, AbstractCreature source, int damage, int repeats)
    {
        this.target = target;
        this.source = source;
        this.damage = damage;
        this.card = card;
        duration = startDuration = Settings.ACTION_DUR_FAST;
        amount = repeats;
        int xOffset = 0;
        int yOffset = 0;
        if(amount == 2)
        {
            xOffset = texture.getWidth() / 4;
            //yOffset = texture.getWidth() / 4;
        }
        if(amount == 1)
        {
            xOffset = -texture.getWidth() / 4;
            //yOffset = texture.getWidth() / 4;
        }
        particle = new ParticleEffect(target.hb.cX + xOffset, target.hb.cY + texture.getWidth() / 4 + yOffset, texture, 12, .3f);
        if(amount == 2)
            particle.setRotation(-10);
        if(amount == 1)
            particle.setRotation(10);
    }

    @Override
    public void update()
    {
        if (duration == startDuration)
        {
            AbstractDungeon.effectsQueue.add(particle);
            //AbstractDungeon.actionManager.addToTop(new SFXAction("ATTACK_HEAVY"));
            duration -= .01;
        }
        if(!AbstractDungeon.effectsQueue.contains(particle) || particle.duration <= particle.startingDuration / 2)
        {
            duration -= Gdx.graphics.getDeltaTime();
        }
        if(duration <= 0)
        {
            if(amount > 1){
                Exhume();
                addToTop(new DimensionTearAction(card, target, source, damage, amount - 1));
            }
            else
            {
                Exhume();
                //addToBot(new BetterExhaustPileToHandAction(99999,false));
            }
            addToTop( new DamageAction(target, new DamageInfo(source, damage, DamageInfo.DamageType.NORMAL), AttackEffect.NONE));
            CardCrawlGame.sound.playA("ORB_LIGHTNING_EVOKE", 0.9F);
            this.isDone = true;
        }
    }

    private void Exhume()
    {
        ArrayList<AbstractCard> exhumableCards = new ArrayList<>();
        for (AbstractCard cardToAdd: AbstractDungeon.player.exhaustPile.group)
        {
            if(card != cardToAdd)
                exhumableCards.add(cardToAdd);
        }

        Random random = new Random();
        int numCards;

        if(amount == 3)
            numCards = Math.round(AbstractDungeon.player.exhaustPile.size() / 3.0f);
        else if(amount == 2)
            numCards = Math.round(AbstractDungeon.player.exhaustPile.size() / 2.0f);
        else
            numCards = AbstractDungeon.player.exhaustPile.size();

        for (int i = 0; i < numCards; i++) {
            AbstractCard pickedCard = exhumableCards.get(random.nextInt(exhumableCards.size()));
            exhumableCards.remove(pickedCard);
            AbstractDungeon.player.exhaustPile.removeCard(pickedCard);

            AbstractGameEffect e = null;
            for (AbstractGameEffect effect: AbstractDungeon.effectList)
            {
                if(effect instanceof ExhaustCardEffect)
                {
                    AbstractCard c = ReflectionHacks.getPrivate(effect, ExhaustCardEffect.class, "c");
                    if(c == pickedCard)
                        e = effect;
                }
            }
            AbstractDungeon.effectList.remove(e);

            AbstractDungeon.effectList.add(new ShowCardAndExhumeEffect(pickedCard, target.hb.cX, target.hb.cY + texture.getWidth() / 4));
        }
    }
}
