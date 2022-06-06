package com.mycompany.pokermedfemkort.model;



import com.mycompany.pokermedfemkort.model.player.PokerPlayer;
import java.util.List;

public abstract class PlayStrategy {
    protected List<Card> hand;

    public abstract Card chooseCardToPlay(Suit led, int value, List<PokerPlayer> players, PokerPlayer perspective);

    public abstract Card chooseCardToBeginWith(List<PokerPlayer> players, PokerPlayer perspective);

    public void setHand(List<Card> hand) {
        this.hand = hand;
    }


}
