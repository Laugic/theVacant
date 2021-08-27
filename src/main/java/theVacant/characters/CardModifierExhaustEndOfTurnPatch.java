package theVacant.characters;

import basemod.helpers.CardModifierManager;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class CardModifierExhaustEndOfTurnPatch
{
    @SpirePatch(
            clz = AbstractCreature.class,
            method = "applyEndOfTurnTriggers"
    )
    public static class CardModifierAtEndOfTurn
    {
        public static void Postfix(AbstractCreature __instance) {
            AbstractPlayer p = AbstractDungeon.player;
            if (__instance == p) {
                for (AbstractCard c : p.exhaustPile.group) {
                    CardModifierManager.atEndOfTurn(c, p.exhaustPile);
                }
            }
        }
    }
}
