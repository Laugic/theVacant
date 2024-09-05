package theVacant.cards.Modifiers;

import basemod.BaseMod;
import basemod.abstracts.AbstractCardModifier;
import basemod.helpers.CardModifierManager;
import basemod.helpers.TooltipInfo;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.evacipated.cardcrawl.mod.stslib.util.extraicons.ExtraIcons;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theVacant.util.KeywordManager;
import theVacant.util.TextureLoader;

import java.util.ArrayList;
import java.util.List;

import static theVacant.VacantMod.makeID;
import static theVacant.VacantMod.modID;

public class SoulMod  extends AbstractCardModifier {
    public static final String ID = makeID(SoulMod.class.getSimpleName());
    public static String[] TEXT = CardCrawlGame.languagePack.getCardStrings(ID).EXTENDED_DESCRIPTION;

    public int amount = 0;
    private boolean test = false;
    private static ArrayList<TooltipInfo> PlateTip;
    private static final Texture tex = TextureLoader.getTexture(modID + "Resources/images/ui/SoulIcon.png");

    public SoulMod() {
        this(0);
    }

    public SoulMod(int amount) {
        this(amount, false);
    }

    public SoulMod(int amount, boolean test) {
        this.test = test;
        priority = 1;
        this.amount += amount;
    }

    /*@Override
    public float modifyDamage(float damage, DamageInfo.DamageType type, AbstractCard card, AbstractMonster target) {
        int mult = 1;
        if (card instanceof GearMultCard)
            mult = ((GearMultCard) card).gearMult();
        if (damage >= 0)
            damage += amount * mult;
        return damage;
    }

    @Override
    public float modifyBlock(float block, AbstractCard card) {
        int mult = 1;
        if (card instanceof GearMultCard)
            mult = ((GearMultCard) card).gearMult();
        if (block >= 0)
            block += amount * mult;
        return block;
    }*/

    @Override
    public float modifyBaseDamage(float damage, DamageInfo.DamageType type, AbstractCard card, AbstractMonster target) {
        return damage + amount;
    }

    @Override
    public float modifyBaseBlock(float block, AbstractCard card) {
        return block + amount;
    }

    @Override
    public boolean shouldApply(AbstractCard card) {
        if (CardModifierManager.hasModifier(card, ID)) {
            SoulMod SoulMod = (SoulMod) CardModifierManager.getModifiers(card, ID).get(0);
            SoulMod.amount += amount;
            if (SoulMod.amount < 0)
                SoulMod.amount = 0;
            return false;
        }
        return (card.baseBlock >= 0 || card.baseDamage >= 0);
    }

    @Override
    public void onRender(AbstractCard card, SpriteBatch sb) {
        if (amount > 0)
            ExtraIcons.icon(tex).text(String.valueOf(amount)).render(card);
    }

    @Override
    public void onSingleCardViewRender(AbstractCard card, SpriteBatch sb) {
        if (amount > 0)
            ExtraIcons.icon(tex).text(String.valueOf(amount)).render(card);
    }

    @Override
    public String identifier(AbstractCard card) {
        return ID;
    }

    @Override
    public AbstractCardModifier makeCopy() {
        return new SoulMod(amount);
    }
}