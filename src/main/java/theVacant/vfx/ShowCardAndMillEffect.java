package theVacant.vfx;

import basemod.BaseMod;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.cards.Soul;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;
import theVacant.powers.RunicThoughtsPower;
import theVacant.relics.LocketRelic;

public class ShowCardAndMillEffect extends AbstractGameEffect {

    public static final float DURATION = Settings.ACTION_DUR_MED;

    private AbstractCard card;
    private CardGroup groupToGoTo;
    private CardGroup groupFrom;
    boolean running = false;

    public ShowCardAndMillEffect(AbstractCard card, CardGroup groupToGoTo){
        this(card, groupToGoTo, AbstractDungeon.player.drawPile);
    }

    public ShowCardAndMillEffect(AbstractCard card, CardGroup groupToGoTo, CardGroup groupFrom) {
        duration = startingDuration = DURATION;
        this.card = card;
        this.groupToGoTo = groupToGoTo;
        this.groupFrom = groupFrom;

        card.drawScale = 0.1f;
        card.targetDrawScale = 0.6f;
        card.lighten(true);
        card.unfadeOut();
        card.unhover();
    }

    @Override
    public void update() {
    if(!running)
        duration = startingDuration;
    if (duration == startingDuration) {
        boolean run = true;
        if (groupFrom == AbstractDungeon.player.discardPile)
        {
            if(card.current_x < (float)Settings.WIDTH)
                run = false;
            else
                card.target_x = Settings.scale * Settings.WIDTH - 100 - (int) (Math.random() * 200) - card.hb.width;
        }
        else
            card.target_x = 100 + (int) (Math.random() * 200);
        if(run) {
            card.target_y = 100 + (int) (Math.random() * 200);
            running = true;
            CardCrawlGame.sound.play("CARD_REJECT");
        }
    }
    if(running)
        Run();
    }

    private void Run()
    {
        duration -= Gdx.graphics.getDeltaTime();
        card.update();
        if (duration < 0.0F) {
            isDone = true;

            if(groupToGoTo == AbstractDungeon.player.hand)
            {
                if(AbstractDungeon.player.hand.size() >= BaseMod.MAX_HAND_SIZE)
                {
                    groupToGoTo = AbstractDungeon.player.discardPile;
                    AbstractDungeon.player.discardPile.addToTop(card);
                }
                else
                {
                    AbstractDungeon.player.hand.addToTop(card);
                    AbstractDungeon.player.hand.refreshHandLayout();
                    AbstractDungeon.player.hand.applyPowers();
                    if(groupFrom == AbstractDungeon.player.drawPile)
                        PostRebound(card);
                }
            }
            if(groupToGoTo == AbstractDungeon.player.discardPile)
            {
                card.shrink();
                AbstractDungeon.getCurrRoom().souls.discard(card, true);
            }
        }
    }

    private void PostRebound(AbstractCard card)
    {
        AbstractPlayer player = AbstractDungeon.player;
        if(player != null && player.hasRelic(LocketRelic.ID))
        {
            player.getRelic(LocketRelic.ID).flash();
            AbstractDungeon.actionManager.addToBottom(new GainBlockAction(player, LocketRelic.BLOCK_AMOUNT));
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
