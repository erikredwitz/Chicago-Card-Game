package com.mycompany.pokermedfemkort.model;

import java.util.List;

public interface SwapStrategy {

    List<Card> chooseCardsToSwap(List<Card> hand, int playerCount, int position);
}
