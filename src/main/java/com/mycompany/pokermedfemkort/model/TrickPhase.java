package com.mycompany.pokermedfemkort.model;

import com.mycompany.pokermedfemkort.model.player.PokerPlayer;

import java.util.List;

public class TrickPhase extends PokerPhase {
    public TrickPhase(List<PokerPlayer> orderedPlayers, PokerGame game) {
        super(orderedPlayers, game);
    }

    public TrickPhase(int firstPlayer, PokerGame game) {
        super(firstPlayer, game);
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

    @Override
    public PokerPhase getSamePhase(int firstPlayer, PokerGame game) {
        return new TrickPhase(firstPlayer, game);
    }
}
