package com.mycompany.pokermedfemkort.model;

import java.util.Objects;

public class Card implements Comparable<Card> {
    private final int value;
    private final Suit suit;

    public Card(int value, Suit suit) {
        this.value = value;
        this.suit = suit;
    }

    public int getValue() {
        return value;
    }

    @Override
    public int compareTo(Card o) {
        return Integer.compare(this.value, o.value);
    }

    public Suit getSuit() {
        return suit;
    }

    public boolean hasSameValue(Card other) {
        return this.value == other.value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Card card = (Card) o;
        return value == card.value &&
            suit == card.suit;
    }

    @Override
    public int hashCode() {
        return Objects.hash(value, suit);
    }

    public String getRepresentation() {
        return suit.getSymbol() + getValueSymbol(value);
    }

    private String getValueSymbol(int value) {
        if (value <= 10) {
            return String.valueOf(value);
        }
        switch (value) {
            case 11:
                return "B";
            case 12:
                return "D";
            case 13:
                return "K";
            case 14:
                return "A";
            default:
                return "?";
        }
    }
}
