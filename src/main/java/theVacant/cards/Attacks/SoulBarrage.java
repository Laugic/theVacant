package theVacant.cards.Attacks;

import com.badlogic.gdx.graphics.Color;
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
import com.megacrit.cardcrawl.relics.ChemicalX;
import com.megacrit.cardcrawl.ui.panels.EnergyPanel;
import com.megacrit.cardcrawl.vfx.BorderFlashEffect;
import theVacant.VacantMod;
import theVacant.actions.SoulBarrageAction;
import theVacant.cards.AbstractDynamicCard;
import theVacant.characters.TheVacant;
import theVacant.powers.InvisibleDebuffTracker;

import static theVacant.VacantMod.makeCardPath;

public class SoulBarrage extends AbstractDynamicCard {

    public static final String ID = VacantMod.makeID(SoulBarrage.class.getSimpleName());
    public static final String IMG = makeCardPath("SoulBarrage.png");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);

    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.ALL_ENEMY;
    private static final CardType TYPE = CardType.ATTACK;
    public static final CardColor COLOR = TheVacant.Enums.COLOR_GOLD;

    private static final int COST = -1, DAMAGE = 7, STARTING_MAGIC = 3;

    public SoulBarrage()
    {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        damage = baseDamage = DAMAGE;
        magicNumber = baseMagicNumber = 2;
        checkHollow = true;
    }


    @Override
    public void use(AbstractPlayer player, AbstractMonster monster)
    {
        int effect = this.energyOnUse;
        if(getHollow())
            effect += magicNumber;
        if (player.hasRelic("Chemical X")) {
            effect += ChemicalX.BOOST;
            player.getRelic("Chemical X").flash();
        }

        for (int i = 0; i < effect; i++)
            addToBot(new SoulBarrageAction(1, AbstractDungeon.getRandomMonster(), new DamageInfo(player, damage, damageTypeForTurn), Color.CYAN));

        if (!freeToPlayOnce)
            player.energy.use(EnergyPanel.totalCount);
    }

    @Override
    public void upgrade()
    {
        upgradeName();
        upgradeDamage(3);
        initializeDescription();
    }
}