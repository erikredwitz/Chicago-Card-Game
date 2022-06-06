package com.mycompany.pokermedfemkort.model;

import java.awt.Color;

public enum Suit {
    SPADE('♠'),
    CLUBS('♣'),
    DIAMONDS('♦'),
    HEARTS('♥');

    private final char symbol;
    
    private static final Color RED = new Color(255, 51, 51);

    Suit(char symbol) {
        this.symbol = symbol;
    }

    public char getSymbol() {
        return symbol;
    }

    public Color getColor()
    {
        if (this == DIAMONDS || this == HEARTS) {
            return RED;
        }
        return Color.BLACK;
    }

    public static Suit representedBy(char symbol) {
        for (Suit suit : Suit.values()) {
            if (suit.symbol == symbol) {
                return suit;
            }
        }
        return null;
    }
}
