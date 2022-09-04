package theVacant.relics;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.evacipated.cardcrawl.mod.stslib.actions.tempHp.AddTemporaryHPAction;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import theVacant.VacantMod;
import theVacant.actions.VacantMillAction;
import theVacant.cards.AbstractVacantCard;
import theVacant.powers.DoomPower;
import theVacant.powers.ShroudPower;
import theVacant.powers.VoidPower;
import theVacant.util.TextureLoader;
import static theVacant.VacantMod.makeRelicOutlinePath;
import static theVacant.VacantMod.makeRelicPath;

public class Deathbell extends CustomRelic
{

    public static final String ID = VacantMod.makeID(Deathbell.class.getSimpleName());

    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath("deathbell_relic.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(makeRelicOutlinePath("deathbell_relic.png"));

    public static int bonusAmount = 2, DOOM_AMOUNT = 2, TempHP = 10;

    public Deathbell() {
        super(ID, IMG, OUTLINE, RelicTier.UNCOMMON, LandingSound.MAGICAL);
    }
    
    @Override
    public void atBattleStart()
    {
        flash();

        AbstractPlayer player = AbstractDungeon.player;
        if(AbstractVacantCard.getWounded())
        {
            addToBot(new RelicAboveCreatureAction(AbstractDungeon.player, this));
            addToBot(new AddTemporaryHPAction(player, player, TempHP));
            grayscale = true;
            /*player.addPower(new ShroudPower(player, player, 2));
            for (AbstractMonster mo : (AbstractDungeon.getCurrRoom()).monsters.monsters)
                addToBot(new ApplyPowerAction(mo, player, new DoomPower(mo, mo, DOOM_AMOUNT), DOOM_AMOUNT, true, AbstractGameAction.AttackEffect.NONE));*/
        }
    }

    @Override
    public void justEnteredRoom(AbstractRoom room)
    {
        grayscale = false;
    }
    
    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }
}