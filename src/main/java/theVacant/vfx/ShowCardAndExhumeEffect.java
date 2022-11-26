package theVacant.vfx;

import basemod.BaseMod;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;
import theVacant.relics.LocketRelic;

public class ShowCardAndExhumeEffect extends AbstractGameEffect {

    public static final float DURATION = Settings.ACTION_DUR_MED;

    private AbstractCard card;
    boolean running = false;

    public ShowCardAndExhumeEffect(AbstractCard card, float x, float y) {
        duration = startingDuration = DURATION;
        this.card = card;

        card.drawScale = 0.1f;
        card.targetDrawScale = 0.6f;
        card.lighten(true);
        card.unfadeOut();
        card.unhover();

        card.current_x = x;
        card.current_y = y;
    }

    @Override
    public void update() {
    if (duration == startingDuration) {
        card.target_x = card.current_x + (- 100 + (int) (Math.random() * 200)) * Settings.scale;
        card.target_y = card.current_y + (- 100 + (int) (Math.random() * 200)) * Settings.scale;
            running = true;
            CardCrawlGame.sound.play("CARD_REJECT");
        }
        Run();
    }

    private void Run()
    {
        duration -= Gdx.graphics.getDeltaTime();
        card.update();
        if (duration < 0.0F) {
            isDone = true;

            if(AbstractDungeon.player.hand.size() >= BaseMod.MAX_HAND_SIZE)
            {
                AbstractDungeon.player.discardPile.addToTop(card);
                card.shrink();
                AbstractDungeon.getCurrRoom().souls.discard(card, true);
            }
            else
            {
                AbstractDungeon.player.hand.addToTop(card);
                AbstractDungeon.player.hand.refreshHandLayout();
                AbstractDungeon.player.hand.applyPowers();
            }
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
