package com.mycompany.pokermedfemkort.view;

import com.mycompany.pokermedfemkort.model.Card;
import com.mycompany.pokermedfemkort.model.PokerGame;
import com.mycompany.pokermedfemkort.model.player.PokerPlayer;

import java.util.List;

public class GuiDummy extends PokerGUI {
    public GuiDummy() {
        super();
    }

    @Override
    public void writeNames(List<PokerPlayer> players) {
    }

    @Override
    public void preparePlayCard(List<Card> cards, boolean[] enabled) {
    }

    @Override
    public void preparePlayCard(List<Card> cards) {}

    @Override
    public void updatePlayedCards() {
        //game.printPlayedCards();
    }

    @Override
    public void prepareSwapCards(List<Card> hand) {
    }

    @Override
    public void showScoreboardChanges(PokerPlayer combinationWinner, int combinationPoints,
                                      PokerPlayer lastTrickWinner) {
    }

    @Override
    public void updateScoreboard() {
    }

    @Override
    public void showNextRoundButton() {
    }

    @Override
    public void updateFont() {
    }
}
