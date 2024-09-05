package theVacant.powers;

import com.megacrit.cardcrawl.cards.AbstractCard;

public interface FreeToPlayPower {
    abstract boolean isFreeToPlay(AbstractCard card);
}
