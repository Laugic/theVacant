package theVacant.cards.Attacks;

import com.badlogic.gdx.graphics.Color;
import com.evacipated.cardcrawl.mod.stslib.cards.interfaces.StartupCard;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.BorderFlashEffect;
import theVacant.VacantMod;
import theVacant.actions.SoulBarrageAction;
import theVacant.cards.AbstractDynamicCard;
import theVacant.characters.TheVacant;
import theVacant.powers.InvisibleDebuffTracker;

import static theVacant.VacantMod.makeCardPath;

public class DarkStrike extends AbstractDynamicCard
{

    public static final String ID = VacantMod.makeID(DarkStrike.class.getSimpleName());
    public static final String IMG = makeCardPath("DarkStrike.png");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);

    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.ALL_ENEMY;
    private static final CardType TYPE = CardType.ATTACK;
    public static final CardColor COLOR = TheVacant.Enums.COLOR_GOLD;

    private static final int COST = 3;
    private static final int DAMAGE = 7;

    public DarkStrike()
    {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        damage = baseDamage = DAMAGE;
        magicNumber = baseMagicNumber = 1;
        tags.add(CardTags.STRIKE);
    }

    @Override
    public void use(AbstractPlayer player, AbstractMonster monster)
    {
        magicNumber = baseMagicNumber = InvisibleDebuffTracker.numDebuffs + 1;
        if(magicNumber > 0)
            addToBot(new VFXAction(player, new BorderFlashEffect(Color.PURPLE), 0.3F, true));
        for (int i = 0; i < magicNumber; i++) {
            addToBot(new SoulBarrageAction(1, AbstractDungeon.getRandomMonster(), new DamageInfo(player, damage, damageTypeForTurn), Color.PURPLE));
        }
    }

    @Override
    public void applyPowers() {
        super.applyPowers();// 52
        this.baseMagicNumber = 0;// 54
        this.magicNumber = 0;// 55

        magicNumber = baseMagicNumber = InvisibleDebuffTracker.numDebuffs + 1;

        if (baseMagicNumber > 0) {// 62
            rawDescription = cardStrings.DESCRIPTION + cardStrings.EXTENDED_DESCRIPTION[0];// 63
            initializeDescription();// 64
        }

    }// 66

    public void onMoveToDiscard() {
        this.rawDescription = cardStrings.DESCRIPTION;// 70
        this.initializeDescription();// 71
    }// 72

    public void calculateCardDamage(AbstractMonster mo) {
        super.calculateCardDamage(mo);// 76
        if (this.baseMagicNumber > 0) {// 77
            this.rawDescription = cardStrings.DESCRIPTION + cardStrings.EXTENDED_DESCRIPTION[0];// 78
        }

        this.initializeDescription();// 80
    }// 81

    @Override
    public void upgrade()
    {
        if (!upgraded)
        {
            upgradeName();
            upgradeDamage(3);
            initializeDescription();
        }
    }
}