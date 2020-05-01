package theVacant.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class DarkStrikeAction extends AbstractGameAction
{
    private float startingDuration;
    public AbstractMonster target;
    public int damage;

    public DarkStrikeAction(AbstractMonster target, int damage, DamageInfo.DamageType dmgType)
    {
        this.actionType = ActionType.DAMAGE;
        this.startingDuration = Settings.ACTION_DUR_FAST;
        this.duration = this.startingDuration;
        this.target = target;
        this.damage = damage;
        this.damageType = dmgType;
    }

    public void update()
    {
        AbstractPlayer player = AbstractDungeon.player;
        int numDark = GetNumberOfDarkCards(player);
        for (int i = 0; i < numDark; i++)
            AbstractDungeon.actionManager.addToBottom( new DamageAction(this.target, new DamageInfo(player, this.damage, this.damageType), AbstractGameAction.AttackEffect.BLUNT_LIGHT));
        this.isDone = true;
    }

    private int GetNumberOfDarkCards(AbstractPlayer player)
    {
        int numDark = 0;
        for(AbstractCard card:player.drawPile.group)
        {
            if(card.color == AbstractCard.CardColor.COLORLESS || card.type == AbstractCard.CardType.CURSE)
                numDark++;
        }
        for(AbstractCard card:player.hand.group)
        {
            if(card.color == AbstractCard.CardColor.COLORLESS || card.type == AbstractCard.CardType.CURSE)
                numDark++;
        }
        for(AbstractCard card:player.discardPile.group)
        {
            if(card.color == AbstractCard.CardColor.COLORLESS || card.type == AbstractCard.CardType.CURSE)
                numDark++;
        }
        return numDark;
    }
}
