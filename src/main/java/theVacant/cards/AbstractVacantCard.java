package theVacant.cards;

import basemod.ReflectionHacks;
import basemod.abstracts.CustomCard;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.evacipated.cardcrawl.modthespire.lib.SpireOverride;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import theVacant.VacantMod;
import theVacant.powers.InvisibleDebuffTracker;
import theVacant.powers.RiftPower;
import theVacant.util.TextureLoader;

import java.lang.reflect.Method;

import static theVacant.VacantMod.modID;

public abstract class AbstractVacantCard extends CustomCard
{
    public boolean isUnnate;
    public boolean isHeavy;
    public boolean ricochet;
    public boolean postMillAction;
    public boolean getBonusMillToMagic;
    public boolean displayFracturedTooltip;
    public int bonusMillAmount;
    public boolean displayWill;
    public boolean hasUpgradeDesc;
    public boolean prime;
    public boolean checkHollow;
    private CardStrings cardStrings = null;
    private static final CardStrings cardStringsAbstract = CardCrawlGame.languagePack.getCardStrings(VacantMod.makeID(AbstractVacantCard.class.getSimpleName()));

    private static final Texture back = TextureLoader.getTexture(modID + "Resources/images/512/face_down.png");
    private TextureAtlas.AtlasRegion back_atlas = new TextureAtlas.AtlasRegion(back, 0, 0, back.getWidth(), back.getHeight());

    public AbstractVacantCard(final String ID,
                              final String name,
                              final String img,
                              final int cost,
                              final String rawDescription,
                              final CardType type,
                              final CardColor color,
                              final CardRarity rarity,
                              final CardTarget target) {

        super(ID, name, img, cost, rawDescription, type, color, rarity, target);
        isCostModified = false;
        isCostModifiedForTurn = false;
        isDamageModified = false;
        isBlockModified = false;
        isMagicNumberModified = false;
        isUnnate = false;
        isHeavy = false;
        ricochet = false;
        postMillAction = false;
        getBonusMillToMagic = false;
        checkHollow = false;
        displayFracturedTooltip = false;
        bonusMillAmount = 0;
        displayWill = false;
        hasUpgradeDesc = false;
        prime = true;
        cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    }

    public void PostMillAction()
    {

    }
/*
    @SpireOverride
    protected void renderBack(SpriteBatch sb, boolean hovered, boolean selected) {
        ReflectionHacks.privateMethod(AbstractCard.class, "renderHelper", SpriteBatch.class, Color.class, TextureAtlas.AtlasRegion.class, float.class, float.class).invoke(this, sb, ReflectionHacks.getPrivate(this, AbstractCard.class, "renderColor"), back_atlas, current_x, current_y);
    }
*/
    @Override
    public void triggerOnGlowCheck() {
        if(checkHollow)
        {
            if(getHollow())
                glowColor = Color.PURPLE;
            else
                glowColor = AbstractCard.BLUE_BORDER_GLOW_COLOR;
        }
    }

    public static boolean getHollow()
    {
        if(AbstractDungeon.player != null)
        {
            int numDebuffs = 0;//InvisibleDebuffTracker.debuffsThisTurn;
            for (AbstractPower power: AbstractDungeon.player.powers) {
                if(power.type == AbstractPower.PowerType.DEBUFF)
                    numDebuffs++;
            }
            /*if(AbstractDungeon.player.hasPower(RiftPower.POWER_ID))
                numDebuffs += ((RiftPower)AbstractDungeon.player.getPower(RiftPower.POWER_ID)).debuffs.size();*/
            if(AbstractDungeon.player.isBloodied || numDebuffs > 0)
                return true;
        }
        return false;
    }
}
