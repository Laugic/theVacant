package theVacant.cards.Attacks;

import basemod.BaseMod;
import basemod.helpers.TooltipInfo;
import basemod.patches.com.megacrit.cardcrawl.dungeons.AbstractDungeon.NoPools;
import basemod.patches.com.megacrit.cardcrawl.screens.compendium.CardLibraryScreen.NoCompendium;
import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.actions.common.RemoveAllBlockAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.orbs.AbstractOrb;
import com.megacrit.cardcrawl.powers.DexterityPower;
import com.megacrit.cardcrawl.powers.watcher.VigorPower;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;
import com.megacrit.cardcrawl.vfx.BorderLongFlashEffect;
import com.megacrit.cardcrawl.vfx.combat.DieDieDieEffect;
import theVacant.VacantMod;
import theVacant.actions.MineGemAction;
import theVacant.actions.SoulBarrageAction;
import theVacant.cards.AbstractDynamicCard;
import theVacant.characters.TheVacant;
import theVacant.orbs.AbstractGemOrb;
import theVacant.orbs.EmeraldOrb;
import theVacant.orbs.OnyxOrb;
import theVacant.powers.DoomPower;
import theVacant.powers.VoidPower;
import theVacant.util.KeywordManager;

import java.util.ArrayList;
import java.util.List;

import static theVacant.VacantMod.makeCardPath;

public class EmeraldSplash extends AbstractDynamicCard {

    public static final String ID = VacantMod.makeID(EmeraldSplash.class.getSimpleName());
    public static final String IMG = makeCardPath("EmeraldSplash.png");

    private static final CardRarity RARITY = CardRarity.COMMON;
    private static final CardTarget TARGET = CardTarget.ALL_ENEMY;
    private static final CardType TYPE = CardType.ATTACK;
    public static final CardColor COLOR = TheVacant.Enums.COLOR_GOLD;

    private static final int COST = 1;
    private static final int DAMAGE = 3;


    public EmeraldSplash()
    {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        damage = baseDamage = DAMAGE;
        magicNumber = baseMagicNumber = 1;
    }

    @Override
    public void use(AbstractPlayer player, AbstractMonster monster)
    {
        addToBot(new VFXAction(new BorderLongFlashEffect(Color.GREEN)));
        addToBot(new MineGemAction(new EmeraldOrb(magicNumber)));
        addToBot(new VFXAction(new DieDieDieEffect(), 0.7F));
        int numOrbs = 1;
        for (AbstractOrb orb: player.orbs) {
            if(orb instanceof AbstractGemOrb)
                numOrbs++;
        }
        if(numOrbs > 10)
            numOrbs = 10;
//        addToBot(new SoulBarrageAction(1, monster, new DamageInfo(player, damage, damageTypeForTurn), Color.GREEN));
        for (int i = 0; i < numOrbs; i++)
            addToBot(new SoulBarrageAction(Color.GREEN, this));
    }

    @Override
    public void upgrade()
    {
        if (!upgraded)
        {
            upgradeName();
            upgradeDamage(1);
            upgradeMagicNumber(1);
            initializeDescription();
        }
    }
}