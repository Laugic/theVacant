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
    ArrayList<AbstractCard> gemChoices = new ArrayList<>();
//    RubyOption rubyOption;
//    SapphireOption sapphireOption;
//    AmethystOption amethystOption;
//    EmeraldOption emeraldOption;
//    OpalOption opalOption;
//    OnyxOption onyxOption;
//    TopazOption topazOption;
//    DiamondOption diamondOption;

    public SpelunkAction(int numOptions)
    {
        this.amount = numOptions;
//        rubyOption = new RubyOption();
//        sapphireOption = new SapphireOption();
//        amethystOption= new AmethystOption();
//        emeraldOption = new EmeraldOption();
//        opalOption = new OpalOption();
//        onyxOption = new OnyxOption();
//        topazOption = new TopazOption();
//        diamondOption = new DiamondOption();
    }

    public void update()
    {
//        for (int i = 0; i < amount; ++i)
//            gemChoices.add(MineNewGem());
//        addToBot(new ChooseOneAction(gemChoices));
        isDone = true;
    }

//    private AbstractDynamicCard MineNewGem()
//    {
//        int rand = AbstractDungeon.cardRandomRng.random(20);
//        if(rand < 4 && !gemChoices.contains(rubyOption))
//            return rubyOption;
//        if(rand < 8 && !gemChoices.contains(sapphireOption))
//            return sapphireOption;
//        if(rand < 12 && !gemChoices.contains(amethystOption))
//            return amethystOption;
//        if(rand < 14 && !gemChoices.contains(emeraldOption))
//            return emeraldOption;
//        if(rand < 16 && !gemChoices.contains(onyxOption))
//            return onyxOption;
//        if(rand < 18 && !gemChoices.contains(opalOption))
//            return opalOption;
//        if(rand < 19 && !gemChoices.contains(topazOption))
//            return topazOption;
//        return diamondOption;
//    }
}
