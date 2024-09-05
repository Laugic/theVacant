package theVacant.patches;

import basemod.abstracts.AbstractCardModifier;
import basemod.helpers.CardModifierManager;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch2;
import com.evacipated.cardcrawl.modthespire.lib.SpirePrefixPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireReturn;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import theVacant.powers.FreeToPlayPower;

public class FreeToPlayPatch {
    @SpirePatch2(clz = AbstractCard.class, method = "freeToPlay")
    public static class FreeCardPlz {
        @SpirePrefixPatch
        public static SpireReturn<?> free(AbstractCard __instance) {
            if (AbstractDungeon.player != null && !AbstractDungeon.isScreenUp && AbstractDungeon.currMapNode != null && AbstractDungeon.getCurrRoom().phase == AbstractRoom.RoomPhase.COMBAT) {
                for (AbstractPower pow : AbstractDungeon.player.powers) {
                    if (pow instanceof FreeToPlayPower && ((FreeToPlayPower) pow).isFreeToPlay(__instance)) {
                        return SpireReturn.Return(true);
                    }
                }
                /*for (AbstractCardModifier cardMod : CardModifierManager.modifiers(__instance)) {
                    if (cardMod instanceof FreeToPlayMod && ((FreeToPlayMod) cardMod).isFreeToPlay(__instance))
                        return SpireReturn.Return(true);
                }*/

//                if (CardTemperatureFields.getCardHeat(__instance) == CardTemperatureFields.OVERHEATED)
//                    return SpireReturn.Return(true);
            }
            return SpireReturn.Continue();
        }
    }
}
