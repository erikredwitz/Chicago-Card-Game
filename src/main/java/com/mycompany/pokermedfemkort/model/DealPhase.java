package com.mycompany.pokermedfemkort.model;

import com.mycompany.pokermedfemkort.model.player.PokerPlayer;

import java.util.LinkedList;
import java.util.List;

public class DealPhase extends PokerPhase {

    public DealPhase(int firstPlayer, PokerGame game) {
      super(firstPlayer, game);
    }

    public DealPhase(List<PokerPlayer> orderedPlayers, PokerGame game) {
        super(orderedPlayers, game);
    }

    public boolean continuePhase() {
        game.deal(orderedPlayers);
        return true;
    }

    @Override
    public PokerPhase getNextPhase() {
        return new SwapPhase(orderedPlayers, game);
    }
}
