package theVacant.cards.Skills;

import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.ArtifactPower;
import com.megacrit.cardcrawl.vfx.combat.AdrenalineEffect;
import com.megacrit.cardcrawl.vfx.combat.VerticalAuraEffect;
import theVacant.VacantMod;
import theVacant.actions.VacantMillAction;
import theVacant.cards.AbstractDynamicCard;
import theVacant.characters.TheVacant;
import theVacant.powers.DoomPower;
import theVacant.powers.RemoveDoomPower;

import static theVacant.VacantMod.makeCardPath;

public class Showdown extends AbstractDynamicCard
{

    public static final String ID = VacantMod.makeID(Showdown.class.getSimpleName());
    public static final String IMG = makeCardPath("Showdown.png");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);

    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.ALL;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = TheVacant.Enums.COLOR_GOLD;

    private static final int COST = 0;

    public Showdown()
    {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        magicNumber = baseMagicNumber = 2;
    }

    @Override
    public void use(AbstractPlayer player, AbstractMonster monster)
    {
        AbstractDungeon.effectList.add(new AdrenalineEffect());

        addToBot(new ApplyPowerAction(player, player, new DoomPower(player, player, magicNumber)));
        if(!player.hasPower(ArtifactPower.POWER_ID))
            addToBot(new ApplyPowerAction(player, player, new RemoveDoomPower(player, player, magicNumber)));

        for (AbstractMonster mo: AbstractDungeon.getMonsters().monsters) {
            if(mo.isDeadOrEscaped())
                continue;
            addToBot(new ApplyPowerAction(mo, player, new DoomPower(mo, player, magicNumber), magicNumber, true, AbstractGameAction.AttackEffect.NONE));
            if(!mo.hasPower(ArtifactPower.POWER_ID))
                addToBot(new ApplyPowerAction(mo, player, new RemoveDoomPower(mo, player, magicNumber)));
        }

        addToBot(new VacantMillAction(CardType.ATTACK, this));
    }

    @Override
    public void upgrade()
    {
        if (!upgraded)
        {
            upgradeName();
            upgradeMagicNumber(2);
            initializeDescription();
        }
    }
}