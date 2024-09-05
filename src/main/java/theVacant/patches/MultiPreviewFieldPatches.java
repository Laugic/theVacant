package theVacant.patches;

import basemod.ReflectionHacks;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireField;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon.CurrentScreen;
import com.megacrit.cardcrawl.helpers.Hitbox;
import com.megacrit.cardcrawl.screens.SingleCardViewPopup;
import com.megacrit.cardcrawl.ui.FtueTip;

import java.util.ArrayList;
import java.util.Iterator;

public class MultiPreviewFieldPatches {
    private static Float cardTipPad = null;

    public MultiPreviewFieldPatches() {
    }// 16

    public static void addPreview(AbstractCard card, AbstractCard preview) {
        if (card != null && preview != null) {// 23
            ExtraPreviews.previews.get(card).add(preview);// 24
            if (preview.cardsToPreview != null && !(ExtraPreviews.previews.get(card)).contains(preview.cardsToPreview)) {// 25
                addPreview(card, preview.cardsToPreview);// 26
            }

            Iterator var2 = ((ArrayList) ExtraPreviews.previews.get(preview)).iterator();// 28

            while (var2.hasNext()) {
                AbstractCard c = (AbstractCard) var2.next();
                if (!((ArrayList) ExtraPreviews.previews.get(card)).contains(c)) {// 29
                    addPreview(card, c);// 30
                }
            }
        }

    }// 34

    @SpirePatch(
            clz = AbstractCard.class,
            method = "renderCardTip"
    )
    public static class renderSwappablesPreviewPatch {
        public renderSwappablesPreviewPatch() {
        }// 79

        public static void Postfix(AbstractCard __instance, SpriteBatch sb) {
            if ((!__instance.isLocked && __instance.isSeen && !Settings.hideCards && (Boolean) ReflectionHacks.getPrivate(__instance, AbstractCard.class, "renderTip") || AbstractDungeon.screen == CurrentScreen.FTUE && ReflectionHacks.getPrivate(AbstractDungeon.ftue, FtueTip.class, "c") == __instance) && !((ArrayList) ExtraPreviews.previews.get(__instance)).isEmpty()) {// 82
                if (AbstractDungeon.player != null && AbstractDungeon.player.isDraggingCard) {// 83
                    return;// 84
                }

                boolean rightSide = __instance.current_x < (float) Settings.WIDTH * 0.25F;// 86
                if (cardTipPad == null) {// 87
                    cardTipPad = (Float) ReflectionHacks.getPrivateStatic(AbstractCard.class, "CARD_TIP_PAD");// 88
                }

                float renderX = (AbstractCard.IMG_WIDTH / 2.0F + AbstractCard.IMG_WIDTH / 2.0F * 0.8F + cardTipPad) * __instance.drawScale;// 90
                float horizontal = (AbstractCard.IMG_WIDTH * 0.8F + cardTipPad) * __instance.drawScale;// 91
                if (!rightSide) {// 92
                    horizontal *= -1.0F;// 93
                }

                float vertical = (AbstractCard.IMG_HEIGHT * 0.8F + cardTipPad) * __instance.drawScale;// 95
                boolean verticalOffset = false;// 96
                if (rightSide) {// 97
                    renderX += __instance.current_x;// 98
                } else {
                    renderX = __instance.current_x - renderX;// 100
                }

                float renderY = __instance.current_y + (AbstractCard.IMG_HEIGHT / 2.0F - AbstractCard.IMG_HEIGHT / 2.0F * 0.8F) * __instance.drawScale;// 102
                if (__instance.cardsToPreview != null) {// 103
                    renderX += horizontal;// 106
                }

                for (Iterator var8 = ((ArrayList) ExtraPreviews.previews.get(__instance)).iterator(); var8.hasNext(); renderX += horizontal) {// 108 113
                    AbstractCard next = (AbstractCard) var8.next();
                    next.current_x = renderX;// 109
                    next.current_y = renderY;// 110
                    next.drawScale = __instance.drawScale * 0.8F;// 111
                    next.render(sb);// 112
                }
            }

        }// 124
    }

    @SpirePatch(
            clz = SingleCardViewPopup.class,
            method = "renderTips"
    )
    public static class renderSwappablesInSingleViewPatch {
        public renderSwappablesInSingleViewPatch() {
        }// 39

        public static void Postfix(SingleCardViewPopup __instance, SpriteBatch sb) {
            AbstractCard card = (AbstractCard) ReflectionHacks.getPrivate(__instance, SingleCardViewPopup.class, "card");// 42
            if (!card.isLocked && card.isSeen && !((ArrayList) ExtraPreviews.previews.get(card)).isEmpty()) {// 43
                float renderX = 1920.0F * Settings.scale - 1435.0F * Settings.scale;// 44
                float renderY = 795.0F * Settings.scale;// 45
                if (cardTipPad == null) {// 46
                    cardTipPad = (Float) ReflectionHacks.getPrivateStatic(AbstractCard.class, "CARD_TIP_PAD");// 47
                }

                float horizontal = AbstractCard.IMG_WIDTH * 0.8F + cardTipPad;// 49
                Hitbox prevHb = (Hitbox) ReflectionHacks.getPrivate(__instance, SingleCardViewPopup.class, "prevHb");// 50
                float vertical = AbstractCard.IMG_HEIGHT * 0.8F + cardTipPad;// 51
                if (prevHb != null) {// 52
                    vertical += prevHb.height;// 53
                }

                boolean verticalOffset = false;// 55
                if (card.cardsToPreview != null) {// 56
                    renderY -= vertical;// 57
                    verticalOffset = true;// 58
                }

                Iterator var9 = ((ArrayList) ExtraPreviews.previews.get(card)).iterator();// 60

                while (var9.hasNext()) {
                    AbstractCard next = (AbstractCard) var9.next();
                    next.current_x = renderX;// 61
                    next.current_y = renderY;// 62
                    next.drawScale = 0.8F;// 63
                    next.render(sb);// 64
                    if (verticalOffset) {// 65
                        renderY += vertical;// 66
                        renderX -= horizontal;// 67
                        verticalOffset = false;// 68
                    } else {
                        renderY -= vertical;// 70
                        verticalOffset = true;// 71
                    }
                }
            }

        }// 75
    }

    @SpirePatch(
            clz = AbstractCard.class,
            method = "<class>"
    )
    public static class ExtraPreviews {
        public static SpireField<ArrayList<AbstractCard>> previews = new SpireField<>(ArrayList::new);

        public ExtraPreviews() {
        }// 18
    }
}
