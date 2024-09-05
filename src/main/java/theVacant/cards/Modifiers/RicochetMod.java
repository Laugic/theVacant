package theVacant.cards.Modifiers;

import basemod.BaseMod;
import basemod.abstracts.AbstractCardModifier;
import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import theVacant.cards.AbstractVacantCard;
import theVacant.cards.Skills.FromNothing;
import theVacant.powers.VoidPower;
import theVacant.util.KeywordManager;

import java.util.ArrayList;

import static basemod.helpers.CardModifierManager.addModifier;
import static basemod.helpers.CardModifierManager.getModifiers;

public class RicochetMod extends AbstractCardModifier
{
    private int amount;
    public static String ID = "VacantRicochetModifier";

    public RicochetMod()
    {
    }

    @Override
    public String modifyDescription(String rawDescription, AbstractCard card)
    {
        return FromNothing.cardStrings.EXTENDED_DESCRIPTION[0] + rawDescription;
    }

    @Override
    public String identifier(AbstractCard card)
    {
        return ID;
    }

    @Override
    public AbstractCardModifier makeCopy()
    {
        return new RicochetMod();
    }
}