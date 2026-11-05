package com.mycompany.pokermedfemkort.model;

import com.mycompany.pokermedfemkort.model.player.PokerBot;
import com.mycompany.pokermedfemkort.model.player.PokerPlayer;
import com.mycompany.pokermedfemkort.view.GuiDummy;

import java.sql.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

public class RandomSamplingSwapStrategy implements SwapStrategy {

    public static final int SWAP_POSSIBILITY_COUNT = 1 << PokerGame.CARD_HAND_SIZE;
    private int difficulty = 5;


    public RandomSamplingSwapStrategy(int difficulty) {
        this.difficulty = difficulty;
    }

    @Override
    public List<Card> chooseCardsToSwap(List<Card> hand, int playerCount, int position) {
        PokerSimulation simulation = new PokerSimulation();
        List<PokerPlayer> simPlayers = simulation.getPlayers();
        simulation.setPlayersToBots(playerCount);
        Deck simDeck = new Deck();
        simDeck.removeAll(hand);
        var shuffledDeck = new Deck();
        shuffledDeck.setCards(new LinkedList<>(simDeck.getCards()));
        int[] decisionEvaluation = new int[SWAP_POSSIBILITY_COUNT];
        for (int i = 0; i < (1 << difficulty); i++) {
            shuffledDeck.shuffle();
            PokerBot simPerspective = (PokerBot) (simPlayers.get(position));
            for (int swapDecision = 0; swapDecision < SWAP_POSSIBILITY_COUNT; swapDecision++) {
                var shuffledDeckCopy = shuffledDeck.getCopy();
                simulation.setDeck(shuffledDeckCopy);
                dealCards(playerCount, simPlayers, position, hand, shuffledDeckCopy);
                simulation.setScoreboard(new ScoreBoard(simPlayers));
                simulation.setPhase(new SwapPhase(simPlayers, simulation));
                simPerspective.predefineSwapDecision(swapDecision);
                simulation.swap(simPlayers, 0);
                simulation.playRound(simPlayers, 0);
                simulation.keepScore();
                decisionEvaluation[swapDecision] +=
                    simulation.getScoreBoard().getScoreOfPlayer(simPlayers.get(position));
                simulation.getScoreBoard().resetScore();
            }
        }
        int bestSwapDecision = 0;
        int maxScore = 0;
        for (int swapDecision = 0; swapDecision < SWAP_POSSIBILITY_COUNT; swapDecision++) {
            if (decisionEvaluation[swapDecision] > maxScore) {
                maxScore = decisionEvaluation[swapDecision];
                bestSwapDecision = swapDecision;
            }
        }
        printSwapEvaluation(bestSwapDecision, decisionEvaluation, hand);
        return PokerBot.getCardsForSwapPossibility(bestSwapDecision, hand);
    }

    private void printSwapEvaluation(int bestSwapIndex, int[] decisionEvaluation, List<Card> hand) {
        System.out.print("Bewertung zum Abwerfen bei Hand" + "\t");
        printCards(hand);
        System.out.print("\n");
        /* unfertige Methode um top3 zu finden
        List<Integer> top3 = new LinkedList<>();
        for (int i = 0; i < decisionEvaluation.length; i++) {
            if (top3.size() < 3 || decisionEvaluation[i] > decisionEvaluation[top3.get(2)]) {
                if (top3.size() == 3) {
                    top3.remove(2);
                }
                top3.add(i);
                top3.sort(Collections.reverseOrder());
            }
        }
         */
        for (int i = 0; i < decisionEvaluation.length; i++) {
            int x = decisionEvaluation[i];
            if (i == bestSwapIndex) {
                System.out.print("Sieger: ");
            }
            System.out.print(x + ":" + "\t");
            printCards(PokerBot.getCardsForSwapPossibility(i, hand));
            System.out.print("\n");
        }

    }

    private void printCards(List<Card> cardsForSwapPossibility) {
        for (var c : cardsForSwapPossibility) {
            System.out.print(c.getRepresentation() + "\t");
        }
    }

    private void dealCards(int playerCount, List<PokerPlayer> simPlayers, int position, List<Card> hand,
                           Deck shuffledDeck) {
        for (int j = 0; j < playerCount; j++) {
            var p = simPlayers.get(j);
            p.getLittered().clear();
            p.getPlayedCards().clear();
            if (j == position) {
                p.setHand(new LinkedList<>(hand));
            } else {
                p.getHand().clear();
                while (p.getHandSize() < PokerGame.CARD_HAND_SIZE) {
                    shuffledDeck.giveTopCard(p);
                }
            }
        }

    }

    public void setDifficulty(int difficulty) {
        this.difficulty = difficulty;
    }
}
