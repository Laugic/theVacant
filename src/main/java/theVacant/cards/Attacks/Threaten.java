package theVacant.cards.Attacks;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.combat.WhirlwindEffect;
import theVacant.VacantMod;
import theVacant.cards.AbstractDynamicCard;
import theVacant.characters.TheVacant;

import java.util.Random;

import static theVacant.VacantMod.makeCardPath;

public class Threaten extends AbstractDynamicCard {

    public static final String ID = VacantMod.makeID(Threaten.class.getSimpleName());
    public static final String IMG = makeCardPath("Threaten.png");

    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final CardType TYPE = CardType.ATTACK;
    public static final CardColor COLOR = TheVacant.Enums.COLOR_GOLD;

    private static final int COST = 1;
    private static final int DAMAGE = 0;

    public Threaten()
    {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        this.damage = this.baseDamage = DAMAGE;
        this.magicNumber = this.baseMagicNumber = 5;
        this.exhaust = true;
    }

    @Override
    public void use(AbstractPlayer player, AbstractMonster monster)
    {
        Random random = new Random();
        if(random.nextInt(250) != 0)
            AbstractDungeon.actionManager.addToBottom(new SFXAction("theVacant:ora"));
        else
            AbstractDungeon.actionManager.addToBottom(new SFXAction("theVacant:ora2"));
        AbstractDungeon.actionManager.addToBottom(new VFXAction(player, new WhirlwindEffect(), Settings.FAST_MODE?0.25F:1.0f));
        for(int i = 0; i < this.magicNumber; i++)
            AbstractDungeon.actionManager.addToBottom(new DamageAction(monster, new DamageInfo(player, damage, damageTypeForTurn), AbstractGameAction.AttackEffect.BLUNT_LIGHT));
    }

    @Override
    public void upgrade()
    {
        if (!upgraded)
        {
            upgradeName();
            upgradeMagicNumber(2);
            this.upgradedMagicNumber = true;
            initializeDescription();
        }
    }
}