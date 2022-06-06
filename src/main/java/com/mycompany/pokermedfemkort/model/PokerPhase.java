package com.mycompany.pokermedfemkort.model;

import com.mycompany.pokermedfemkort.model.player.PokerPlayer;

import java.util.LinkedList;
import java.util.List;

public abstract class PokerPhase {
    protected int playerInTurn = 0; //wer dran ist (Index in orderedPlayers)

    protected List<PokerPlayer> orderedPlayers; //geordnet mit Geber am Ende


    protected PokerGame game;

    protected PokerPhase(int firstPlayer, PokerGame game) {
        this.game = game;
        orderedPlayers = new LinkedList<>(game.getPlayers());
        for (int i = 0; i < firstPlayer; i++) {
            orderedPlayers.add(orderedPlayers.remove(0));
        }
    }

    protected PokerPhase(List<PokerPlayer> orderedPlayers, PokerGame game) {
        this.game = game;
        this.orderedPlayers = orderedPlayers;
    }

    public abstract boolean continuePhase();

    public abstract PokerPhase getNextPhase();

    public int getPlayerInTurn() {
        return playerInTurn;
    }

    public void setPlayerInTurn(int playerInTurn) {
        this.playerInTurn = playerInTurn;
    }

    public void setFirstPlayer(PokerPlayer firstPlayer) {
        for(int i = 0; i < orderedPlayers.size() && orderedPlayers.get(0) != firstPlayer; i++) {
            orderedPlayers.add(orderedPlayers.remove(0));
        }
    }
}
