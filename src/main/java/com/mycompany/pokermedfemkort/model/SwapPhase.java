package com.mycompany.pokermedfemkort.model;

import com.mycompany.pokermedfemkort.model.player.PokerPlayer;

import java.util.List;

public class SwapPhase extends PokerPhase {
    public SwapPhase(List<PokerPlayer> orderedPlayers, PokerGame game) {
        super(orderedPlayers, game);
    }

    @Override
    public boolean continuePhase() {
        return game.swap(orderedPlayers, playerInTurn);
    }

    public PokerPhase getNextPhase() {
        return new TrickPhase(orderedPlayers, game);
    }
}
