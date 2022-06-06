package com.mycompany.pokermedfemkort.model;

import com.mycompany.pokermedfemkort.model.player.PokerPlayer;
import java.util.List;

public class BotPlayStrategy extends PlayStrategy {
     

    @Override
    public Card chooseCardToPlay(Suit led, int value, List<PokerPlayer> players, PokerPlayer perspective) {
        List<Integer> allowed = PokerGame.getPlayable(hand, led, value);
        int i = 0;
        var card = hand.get(i);
        while (!allowed.contains(i)) {
            i++;
            card = hand.get(i);
        }
        return card;
    }

    @Override
    public Card chooseCardToBeginWith(List<PokerPlayer> players, PokerPlayer perspective) {
        return hand.get(0);
    }

  
}
