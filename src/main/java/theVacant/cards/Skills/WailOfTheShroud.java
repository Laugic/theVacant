package theVacant.cards.Skills;

import com.badlogic.gdx.graphics.Color;
import com.evacipated.cardcrawl.mod.stslib.fields.cards.AbstractCard.SoulboundField;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.LoseHPAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.ArtifactPower;
import com.megacrit.cardcrawl.vfx.BorderFlashEffect;
import com.megacrit.cardcrawl.vfx.combat.ExplosionSmallEffect;
import com.megacrit.cardcrawl.vfx.combat.VerticalAuraEffect;
import theVacant.VacantMod;
import theVacant.actions.MineGemAction;
import theVacant.actions.VacantMillAction;
import theVacant.cards.AbstractDynamicCard;
import theVacant.characters.TheVacant;
import theVacant.orbs.OnyxOrb;
import theVacant.orbs.RubyOrb;
import theVacant.powers.DoomPower;
import theVacant.powers.RemoveDoomPower;
import theVacant.powers.ShroudPower;

import static theVacant.VacantMod.makeCardPath;

public class WailOfTheShroud extends AbstractDynamicCard
{

    public static final String ID = VacantMod.makeID(WailOfTheShroud.class.getSimpleName());
    public static final String IMG = makeCardPath("WailOfTheShroud.png");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);

    private static final CardRarity RARITY = CardRarity.SPECIAL;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.CURSE;
    public static final CardColor COLOR = CardColor.CURSE;

    private static final int COST = 0, FRAIL = 2;

    public WailOfTheShroud()
    {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        magicNumber = baseMagicNumber = 1;
        //SoulboundField.soulbound.set(this, true);
        exhaust = true;
    }

    @Override
    public void use(AbstractPlayer player, AbstractMonster monster)
    {
        addToBot(new VFXAction(player, new BorderFlashEffect(Color.CYAN), 0.25F, true));
        addToBot(new VFXAction(player, new VerticalAuraEffect(Color.BLACK, player.hb.cX, player.hb.cY), 0.15F));
        addToBot(new ApplyPowerAction(player, player, new ShroudPower(player, player, magicNumber), magicNumber));
        addToBot(new LoseHPAction(player, player, 4));
    }

    @Override
    public boolean canUpgrade() {
        return !upgraded;
    }

    @Override
    public void upgrade() {
        if(!upgraded){
            upgradeName();
            upgradeMagicNumber(1);
            initializeDescription();
        }
    }
}