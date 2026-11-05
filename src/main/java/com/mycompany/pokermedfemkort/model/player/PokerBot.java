/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.mycompany.pokermedfemkort.model.player;

import com.mycompany.pokermedfemkort.model.Card;
import com.mycompany.pokermedfemkort.model.PlayStrategy;
import com.mycompany.pokermedfemkort.model.PokerGame;
import com.mycompany.pokermedfemkort.model.Suit;
import com.mycompany.pokermedfemkort.model.SwapStrategy;

import java.util.LinkedList;
import java.util.List;

/**
 * @author erikr
 */
public class PokerBot extends PokerPlayer {

    boolean isSwapPredefined = false;
    int swapDefinition = 0;
    boolean isPlayPredefined = false;
    int playDefinition = 0;

    public PokerBot(PokerGame game, String name, SwapStrategy swapStrategy, PlayStrategy playStrategy) {
        super(game, name, swapStrategy,
            playStrategy, false);
    }

    public void predefineSwapDecision(int swapDefinition) {
        this.swapDefinition = swapDefinition;
        isSwapPredefined = true;
    }

    public void predefinePlayDecision(int playDefinition) {
        this.playDefinition = playDefinition;
        isPlayPredefined = true;
    }

    @Override
    public Card chooseCardToPlay(Suit led, int value) {
        playStrategy.setHand(hand);
        if (isPlayPredefined) {
            if(playDefinition >= hand.size()){
                System.out.println("falsch");
            }
            isPlayPredefined = false;
            return hand.get(playDefinition);
        } else {
            return super.chooseCardToPlay(led, value);
        }
    }

    @Override
    public Card chooseCardToBeginWith() {
        playStrategy.setHand(hand);
        if (isPlayPredefined) {
            if(playDefinition >= hand.size()){
                System.out.println("falsch");
            }
            isPlayPredefined = false;
            return hand.get(playDefinition);
        } else {
            return super.chooseCardToBeginWith();
        }
    }

    @Override
    public List<Card> chooseCardsToSwap() {
        if (isSwapPredefined) {
            isSwapPredefined = false;
            return getCardsForSwapPossibility(swapDefinition, hand);
        } else {
            return super.chooseCardsToSwap();
        }
    }

    public static List<Card> getCardsForSwapPossibility(int swapPossibility, List<Card> hand) {
        boolean[] keepArray = new boolean[PokerGame.CARD_HAND_SIZE];
        for (int i = 0; i < keepArray.length; i++) {
            keepArray[i] = ((1 << i) & swapPossibility) != 0;
        }
        List<Card> cardsToSwap = new LinkedList<>();
        for (int i = 0; i < hand.size(); i++) {
            if (!keepArray[i]) {
                cardsToSwap.add(hand.get(i));
            }
        }
        return cardsToSwap;
    }

    @Override
    public void playCard(Card card) {
        super.playCard(card);
        if(isPlayPredefined){
            System.out.println("falsch: play immer noch predefined");
        }
    }
}
