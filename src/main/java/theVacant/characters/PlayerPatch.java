package theVacant.characters;

import com.evacipated.cardcrawl.modthespire.lib.ByRef;
import com.evacipated.cardcrawl.modthespire.lib.SpireInsertPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import theVacant.relics.BoundSoul;

public class PlayerPatch
{
    /*
    @SpirePatch(clz = AbstractPlayer.class, method = "damage")
    public static class VacantFormDeathPrevention
    {
        @SpireInsertPatch(
                rloc = 126
        )
        public static void damage(DamageInfo info)
        {
            //if(AbstractDungeon.player.hasRelic(BoundSoul.ID) && AbstractDungeon.player.currentHealth < 1 && AbstractDungeon.player.getRelic(BoundSoul.ID).counter == BoundSoul.VACANT_FORM)
                return;
        }
    }*/
}
