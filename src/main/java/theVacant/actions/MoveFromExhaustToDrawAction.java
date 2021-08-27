package theVacant.actions;

import basemod.BaseMod;
import basemod.abstracts.AbstractCardModifier;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.utility.DrawPileToHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theVacant.cards.Modifiers.VoidboundModifier;

import java.util.ArrayList;

import static basemod.helpers.CardModifierManager.addModifier;
import static basemod.helpers.CardModifierManager.getModifiers;
import static com.megacrit.cardcrawl.characters.AbstractPlayer.uiStrings;

public class MoveFromExhaustToDrawAction extends AbstractGameAction
{

    public static final String[] TEXT = uiStrings.TEXT;

    private AbstractCard card;

    public MoveFromExhaustToDrawAction(AbstractCard card)
    {
        this.card = card;
    }

    public void update()
    {
        AbstractPlayer player = AbstractDungeon.player;
        if(player.exhaustPile.contains(card))
        {
            player.exhaustPile.moveToDeck(card, false);
            card.unhover();
            card.lighten(true);
            card.unfadeOut();
        }
        this.isDone = true;
    }

}
