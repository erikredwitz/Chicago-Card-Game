package com.mycompany.pokermedfemkort.model;




import com.mycompany.pokermedfemkort.model.player.PokerPlayer;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import static com.mycompany.pokermedfemkort.model.PokerCombination.FOUR_OF_A_KIND;
import static com.mycompany.pokermedfemkort.model.PokerCombination.FULL_HOUSE;
import static com.mycompany.pokermedfemkort.model.PokerCombination.NOTHING;
import static com.mycompany.pokermedfemkort.model.PokerCombination.ONE_PAIR;
import static com.mycompany.pokermedfemkort.model.PokerCombination.THREE_OF_A_KIND;
import static com.mycompany.pokermedfemkort.model.PokerCombination.TWO_PAIR;


public final class PokerHand implements Comparable<PokerHand> {
    public static final int ROYAL_STRAIGHT_FLUSH_LOWEST = 10;
    private static final Suit IMPERIAL_ROYAL_STRAIGHT_FLUSH_SUIT = Suit.HEARTS;
    private static final int ONE_PAIR_NUMBER_OF_SAME_VALUES = 1;
    private static final int FULL_HOUSE_EQUAL_VALUES = 2;
    private static final int ONE_PAIR_INVOLVED = 2;
    private static final int THREE_OF_A_KIND_INVOLVED = 3;
    private static final int FOUR_OF_A_KIND_INVOLVED = 4;
    private PokerPlayer player;
    private final PokerCombination combination;
    private final List<Card> involvedCards;


    public PokerHand(PokerPlayer player, PokerCombination combination, List<Card> involvedCards) {
        this.player = player;
        this.combination = combination;
        this.involvedCards = involvedCards;
    }
    public PokerHand(PokerCombination combination, List<Card> involvedCards) {
        this.combination = combination;
        this.involvedCards = involvedCards;
    }


    public static PokerHand getPokerHandWithPlayer(PokerPlayer player) {
        List<Card> hand = new LinkedList<>(player.getPlayedCards());
        var pokerHand = findCombination(hand);
        pokerHand.setPlayer(player);
        return pokerHand;
    }


    public static PokerHand findCombination(List<Card> hand) {
        if (hand.size() != PokerGame.CARD_HAND_SIZE) {
            return noCombination();
        }
        Collections.sort(hand);
        if (isFlush(hand)) {
            return new PokerHand(findFlushCombination(hand), hand);
        }
        if (isStraight(hand)) {
            return new PokerHand(PokerCombination.STRAIGHT, hand);
        }
        List<List<Card>> sameValueListList = getSameValueListList(hand);
        if (sameValueListList.isEmpty()) {
            return noCombination();
        }
        if (sameValueListList.size() == ONE_PAIR_NUMBER_OF_SAME_VALUES) {
            return new PokerHand(getOneValueCombination(sameValueListList), sameValueListList.get(0));
        } else {
            List<Card> involvedCards = new LinkedList<>();
            for (List<Card> list : sameValueListList) {
                involvedCards.addAll(list);
            }
            return new PokerHand(getTwoValuesCombination(involvedCards.size()), involvedCards);
        }
    }

    private static PokerCombination getTwoValuesCombination(int numberOfInvolvedCards) {
        if (numberOfInvolvedCards == TWO_PAIR.getCardCount()) {
            return TWO_PAIR;
        }
        return FULL_HOUSE;
    }

    private static PokerCombination getOneValueCombination(List<List<Card>> sameValueListList) {
        List<Card> involvedCards = sameValueListList.get(0);
        switch (involvedCards.size()) {
            case ONE_PAIR_INVOLVED:
                return ONE_PAIR;
            case THREE_OF_A_KIND_INVOLVED:
                return THREE_OF_A_KIND;
            case FOUR_OF_A_KIND_INVOLVED:
                return FOUR_OF_A_KIND;
            default:
                return NOTHING;
        }
    }


    private static List<List<Card>> getSameValueListList(List<Card> hand) {
        List<Card> cardsToLookAt = new LinkedList<>(hand);
        List<List<Card>> sameValueListList = new LinkedList<>();
        for (int i = 0; i < cardsToLookAt.size() - 1; i++) {
            List<Card> sameValueList = new LinkedList<>();
            for (int j = i + 1; j < cardsToLookAt.size(); j++) {
                if (cardsToLookAt.get(i).hasSameValue(cardsToLookAt.get(j))) {
                    sameValueList.add(cardsToLookAt.get(j));
                }
            }
            if (!sameValueList.isEmpty()) {
                sameValueList.add(cardsToLookAt.get(i));
                sameValueListList.add(sameValueList);
                cardsToLookAt.removeAll(sameValueList);
                i = -1;
            }
        }
        return sameValueListList;
    }

    private static PokerCombination findFlushCombination(List<Card> hand) {
        if (isStraight(hand)) {
            if (hand.get(0).getValue() == ROYAL_STRAIGHT_FLUSH_LOWEST) {
                if (hand.get(0).getSuit() == IMPERIAL_ROYAL_STRAIGHT_FLUSH_SUIT) {
                    return PokerCombination.IMPERIAL_ROYAL_STRAIGHT_FLUSH;
                }
                return PokerCombination.ROYAL_STRAIGHT_FLUSH;
            }
            return PokerCombination.STRAIGHT_FLUSH;
        }
        return PokerCombination.FLUSH;
    }

    private static PokerHand noCombination() {
        return new PokerHand(NOTHING, Collections.emptyList());
    }


    private static boolean isStraight(List<Card> hand) {
        List<Card> cardsToLookAt = new LinkedList<>(hand);
        if (cardsToLookAt.get(cardsToLookAt.size() - 1).getValue() == Deck.HIGHEST_VALUE
            && cardsToLookAt.get(0).getValue() == Deck.LOWEST_VALUE) {
            cardsToLookAt.remove(cardsToLookAt.get(cardsToLookAt.size() - 1));
        }
        int previousValue = cardsToLookAt.get(0).getValue();
        for (int i = 1; i < cardsToLookAt.size(); i++) {
            if (cardsToLookAt.get(i).getValue() != previousValue + 1) {
                return false;
            }
            previousValue = cardsToLookAt.get(i).getValue();
        }
        return true;
    }

    //sortierte Liste als Parameter
    private static boolean isFlush(List<Card> hand) {
        if (hand.size() != PokerGame.CARD_HAND_SIZE) {
            return false;
        }
        for (int i = 1; i < hand.size(); i++) {
            if (hand.get(i).getSuit() != hand.get(0).getSuit()) {
                return false;
            }
        }
        return true;
    }


    @Override
    public int compareTo(PokerHand other) {
        if (this.combination != other.combination) {
            return this.combination.compareTo(other.combination);
        }
        switch (combination) {
            case NOTHING:
                return 0;
            case ONE_PAIR:
            case TWO_PAIR:
            case THREE_OF_A_KIND:
            case FOUR_OF_A_KIND:
                return comparePartOfHandCombinations(other);
            case FULL_HOUSE:
                return compareFullHouse(other);
            default:
                return compareHighestCard(other);
        }
    }

    private int compareFullHouse(PokerHand other) {
        List<List<Card>> thisSameValueListList = getSameValueListList(this.involvedCards);
        List<List<Card>> otherSameValueListList = getSameValueListList(other.involvedCards);
        List<Card> thisThreeOfAKindInvolved = new LinkedList<>();
        List<Card> otherThreeOfAKindInvolved = new LinkedList<>();
        for (int i = 0;
             i < FULL_HOUSE_EQUAL_VALUES && (thisThreeOfAKindInvolved.isEmpty() || otherThreeOfAKindInvolved.isEmpty());
             i++) {
            if (thisSameValueListList.get(i).size() == THREE_OF_A_KIND.getCardCount()) {
                thisThreeOfAKindInvolved.addAll(thisSameValueListList.get(i));
            }
            if (otherSameValueListList.get(i).size() == THREE_OF_A_KIND.getCardCount()) {
                otherThreeOfAKindInvolved.addAll(otherSameValueListList.get(i));
            }
        }
        return thisThreeOfAKindInvolved.get(0).compareTo(otherThreeOfAKindInvolved.get(0));
    }

    private int compareHighestCard(PokerHand other) {
        this.involvedCards.sort(Collections.reverseOrder());
        other.involvedCards.sort(Collections.reverseOrder());
        for (int i = 0; i < this.involvedCards.size(); i++) {
            Card thisCard = this.involvedCards.get(i);
            Card otherCard = other.involvedCards.get(i);
            int compare = thisCard.compareTo(otherCard);
            if (compare != 0) {
                return compare;
            }
        }
        Card thisHighest = this.involvedCards.get(0);
        Card otherHighest = other.involvedCards.get(0);
        return thisHighest.getSuit().compareTo(otherHighest.getSuit());
    }

    private int comparePartOfHandCombinations(PokerHand other) {
        this.involvedCards.sort(Collections.reverseOrder());
        other.involvedCards.sort(Collections.reverseOrder());
        int compare;
        if (involvedCards.size() == TWO_PAIR.getCardCount()) {
            compare = compareTwoPair(other);
        } else {
            compare = compareOneToThreeOfAKind(other);
        }
        if (compare != 0) {
            return compare;
        }
        return compareSideCards(other);
    }

    private int compareOneToThreeOfAKind(PokerHand other) {
        return this.involvedCards.get(0).compareTo(other.involvedCards.get(0));
    }

    private int compareTwoPair(PokerHand other) {
        for (int i = 0; i < involvedCards.size() - ONE_PAIR.getCardCount(); i += ONE_PAIR.getCardCount()) {
            int compare = this.involvedCards.get(i).compareTo(other.involvedCards.get(i));
            if (compare != 0) {
                return compare;
            }
        }
        return 0;
    }

    private int compareSideCards(PokerHand other) {
        List<Card> sideCardsOne = new LinkedList<>(this.player.getPlayedCards());
        List<Card> sideCardsTwo = new LinkedList<>(other.player.getPlayedCards());
        sideCardsOne.removeAll(this.involvedCards);
        sideCardsTwo.removeAll(other.involvedCards);
        sideCardsOne.sort(Collections.reverseOrder());
        sideCardsTwo.sort(Collections.reverseOrder());
        return sideCardsOne.get(0).compareTo(sideCardsTwo.get(0));
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        PokerHand other = (PokerHand) obj;
        return this.combination == other.combination;
    }

    public PokerPlayer getPlayer() {
        return player;
    }

    public PokerCombination getCombination() {
        return combination;
    }

    public List<Card> getInvolvedCards() {
        return involvedCards;
    }

    public void setPlayer(PokerPlayer player) {
        this.player = player;
    }
}
