package theVacant.gems;

import com.badlogic.gdx.math.MathUtils;
import com.esotericsoftware.spine.AnimationState;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.GameActionManager;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.SporeCloudPower;
import theVacant.VacantMod;
import theVacant.characters.TheVacant;
import theVacant.powers.RubyPower;

public class RubyGem extends AbstractMonster
{
    public static final String NAME = "Ruby";
    public static final String ID = VacantMod.makeID("Ruby");


    public RubyGem()
    {
        this(3, 0.0f, 0f);
    }

    public RubyGem(int size, float x, float y)
    {
        super(NAME, ID, size, x, y, 128, 128, null, 0, 0);

        this.loadAnimation(
                "theVacantResources/images/monsters/gems/Ruby.atlas",
                "theVacantResources/images/monsters/gems/Ruby.json",
                1.0f);

        AnimationState.TrackEntry e = this.state.setAnimation(0, "idle", true);
        e.setTime(e.getEndTime() * MathUtils.random());
    }

    /*@Override
    public void init()
    {
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(this, this, new RubyPower(this, this, maxHealth)));
    }*/

    @Override
    public void takeTurn()
    {

    }

    @Override
    protected void getMove(int i)
    {
        this.setMove((byte) 1, Intent.BUFF, 0);
    }


    public static class MoveBytes {
        public static final byte TACKLE = 0;
        public static final byte DEFEND = 1;
        public static final byte VENOM_SHOT = 2;
        public static final byte FANG_ATTACK = 3;
    }
}
