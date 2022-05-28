package theVacant.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.watcher.ChooseOneAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theVacant.cards.AbstractDynamicCard;
import theVacant.cards.Special.*;

import java.util.ArrayList;

public class SpelunkAction extends AbstractGameAction
{
    public SpelunkAction(int numOptions)
    {
        this.amount = numOptions;
    }

    public void update()
    {
        ArrayList<AbstractCard> gemChoices = new ArrayList<>();
        for (int i = 0; i < amount; ++i)
            gemChoices.add(MineNewGem());
        addToBot(new ChooseOneAction(gemChoices));
        isDone = true;
    }

    private AbstractDynamicCard MineNewGem()
    {
        int rand = AbstractDungeon.cardRandomRng.random(46);
        if(rand < 10)
            return new RubyOption(AbstractDungeon.miscRng.random(3,6));
        if(rand < 20)
            return new SapphireOption(AbstractDungeon.miscRng.random(3,6));
        if(rand < 25)
            return new OpalOption(AbstractDungeon.miscRng.random(2,3));
        if(rand < 30)
            return new EmeraldOption(AbstractDungeon.miscRng.random(2,3));
        if(rand < 35)
            return new OnyxOption(AbstractDungeon.miscRng.random(2,3));
        if(rand < 40)
            return new AmethystOption(AbstractDungeon.miscRng.random(3, 6));
        if(rand < 45)
            return new TopazOption(AbstractDungeon.miscRng.random(2, 3));
        return new DiamondOption(AbstractDungeon.miscRng.random(1,3));
    }
}
