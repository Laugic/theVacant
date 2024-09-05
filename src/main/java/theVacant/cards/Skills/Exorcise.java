package theVacant.cards.Skills;

import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.vfx.ShineSparkleEffect;
import com.megacrit.cardcrawl.vfx.combat.MindblastEffect;
import theVacant.VacantMod;
import theVacant.cards.AbstractDynamicCard;
import theVacant.characters.TheVacant;
import theVacant.util.TextureLoader;
import theVacant.vfx.ParticleEffect;

import static theVacant.VacantMod.makeCardPath;

public class Exorcise extends AbstractDynamicCard
{

    public static final String ID = VacantMod.makeID(Exorcise.class.getSimpleName());
    public static final String IMG = makeCardPath("Exorcise.png");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;

    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = TheVacant.Enums.COLOR_GOLD;


    private static final int COST = 1;
    private static Texture crossTexture =  TextureLoader.getTexture("theVacantResources/images/vfx/Cross.png");

    public Exorcise()
    {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        exhaust = true;
    }

    @Override
    public void use(AbstractPlayer player, AbstractMonster monster)
    {
        boolean hadDebuff = false, inflictedDebuff = false;
        for(AbstractPower power: player.powers)
        {
            if(power.type.equals(AbstractPower.PowerType.DEBUFF))
            {
                hadDebuff = true;
                addToBot(new RemoveSpecificPowerAction(player, player, power));
                if(!VacantMod.IMMUNE_POWERS.contains(power.ID))
                {
                    if(!inflictedDebuff)
                    {
                        inflictedDebuff = true;
                        AbstractDungeon.actionManager.addToBottom(new VFXAction(player, new MindblastEffect(player.dialogX, player.dialogY, player.flipHorizontal), 0.1F));
                    }
                    for (AbstractMonster mo : (AbstractDungeon.getCurrRoom()).monsters.monsters)
                    {
                        power.owner = mo;
                        addToBot(new ApplyPowerAction(mo, player, power));
                    }
                }
            }
        }
        if(hadDebuff)
        {
            for(int i = 0; i < 20; ++i)
                AbstractDungeon.effectsQueue.add(new ShineSparkleEffect(player.hb.x + (float)Math.random() * player.hb.width, player.hb.y + (float)Math.random() * player.hb.height));

            ParticleEffect cross = new ParticleEffect(player.hb.cX, player.hb.cY, crossTexture, 1, .5f, false, .3f);
            cross.scaleMult = .5f;
            cross.yVel = 60;
            AbstractDungeon.effectsQueue.add(cross);
        }
    }

    @Override
    public void upgrade()
    {
        if (!upgraded)
        {
            upgradeName();
            selfRetain = true;
            rawDescription = cardStrings.UPGRADE_DESCRIPTION;
            initializeDescription();
        }
    }
}