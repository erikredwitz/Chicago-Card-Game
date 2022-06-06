package com.mycompany.pokermedfemkort.model;

import com.mycompany.pokermedfemkort.model.player.PokerPlayer;

import java.util.List;

public class TrickPhase extends PokerPhase {
    public TrickPhase(List<PokerPlayer> orderedPlayers, PokerGame game) {
        super(orderedPlayers, game);
    }

    @Override
    public boolean continuePhase() {
        return game.playRound(orderedPlayers, playerInTurn);
    }

    @Override
    public PokerPhase getNextPhase() {
        game.prepareNextRound();
        orderedPlayers.add(orderedPlayers.remove(0));//let next player begin
        return new DealPhase(orderedPlayers, game);
    }
}
