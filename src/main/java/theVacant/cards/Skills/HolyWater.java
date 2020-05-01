package theVacant.cards.Skills;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.BetterDiscardPileToHandAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.DiscardAction;
import com.megacrit.cardcrawl.actions.unique.RegenAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.combat.LightningEffect;
import theVacant.VacantMod;
import theVacant.cards.AbstractDynamicCard;
import theVacant.characters.TheVacant;

import static theVacant.VacantMod.makeCardPath;

public class HolyWater extends AbstractDynamicCard
{

    public static final String ID = VacantMod.makeID(HolyWater.class.getSimpleName());
    public static final String IMG = makeCardPath("Skill.png");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);

    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = TheVacant.Enums.COLOR_GOLD;

    private static final int COST = 0;

    public HolyWater()
    {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        this.magicNumber = this.baseMagicNumber = 10;
        this.damage = this.baseDamage = 10;
        this.exhaust = true;
        this.tags.add(AbstractCard.CardTags.HEALING);
    }

    @Override
    public void use(AbstractPlayer player, AbstractMonster monster)
    {
        AbstractDungeon.effectsQueue.add(new LightningEffect(AbstractDungeon.player.hb.cX, AbstractDungeon.player.hb.cY));
        addToBot(new DamageAction(AbstractDungeon.player, new DamageInfo(AbstractDungeon.player, this.damage, DamageInfo.DamageType.NORMAL), AbstractGameAction.AttackEffect.NONE));
        addToBot(new RegenAction(AbstractDungeon.player, this.magicNumber));
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