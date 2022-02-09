package theVacant.cards.Special;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.random.Random;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;
import com.megacrit.cardcrawl.vfx.combat.MindblastEffect;
import theVacant.VacantMod;
import theVacant.actions.MineGemAction;
import theVacant.cards.AbstractDynamicCard;
import theVacant.cards.Attacks.OnyxBlaster;
import theVacant.characters.TheVacant;
import theVacant.orbs.*;

import static theVacant.VacantMod.makeCardPath;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.combat.MindblastEffect;
import theVacant.VacantMod;
import theVacant.actions.MineGemAction;
import theVacant.cards.AbstractDynamicCard;
import theVacant.characters.TheVacant;
import theVacant.orbs.OnyxOrb;

public class SapphireOption  extends AbstractDynamicCard
{
    public static final String ID = VacantMod.makeID(SapphireOption.class.getSimpleName());
    public static final String IMG = makeCardPath("Aquamarine.png");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);

    private static final AbstractCard.CardRarity RARITY = AbstractCard.CardRarity.SPECIAL;
    private static final AbstractCard.CardTarget TARGET = AbstractCard.CardTarget.NONE;
    private static final AbstractCard.CardType TYPE = CardType.POWER;
    public static final AbstractCard.CardColor COLOR = AbstractCard.CardColor.COLORLESS;

    private static final int COST = -2;

    public SapphireOption()
    {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        Random random = new Random();
        magicNumber = baseMagicNumber = random.random(3,6);
    }

    public void use(AbstractPlayer p, AbstractMonster m)
    {
        onChoseThisOption();
    }

    @Override
    public void onChoseThisOption()
    {
        addToTop(new MineGemAction(new SapphireOrb(magicNumber)));
    }

    @Override
    public void upgrade()
    {
        if (!upgraded)
        {
            upgradeName();
            upgradeMagicNumber(1);
            upgradedMagicNumber = true;
            initializeDescription();
        }
    }
}
