package theVacant.cards.Attacks;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.orbs.AbstractOrb;
import theVacant.VacantMod;
import theVacant.actions.ChipOrbAction;
import theVacant.cards.AbstractDynamicCard;
import theVacant.characters.TheVacant;
import theVacant.orbs.AbstractGemOrb;

import static theVacant.VacantMod.makeCardPath;

public class GildedPickaxe extends AbstractDynamicCard {

    public static final String ID = VacantMod.makeID(GildedPickaxe.class.getSimpleName());
    public static final String IMG = makeCardPath("GildedPickaxe.png");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);

    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final CardType TYPE = CardType.ATTACK;
    public static final CardColor COLOR = TheVacant.Enums.COLOR_GOLD;

    private static final int COST = 1;
    private static final int DAMAGE = 6;

    public GildedPickaxe()
    {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        damage = baseDamage = DAMAGE;
    }

    @Override
    public void use(AbstractPlayer player, AbstractMonster monster)
    {
        for(int i = 0; i < 2; i++)
            AbstractDungeon.actionManager.addToBottom(new DamageAction(monster, new DamageInfo(player, damage, damageTypeForTurn), AbstractGameAction.AttackEffect.BLUNT_LIGHT));

        if(player.orbs.size() > 0)
        {
            for (AbstractOrb gem : player.orbs)
            {
                if(gem instanceof AbstractGemOrb)
                    addToBot(new ChipOrbAction(gem, 1));
            }
        }
    }

    @Override
    public void upgrade()
    {
        if (!upgraded)
        {
            upgradeName();
            upgradeDamage(2);
            upgradedDamage = true;
            initializeDescription();
        }
    }
}