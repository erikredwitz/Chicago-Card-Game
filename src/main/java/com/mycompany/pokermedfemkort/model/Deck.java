package com.mycompany.pokermedfemkort.model;



import com.mycompany.pokermedfemkort.model.player.PokerPlayer;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.function.Predicate;

public class Deck {
    public static final int HIGHEST_VALUE = 14;
    public static final int LOWEST_VALUE = 2;
    public static final int POSSIBLE_VALUES = HIGHEST_VALUE - LOWEST_VALUE + 1;
    public static final int CARD_ATTRIBUTES = 2;
    private final List<Card> cards = new LinkedList<>();

    public int size() {
        return cards.size();
    }

    public boolean isEmpty() {
        return cards.isEmpty();
    }

    public Deck() {
        for (int i = LOWEST_VALUE; i <= HIGHEST_VALUE; i++) {
            for (Suit suit : Suit.values()) {
                cards.add(new Card(i, suit));
            }
        }
    }

    public List<Card> getCards() {
        return cards;
    }

    public void shuffle() {
        Collections.shuffle(cards);
    }

    public void giveTopCard(PokerPlayer player) {
        player.acceptCard(cards.remove(0));
    }

    public boolean add(Card card) {
        return cards.add(card);
    }

    public boolean remove(Card o) {
        return cards.remove(o);
    }

    public boolean addAll(Collection<? extends Card> c) {
        return cards.addAll(c);
    }

    public boolean addAll(int index, Collection<? extends Card> c) {
        return cards.addAll(index, c);
    }

    public boolean removeAll(Collection<?> c) {
        return cards.removeAll(c);
    }

    public void sort(Comparator<? super Card> c) {
        cards.sort(c);
    }

    public void clear() {
        cards.clear();
    }

    public Card get(int index) {
        return cards.get(index);
    }

    public void add(int index, Card element) {
        cards.add(index, element);
    }

    public Card remove(int index) {
        return cards.remove(index);
    }

    public boolean removeIf(Predicate<? super Card> filter) {
        return cards.removeIf(filter);
    }

    public boolean add(List<Card> cardList) {
        return cards.addAll(cardList);
    }

}
