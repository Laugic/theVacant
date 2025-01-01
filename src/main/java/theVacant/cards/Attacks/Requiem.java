package theVacant.cards.Attacks;

import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theVacant.VacantMod;
import theVacant.actions.SoulBarrageAction;
import theVacant.cards.AbstractDynamicCard;
import theVacant.characters.TheVacant;
import theVacant.powers.AntifactPower;
import theVacant.powers.RequiemBuddyPower;
import theVacant.powers.RequiemPower;
import theVacant.vfx.HorizontalThrowVFX;

import static theVacant.VacantMod.makeCardPath;

public class Requiem extends AbstractDynamicCard
{

    public static final String ID = VacantMod.makeID(Requiem.class.getSimpleName());
    public static final String IMG = makeCardPath("Requiem.png");

    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;

    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final CardType TYPE = CardType.ATTACK;
    public static final CardColor COLOR = TheVacant.Enums.COLOR_GOLD;

    private static final int COST = 1;

    public Requiem()
    {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        damage = baseDamage = 10;
        exhaust = true;
    }

    @Override
    public void use(AbstractPlayer player, AbstractMonster monster)
    {
        //addToBot(new VFXAction(new HorizontalThrowVFX(player.hb.cX, player.hb.cY, Color.GOLD)));
        addToBot(new SoulBarrageAction(1, monster, new DamageInfo(player, damage, DamageInfo.DamageType.NORMAL), Color.GOLD));
        addToBot(new ApplyPowerAction(monster, player, new RequiemPower(monster, player, 1), 1));
        addToBot(new ApplyPowerAction(player, player, new RequiemBuddyPower(player, player, 1), 0));
    }

    @Override
    public void upgrade()
    {
        if (!upgraded)
        {
            upgradeName();
            upgradeDamage(5);
            initializeDescription();
        }
    }
}