package theVacant.cards.Skills;

import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDiscardAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.ArtifactPower;
import com.megacrit.cardcrawl.powers.GainStrengthPower;
import com.megacrit.cardcrawl.powers.StrengthPower;
import com.megacrit.cardcrawl.vfx.combat.VerticalAuraEffect;
import theVacant.VacantMod;
import theVacant.actions.VacantMillAction;
import theVacant.cards.AbstractDynamicCard;
import theVacant.characters.TheVacant;
import theVacant.powers.DoomPower;

import static theVacant.VacantMod.makeCardPath;

public class Hex extends AbstractDynamicCard
{

    public static final String ID = VacantMod.makeID(Hex.class.getSimpleName());
    public static final String IMG = makeCardPath("Hex.png");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);

    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = TheVacant.Enums.COLOR_GOLD;

    private static final int COST = 1;

    public Hex()
    {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        magicNumber = baseMagicNumber = 3;
        checkHollow = true;
    }

    @Override
    public void use(AbstractPlayer player, AbstractMonster monster)
    {
        addToBot(new VFXAction(player, new VerticalAuraEffect(Color.BLACK, monster.hb.cX, monster.hb.cY), 0.25F));
        addToBot(new ApplyPowerAction(monster, player, new DoomPower(monster, player, magicNumber), magicNumber));
        if(getHollow())
        {
            addToBot(new ApplyPowerAction(monster, player, new StrengthPower(monster, -magicNumber), -magicNumber));
            if(!monster.hasPower(ArtifactPower.POWER_ID) || (monster.hasPower(ArtifactPower.POWER_ID) && monster.getPower(ArtifactPower.POWER_ID).amount <= 1))
                addToBot(new ApplyPowerAction(monster, player, new GainStrengthPower(monster, magicNumber), magicNumber));
        }
            //addToBot(new MakeTempCardInDiscardAction(this.makeStatEquivalentCopy(), 1));
        //addToBot(new VacantMillAction(magicNumber, this));
    }

    @Override
    public void upgrade()
    {
        if (!upgraded)
        {
            upgradeName();
            upgradeMagicNumber(1);
            initializeDescription();
        }
    }
}