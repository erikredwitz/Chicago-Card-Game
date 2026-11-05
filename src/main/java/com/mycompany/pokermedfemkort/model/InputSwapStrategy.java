package com.mycompany.pokermedfemkort.model;

import java.util.LinkedList;
import java.util.List;

public class InputSwapStrategy implements SwapStrategy {

    private boolean[] swapDecision;


    @Override
    public List<Card> chooseCardsToSwap(List<Card> hand, int playerCount, int position) {
        List<Card> cardsToSwap = new LinkedList<>();
        for (int i = 0; i < PokerGame.CARD_HAND_SIZE; i++) {
            if (swapDecision[i]) {
                cardsToSwap.add(hand.get(i));
            }
        }
        return cardsToSwap;
    }

    public void swapInput(boolean[] swapDecision) {
        this.swapDecision = swapDecision;
    }
}
