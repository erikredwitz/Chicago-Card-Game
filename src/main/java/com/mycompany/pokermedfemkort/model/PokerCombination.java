package com.mycompany.pokermedfemkort.model;


public enum PokerCombination implements Comparable<PokerCombination>{
    NOTHING(0, 0),
    ONE_PAIR(1, 2),
    TWO_PAIR(2, 4),
    THREE_OF_A_KIND(3, 3),
    STRAIGHT(4, PokerGame.CARD_HAND_SIZE),
    FLUSH(5, PokerGame.CARD_HAND_SIZE),
    FULL_HOUSE(6, 5),
    FOUR_OF_A_KIND(8, 4),
    STRAIGHT_FLUSH(10, PokerGame.CARD_HAND_SIZE),
    ROYAL_STRAIGHT_FLUSH(11, PokerGame.CARD_HAND_SIZE),
    IMPERIAL_ROYAL_STRAIGHT_FLUSH(12, PokerGame.CARD_HAND_SIZE);


    private final int points;
    private final int cardCount;


    PokerCombination(int points, int cardCount){
        this.points = points;
        this.cardCount = cardCount;
    }


    public int getPoints() {
        return points;
    }


    public int getCardCount() {
        return cardCount;
    }
}
