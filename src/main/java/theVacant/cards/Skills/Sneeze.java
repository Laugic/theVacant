package theVacant.cards.Skills;

import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.cards.status.Slimed;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.combat.CleaveEffect;
import com.megacrit.cardcrawl.vfx.combat.MindblastEffect;
import theVacant.VacantMod;
import theVacant.actions.VacantMillAction;
import theVacant.cards.AbstractDynamicCard;
import theVacant.characters.TheVacant;
import theVacant.vfx.HorizontalThrowVFX;

import static theVacant.VacantMod.makeCardPath;

public class Sneeze extends AbstractDynamicCard
{

    public static final String ID = VacantMod.makeID(Sneeze.class.getSimpleName());
    public static final String IMG = makeCardPath("Sneeze.png");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);

    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = TheVacant.Enums.COLOR_GOLD;

    private static final int COST = 0, STARTMILL = 4, UPGRADE = 1;
    private int retainBonus = 0;
    public Sneeze()
    {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        magicNumber = baseMagicNumber = STARTMILL;
        selfRetain = true;
//        exhaust = true;
        getBonusMillToMagic = true;
        retainBonus = 0;
        cardsToPreview = new Slimed();
    }

    @Override
    public void use(AbstractPlayer player, AbstractMonster m)
    {
        if(magicNumber > 10)
        {
            addToBot(new SFXAction("theVacant:sneezeBig1"));
            addToBot(new WaitAction(.8f));
            addToBot(new VFXAction(player, new MindblastEffect(player.dialogX, player.dialogY, player.flipHorizontal), 0.1F));
        }
        else if(magicNumber >= 8)
        {
            addToBot(new VFXAction(player, new CleaveEffect(), 0.1F));
            addToBot(new SFXAction("theVacant:sneezeSmall1"));
        }
        else
        {
            AbstractMonster left = AbstractDungeon.getRandomMonster();
            addToBot(new VFXAction(new HorizontalThrowVFX(left.hb.cX, left.hb.cY, new Color(1, 1, 1, 1))));
            AbstractDungeon.actionManager.addToBottom(new SFXAction("theVacant:sneezeSmall1"));
        }

        AbstractDungeon.actionManager.addToBottom(new VacantMillAction(magicNumber, this));

        addToBot(new MakeTempCardInHandAction(new Slimed()));
    }

    @Override
    public void onRetained()
    {
        retainBonus += 2;
        super.onRetained();
    }
    @Override
    public void applyPowers()
    {
        magicNumber = baseMagicNumber = STARTMILL + retainBonus + (upgraded?UPGRADE:0);
        isMagicNumberModified = (retainBonus > 0);
        super.applyPowers();
    }

    @Override
    public void atTurnStart()
    {
        magicNumber = baseMagicNumber = STARTMILL + retainBonus + (upgraded?UPGRADE:0);
        isMagicNumberModified = (retainBonus > 0);
        super.atTurnStart();
    }
    @Override
    public void upgrade()
    {
        if (!upgraded)
        {
            upgradeName();
            isInnate = true;
            upgradeMagicNumber(UPGRADE);
            upgradedMagicNumber = true;
            rawDescription = cardStrings.UPGRADE_DESCRIPTION;
            initializeDescription();
        }
    }
}