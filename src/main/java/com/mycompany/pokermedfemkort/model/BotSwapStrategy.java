package com.mycompany.pokermedfemkort.model;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;

public class BotSwapStrategy implements SwapStrategy {
    public static final int KEEP_COMBINATIONS_MAX = PokerCombination.IMPERIAL_ROYAL_STRAIGHT_FLUSH.getPoints() + 1;
    public static final int KEEP_COMBINATIONS_MIN = PokerCombination.ONE_PAIR.getPoints();
    public static final int KEEP_VALUE_MAX = Deck.HIGHEST_VALUE + 1;
    public static final int KEEP_VALUE_MIN = Deck.LOWEST_VALUE;
    public static final int TRY_FLUSH_MAX = PokerGame.CARD_HAND_SIZE;
    public static final int TRY_FLUSH_MIN = PokerGame.CARD_HAND_SIZE - 2;

    //Karten von fertigen Kombinationen behalten: Werte von 1 bis 13
    private final int keepCombinationsFromWorth = 2;
    private final int keepCardsFromValue = 10;
    //zum Beispiel bei Karo König, auch Karo 9 behalten, wenn keepCardsFromValue = 10 ist.
    //Werte von 2 bis 15, und 15 heißt: Taktik wird nicht verwendet
    private final int keepSuitFromValue = 13;
    //z. B. schon ab 3 gleichfarbigen Karten auf Farbe sparen.
    private final int keepSuitFromCount = 4;
    private final boolean keepFourStraightWithoutGap = true;
    private final boolean keepFourStraightWithGap = false;
  


    @Override
    public List<Card> chooseCardsToSwap(List<Card> hand) {
        HashSet<Card> keep = new HashSet<>();
        var pokerHand = PokerHand.findCombination(hand);
        if (pokerHand.getCombination().getPoints() >= keepCombinationsFromWorth) {
            keep.addAll(pokerHand.getInvolvedCards());
        }
        for (Card card : hand) {
            if (card.getValue() >= keepCardsFromValue) {
                keep.add(card);
            }
        }
        List<Card> throwList = new LinkedList<>();
        for (Card card : hand) {
            if (!keep.contains(card)) {
                throwList.add(card);
            }
        }
        return throwList;
    }

}
