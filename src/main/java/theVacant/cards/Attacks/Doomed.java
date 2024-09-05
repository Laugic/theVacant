package theVacant.cards.Attacks;

import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.ArtifactPower;
import com.megacrit.cardcrawl.powers.FrailPower;
import com.megacrit.cardcrawl.powers.StrengthPower;
import com.megacrit.cardcrawl.vfx.combat.VerticalAuraEffect;
import theVacant.VacantMod;
import theVacant.actions.AddReduceDoomAction;
import theVacant.actions.MineGemAction;
import theVacant.cards.AbstractDynamicCard;
import theVacant.characters.TheVacant;
import theVacant.orbs.SapphireOrb;
import theVacant.powers.DizzyPower;
import theVacant.powers.DoomPower;
import theVacant.powers.RemoveDoomPower;

import static theVacant.VacantMod.makeCardPath;

public class Doomed extends AbstractDynamicCard {

    public static final String ID = VacantMod.makeID(Doomed.class.getSimpleName());
    public static final String IMG = makeCardPath("Doomed.png");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);

    private static final CardRarity RARITY = CardRarity.COMMON;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final CardType TYPE = CardType.ATTACK;
    public static final CardColor COLOR = TheVacant.Enums.COLOR_GOLD;

    private static final int COST = 1;
    private static final int DAMAGE = 14;

    public Doomed()
    {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        damage = baseDamage = DAMAGE;
        magicNumber = baseMagicNumber = 2;
    }

    @Override
    public void use(AbstractPlayer player, AbstractMonster monster)
    {
        addToBot(new DamageAction(monster, new DamageInfo(player, damage, damageTypeForTurn), AbstractGameAction.AttackEffect.SLASH_DIAGONAL));

        addToBot(new VFXAction(player, new VerticalAuraEffect(Color.BLACK, player.hb.cX, player.hb.cY), 0.15F));
        addToBot(new ApplyPowerAction(player, player, new DoomPower(player, player, magicNumber), magicNumber, true));
        if(!player.hasPower(ArtifactPower.POWER_ID))
            addToBot(new ApplyPowerAction(player, player, new RemoveDoomPower(player, player, magicNumber), magicNumber, true));

        for (AbstractMonster mo: AbstractDungeon.getMonsters().monsters) {
            if(mo.isDeadOrEscaped())
                continue;
            addToBot(new VFXAction(mo, new VerticalAuraEffect(Color.BLACK, mo.hb.cX, mo.hb.cY), 0.15F));
            addToBot(new ApplyPowerAction(mo, player, new DoomPower(mo, player, magicNumber), magicNumber, true, AbstractGameAction.AttackEffect.NONE));
            if(!mo.hasPower(ArtifactPower.POWER_ID))
                addToBot(new ApplyPowerAction(mo, player, new RemoveDoomPower(mo, player, magicNumber), magicNumber, true));
        }
    }

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