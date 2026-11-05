package com.mycompany.pokermedfemkort.model.player;


import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import com.mycompany.pokermedfemkort.model.Card;
import com.mycompany.pokermedfemkort.model.PlayStrategy;
import com.mycompany.pokermedfemkort.model.PokerGame;
import com.mycompany.pokermedfemkort.model.Suit;
import com.mycompany.pokermedfemkort.model.SwapStrategy;

public abstract class PokerPlayer {

    private final PokerGame game;
    protected final List<Card> hand = new LinkedList<>();
    private final String name;
    private final List<Card> littered = new LinkedList<>();
    private final List<Card> playedCards = new LinkedList<>();

    protected final SwapStrategy swapStrategy;
    protected final PlayStrategy playStrategy;
    private final boolean user;


    protected PokerPlayer(PokerGame game, String name, SwapStrategy swapStrategy, PlayStrategy playStrategy, boolean user) {
        this.swapStrategy = swapStrategy;
        this.playStrategy = playStrategy;
        this.user = user;
        this.game = game;
        this.name = name;

    }

    public Card chooseCardToPlay(Suit led, int value) {
        if (hand.size() == 1) {
            return hand.get(0);
        }
        playStrategy.setHand(hand);
        Card cardToPlay = playStrategy.chooseCardToPlay(led, value, game.getPlayers(), this);
        //detect cheating and change card
        cardToPlay = followTheRules(led, value, cardToPlay);
        return cardToPlay;
    }


    private Card followTheRules(Suit led, int value, Card cardToPlay) {
        if (cardToPlay.getSuit() != led) {
            for (Card card : hand) {
                if (card.getSuit() == led) {
                    cardToPlay = card;
                    break;
                }
            }
        }
        if (cardToPlay.getValue() < value && cardToPlay.getSuit() == led) {
            for (Card card : hand) {
                if (card.getValue() > value && card.getSuit() == led) {
                    cardToPlay = card;
                    break;
                }
            }
        }
        return cardToPlay;
    }

    public Card chooseCardToBeginWith() {
        playStrategy.setHand(hand);
        return playStrategy.chooseCardToBeginWith(game.getPlayers(), this);
    }

    public List<Card> litterCards(List<Card> decision) {
        littered.clear();
        littered.addAll(decision);
        hand.removeAll(littered);
        return littered;
    }

    public void playCard(Card card) {
        if (hand.contains(card)) {
            hand.remove(card);
            playedCards.add(card);
        }
        else{
            System.out.println(name + " hat nicht  " + card.getRepresentation());
        }
    }

    public void acceptCard(Card card) {
        hand.add(card);
        hand.sort(Collections.reverseOrder());
    }

    public List<Card> getPlayedCards() {
        return playedCards;
    }

    public Card getLastPlayedCard(){
        if(playedCards.isEmpty()){
            System.out.println("playedCards is empty");
            return null;
        }
        return playedCards.get(playedCards.size() - 1);
    }

    public List<Card> getHand() {
        return hand;
    }

    public String getName() {
        if (name == null) {
            return PokerPlayer.class.getSimpleName();
        }
        return name;
    }

    public PokerGame getGame() {
        return game;
    }

    public List<Card> getLittered() {
        return littered;
    }


    public boolean isUser() {
        return user;
    }


    public int getHandSize() {
        return hand.size();
    }

    public List<Card> chooseCardsToSwap() {
        return swapStrategy.chooseCardsToSwap(hand, game.getPlayerCount(), game.getPosition(this));
    }

    public void setHand(List<Card> hand){
        this.hand.clear();
        this.hand.addAll(hand);
    }

    public boolean isPredefined() {
        if (this instanceof PokerBot bot){
            return bot.isPlayPredefined;
        }
        else return false;
    }
}
