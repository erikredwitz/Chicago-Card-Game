package com.mycompany.pokermedfemkort.model;

import com.mycompany.pokermedfemkort.model.player.PokerBot;
import com.mycompany.pokermedfemkort.model.player.PokerPlayer;
import com.mycompany.pokermedfemkort.view.GuiDummy;

import java.util.LinkedList;
import java.util.List;

public class PokerSimulation extends PokerGame {
    public PokerSimulation() {
        super(new GuiDummy());
        this.gui.setGame(this);
    }

    public void setPlayersToBots(int playerCount) {
        List<PokerPlayer> simPlayers = getPlayers();
        simPlayers.clear();
        for (int i = 0; i < playerCount; i++) {
            simPlayers.add(new PokerBot(this, "p" + i + 1, new BotSwapStrategy(), new BotPlayStrategy()));
        }
    }


    public void setPossibleHandsBasedOnDeck(PokerGame original, int perspective, Deck shuffledDeck) {
        var simulation = this;
        var simPlayers = this.getPlayers();
        var originalPlayers = original.getPhase().orderedPlayers;
        simulation.setDeck(shuffledDeck);
        simulation.setScoreboard(new ScoreBoard(simPlayers));
        simulation.clearHuddle();
        for (int j = 0; j < simPlayers.size(); j++) {
            var simPlayer = simPlayers.get(j);
            var originalPlayer = originalPlayers.get(j);
            simPlayer.getLittered().clear();
            simPlayer.getLittered().addAll(originalPlayer.getLittered());
            simPlayer.getPlayedCards().clear();
            simPlayer.getPlayedCards().addAll(originalPlayer.getPlayedCards());
            if (j == perspective) {
                simPlayer.setHand(new LinkedList<>(originalPlayer.getHand()));
            } else {
                simPlayer.getHand().clear();
                while (simPlayer.getHandSize() < originalPlayer.getHandSize()) {
                    shuffledDeck.giveTopCard(simPlayer);
                }
            }
        }
        //printComparison(original, simulation);
    }

    private void printComparison(PokerGame a, PokerGame b) {
        System.out.println("played cards");
        System.out.println("game a");
        a.printPlayedCards();
        System.out.println("game b");
        b.printPlayedCards();
    }
}
