package theVacant.cards;

import basemod.ReflectionHacks;
import com.badlogic.gdx.Gdx;
import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.EmptyDeckShuffleAction;
import com.megacrit.cardcrawl.actions.utility.TextAboveCreatureAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import theVacant.powers.AntifactPower;
import theVacant.powers.RequiemPower;

import java.util.ArrayList;
import java.util.Iterator;

public class VacantCardGroup
{
    @SpirePatch(clz = CardGroup.class, method = "initializeDeck")
    public static class UnnateDeckInitializePatch
    {
        @SpireInsertPatch(
                rloc = 6,
                localvars = {"copy"}
        )
        public static void initializeDeckPatch(CardGroup combatDeck, @ByRef CardGroup[] copy)
        {
            ArrayList<AbstractCard> placeOnBot = new ArrayList();
            Iterator vacantIterator = copy[0].group.iterator();

            while (vacantIterator.hasNext()) {
                AbstractCard newCard = (AbstractCard) vacantIterator.next();
                if (newCard instanceof AbstractDynamicCard) {
                    if (((AbstractDynamicCard) newCard).isUnnate)
                        placeOnBot.add(newCard);
                }
            }
            if (placeOnBot.size() > 0) {
                for (AbstractCard abstractCard : placeOnBot) {
                    copy[0].removeCard(abstractCard);
                    copy[0].addToBottom(abstractCard);
                }
            }
        }
    }
    @SpirePatch(clz = EmptyDeckShuffleAction.class, method = "update")
    public static class HeavyReshuffle
    {
        @SpireInsertPatch(
                rloc=3
        )
        public static void update()
        {
            ArrayList<AbstractCard> placeOnBot = new ArrayList();
            CardGroup deck = AbstractDungeon.player.discardPile;
            Iterator vacantIterator = deck.group.iterator();

            while (vacantIterator.hasNext())
            {
                AbstractCard newCard = (AbstractCard) vacantIterator.next();
                if(newCard instanceof AbstractVacantCard)
                {
                    if (((AbstractVacantCard) newCard).isHeavy)
                        placeOnBot.add(newCard);
                }
            }
            if(placeOnBot.size() > 0)
            {
                for (AbstractCard abstractCard : placeOnBot)
                {
                    deck.removeCard(abstractCard);
                    deck.addToBottom(abstractCard);
                }
            }
        }
    }

    @SpirePatch(clz = ApplyPowerAction.class, method = "update")
    public static class ApplyPowerPatches
    {
        @SpireInsertPatch(
                rloc=9
        )
        public static void update(ApplyPowerAction _instance)
        {
            AbstractPower power = ReflectionHacks.getPrivate(_instance, ApplyPowerAction.class, "powerToApply");
//            //Antifact
//            if (_instance.target.hasPower(AntifactPower.POWER_ID) && power.type == AbstractPower.PowerType.BUFF) {// 196 197
//                //_instance.addToTop(new TextAboveCreatureAction(_instance.target, TEXT[0]));// 198
//                /*float dur = ReflectionHacks.getPrivate(_instance, ApplyPowerAction.class, "duration");
//                dur -= Gdx.graphics.getDeltaTime();// 199
//                ReflectionHacks.setPrivate(_instance, ApplyPowerAction.class, "duration", dur);*/
//                CardCrawlGame.sound.play("NULLIFY_SFX");// 200
//                _instance.target.getPower("Artifact").flashWithoutSound();// 201
//                _instance.target.getPower("Artifact").onSpecificTrigger();// 202
//                return;// 203
//            }

            //Requiem
            if(_instance.target.hasPower(RequiemPower.POWER_ID))
            {
                if(_instance.amount > 0 && (power.type == AbstractPower.PowerType.BUFF || power.type == AbstractPower.PowerType.DEBUFF))
                    _instance.amount *= 2;

                power.updateDescription();

                _instance.target.getPower(RequiemPower.POWER_ID).flash();

                for (AbstractCard card: AbstractDungeon.player.hand.group)
                    card.applyPowers();
            }
        }
    }
}
