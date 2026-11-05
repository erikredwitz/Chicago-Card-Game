package com.mycompany.pokermedfemkort.model;

import com.mycompany.pokermedfemkort.model.player.PokerBot;
import com.mycompany.pokermedfemkort.model.player.PokerPlayer;

import java.util.LinkedList;
import java.util.List;

public class RandomSamplingPlayStrategy extends PlayStrategy {

    int difficulty;

    public RandomSamplingPlayStrategy(int difficulty) {
        this.difficulty = difficulty;
    }

    @Override
    public Card chooseCardToPlay(Suit led, int value, List<PokerPlayer> players, PokerPlayer perspective) {
        PokerSimulation simulation = new PokerSimulation();
        var originalGame = perspective.getGame();
        simulation.setPlayersToBots(originalGame.getPlayerCount());
        int perspectiveIndex = PokerPhase.getPlayerIndex(perspective, originalGame.getPhase().orderedPlayers);
        var simPlayers = simulation.getPlayers();
        PokerBot simPerspective = (PokerBot) (simPlayers.get(perspectiveIndex));
        int[] decisionEvaluation = new int[perspective.getHandSize()];
        var deckWithCorrectCards = createDeckWithCorrectCards(originalGame, perspective);
        var shuffledDeck = deckWithCorrectCards.getCopy();
        for (int i = 0; i < (1 << difficulty); i++) {
            shuffledDeck.shuffle();
            List<Integer> playable;
            if (led == null) {
                playable = PokerGame.listFromZeroTo(hand.size());
            } else {
                playable = PokerGame.getPlayable(hand, led, value);
            }
            for (int playDecision = 0; playDecision < hand.size(); playDecision++) {
                if (playable.contains(playDecision)) {
                    var shuffledDeckCopy = shuffledDeck.getCopy();
                    simulation.setPossibleHandsBasedOnDeck(originalGame, perspectiveIndex, shuffledDeckCopy);
                    simPerspective.predefinePlayDecision(playDecision);
                    simulation.playRound(new LinkedList<>(simPlayers), perspectiveIndex);
                    simulation.keepScore();
                    decisionEvaluation[playDecision] += simulation.getScoreBoard().getScoreOfPlayer(simPerspective);
                    simulation.getScoreBoard().resetScore();
                }
            }
        }
        int bestDecision = 0;
        int maxScore = 0;
        for (int swapDecision = 0; swapDecision < hand.size(); swapDecision++) {
            if (decisionEvaluation[swapDecision] > maxScore) {
                maxScore = decisionEvaluation[swapDecision];
                bestDecision = swapDecision;
            }
        }
        return hand.get(bestDecision);
    }

    public Deck createDeckWithCorrectCards(PokerGame original, PokerPlayer perspective) {
        Deck simDeck = new Deck();
        List<PokerPlayer> originalPlayers = original.getPhase().orderedPlayers;
        simDeck.removeAll(perspective.getHand());
        simDeck.removeAll(perspective.getLittered());
        for (var p : originalPlayers) {
            simDeck.removeAll(p.getPlayedCards());
        }
        return simDeck;
    }

    @Override
    public Card chooseCardToBeginWith(List<PokerPlayer> players, PokerPlayer perspective) {
        return chooseCardToPlay(null, 0, players, perspective);
    }

    public void setDifficulty(int difficulty) {
        this.difficulty = difficulty;
    }
}
