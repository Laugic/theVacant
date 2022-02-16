package theVacant.vfx;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;

public class ShowCardAndMillEffect extends AbstractGameEffect {

    public static final float DURATION = Settings.ACTION_DUR_MED;

    private AbstractCard card;

    public ShowCardAndMillEffect(AbstractCard card) {
        duration = startingDuration = DURATION;
        this.card = card;

        card.target_x = 100 + (int)(Math.random() * 200);
        card.target_y = 100 + (int)(Math.random() * 200);
        card.drawScale = 0.1f;
        card.targetDrawScale = 0.6f;
    }

    @Override
    public void update() {
        if (duration == startingDuration) {
            CardCrawlGame.sound.play("CARD_REJECT");
//            AbstractDungeon.player.drawPile.removeCard(card);
//            AbstractDungeon.player.discardPile.addToTop(card);
        }
        duration -= Gdx.graphics.getDeltaTime();
        card.update();
        if (duration < 0.0F) {
            isDone = true;
            card.shrink();
            AbstractDungeon.getCurrRoom().souls.discard(card, true);
        }
    }

    @Override
    public void render(SpriteBatch sb) {
        if (!isDone)
            card.render(sb);
    }

    @Override
    public void dispose() {

    }
}
