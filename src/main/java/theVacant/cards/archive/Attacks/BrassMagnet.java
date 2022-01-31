package theVacant.cards.archive.Attacks;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.DiscardSpecificCardAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.combat.FlickCoinEffect;
import theVacant.VacantMod;
import theVacant.cards.AbstractDynamicCard;
import theVacant.characters.TheVacant;

import static theVacant.VacantMod.makeCardPath;

public class BrassMagnet extends AbstractDynamicCard {

    public static final String ID = VacantMod.makeID(BrassMagnet.class.getSimpleName());
    public static final String IMG = makeCardPath("BrassMagnet.png");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);

    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final CardType TYPE = CardType.ATTACK;
    public static final CardColor COLOR = TheVacant.Enums.COLOR_GOLD;

    private static final int COST = 0;
    private static final int DAMAGE = 14;
    private static final int UPGRADE_PLUS_DMG = 4;

    public BrassMagnet()
    {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        this.baseDamage = DAMAGE;
        this.rebound = true;
    }

    @Override
    public void use(AbstractPlayer player, AbstractMonster monster)
    {
        AbstractDungeon.actionManager.addToBottom(new VFXAction(new FlickCoinEffect(player.hb.cX, player.hb.cY, monster.hb.cX, monster.hb.cY), 0.3F));
        AbstractDungeon.actionManager.addToBottom(new DamageAction(monster, new DamageInfo(player, this.damage, damageTypeForTurn), AbstractGameAction.AttackEffect.NONE));
    }

    @Override
    public void upgrade()
    {
        if (!upgraded)
        {
            upgradeName();
            upgradeDamage(UPGRADE_PLUS_DMG);
            initializeDescription();
        }
    }
    @Override
    public void triggerWhenDrawn()
    {
        AbstractDungeon.actionManager.addToTop(new DiscardSpecificCardAction(this));
    }
}