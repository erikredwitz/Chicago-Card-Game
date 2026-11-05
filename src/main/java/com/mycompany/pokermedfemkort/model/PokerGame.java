package com.mycompany.pokermedfemkort.model;

import com.mycompany.pokermedfemkort.model.player.PokerPlayer;
import com.mycompany.pokermedfemkort.view.PokerGUI;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import com.mycompany.pokermedfemkort.model.player.PokerBot;
import com.mycompany.pokermedfemkort.model.player.PokerUser;

public class PokerGame {

    public static final int DECK_SIZE = 52;
    public static final int CARD_HAND_SIZE = 5;
    public static final String BOT_NAME = "Bot";
    private static final int FOR_NOW_NOT_DEALER = 4;
    private static final int SCORE_FOR_WINNING = 12;
    public static final int POINTS_FOR_LAST_TRICK = 2;
    public static final String USER_NAME = "Du";
    public static final Random RANDOM = new Random();
    private final Settings settings;
    private Deck deck;
    private final List<PokerPlayer> players = new LinkedList<>();
    private ScoreBoard scoreboard;
    private final List<Card> huddle = new LinkedList<>();
    protected final PokerGUI gui;
    private PokerPhase phase;

    private boolean inputValid;
    private PokerPlayer lastTrickWinner;
    private boolean endOfRound;

    public PokerGame(PokerGUI gui) {
        deck = new Deck();
        settings = new Settings();
        this.gui = gui;
    }

    /**
     * Sets the number of opponents.
     *
     * @param sliderValue Slider value with range from 1 to 5
     */
    public void setOpponentCount(int sliderValue) {
        settings.setOpponentCount(sliderValue);
    }

    public void setDifficulty(int sliderValue) {
        settings.setDifficulty(sliderValue);
    }

    public void deal(List<PokerPlayer> orderedPlayers) {
        deck.shuffle();
        for (int i = 0; i < CARD_HAND_SIZE; i++) {
            for (PokerPlayer player : orderedPlayers) {
                deck.giveTopCard(player);
            }
        }
    }

    public boolean swap(List<PokerPlayer> orderedPlayers, int playerInTurn) {
        gui.updatePlayedCards();
        gui.updateScoreboard();
        for (int i = playerInTurn; i < orderedPlayers.size(); i++) {
            var player = orderedPlayers.get(i);
            if (player.isUser() && !inputValid) {
                gui.prepareSwapCards(player.getHand());
                phase.setPlayerInTurn(i);
                return false;
            }
            List<Card> littered = player.litterCards(player.chooseCardsToSwap());
            if (player.isUser()) {
                inputValid = false;
            }
            huddle.addAll(littered);
        }
        var dealer = orderedPlayers.get(orderedPlayers.size() - 1);
        if (dealer.getHandSize() == 0) {
            deck.giveTopCard(dealer);
        }
        for (int j = 0; j < orderedPlayers.size() - 1; j++) {
            var player = orderedPlayers.get(j);
            if (player.getHandSize() == 0) {
                for (int i = 0; i < FOR_NOW_NOT_DEALER; i++) {
                    deck.giveTopCard(player);
                }
            } else {
                while (player.getHandSize() < CARD_HAND_SIZE && !deck.isEmpty()) {
                    deck.giveTopCard(player);
                }
            }
        }
        for (PokerPlayer player : orderedPlayers) {
            while (player.getHandSize() < CARD_HAND_SIZE && !deck.isEmpty()) {
                deck.giveTopCard(player);
            }
        }
        return true;
    }

    public boolean playRound(List<PokerPlayer> orderedPlayers, int playerInTurn) {
        PokerPlayer trickWinningPlayer = orderedPlayers.get(0);
        while (orderedPlayers.get(playerInTurn).getHandSize() > 0) {
            PokerPlayer firstPlayer = orderedPlayers.get(0);
            Suit led;
            int highest;
            Card leadingCard = getLeading(orderedPlayers);
            if (leadingCard != null) {
                led = leadingCard.getSuit();
                highest = leadingCard.getValue();
            } else {
                led = null;
                highest = 0;
            }
            for (; playerInTurn < orderedPlayers.size(); playerInTurn++) {
                PokerPlayer player = orderedPlayers.get(playerInTurn);
                Card playedCard;
                if (player.getHandSize() > 1) {
                    if (player.isUser() && !inputValid) {
                        phase.setFirstPlayer(firstPlayer);
                        phase.setPlayerInTurn(playerInTurn);
                        var hand = player.getHand();
                        gui.updatePlayedCards();
                        if (led == null) {
                            gui.preparePlayCard(hand);
                        } else {
                            gui.preparePlayCard(hand, getEnabled(led, highest, hand));
                        }
                        return false;
                    }
                    if (playerInTurn == 0) {
                        playedCard = player.chooseCardToBeginWith();
                    } else {
                        playedCard = player.chooseCardToPlay(led, highest);
                    }
                    if (player.isUser()) {
                        inputValid = false;
                    }
                } else {
                    playedCard = player.getHand().get(0);
                }
                player.playCard(playedCard);
                if (led == null) {
                    led = playedCard.getSuit();
                    highest = playedCard.getValue();
                } else if (playedCard.getSuit() == led && playedCard.getValue() > highest) {
                    highest = playedCard.getValue();
                }
            }
            trickWinningPlayer = getPlayerWhoLastPlayed(led, highest);
            if (orderedPlayers.get(0).getHandSize() > 0) {
                while (trickWinningPlayer != orderedPlayers.get(0)) {
                    orderedPlayers.add(orderedPlayers.remove(0));
                }
            }
            playerInTurn = 0;
        }
        gui.updatePlayedCards();
        lastTrickWinner = trickWinningPlayer;
        endOfRound = true;
        return true;
    }

    public void printHand(PokerPlayer firstPlayer) {
        for (var card : firstPlayer.getHand()) {
            System.out.print(card.getRepresentation() + " ");
        }
        System.out.print("\n");
    }

    private Card getLeading(List<PokerPlayer> orderedPlayers) {
        List<PokerPlayer> playersWhoHavePlayed = new LinkedList<>();
        int maxPlayed = orderedPlayers.get(0).getPlayedCards().size();
        if (maxPlayed == 0) {
            return null;
        }
        for (PokerPlayer player : orderedPlayers) {
            int played = player.getPlayedCards().size();
            if (played == maxPlayed) {
                playersWhoHavePlayed.add(player);
            }
        }
        if (playersWhoHavePlayed.size() == orderedPlayers.size()) {
            return null;
        }
        Card leading = playersWhoHavePlayed.get(0).getLastPlayedCard();
        for (PokerPlayer player : playersWhoHavePlayed) {
            var played = player.getLastPlayedCard();
            if (played.getValue() > leading.getValue() && played.getSuit() == leading.getSuit()) {
                leading = played;
            }
        }
        return leading;
    }

    public void printPlayedCards() {
        for (var player : players) {
            var playedCards = player.getPlayedCards();
            System.out.print(player.getName() + ":\t");
            for (Card card : playedCards) {
                System.out.print(card.getRepresentation() + "\t");
            }
            System.out.print("\n");
        }
    }

    public static boolean[] getEnabled(Suit led, int value, List<Card> hand) {
        var playable = getPlayable(hand, led, value);
        boolean[] enabled = new boolean[hand.size()];
        for (int i = 0; i < hand.size(); i++) {
            enabled[i] = playable.contains(i);
        }
        return enabled;
    }

    private PokerPlayer getPlayerWhoLastPlayed(Suit led, int highestValue) {
        for (PokerPlayer player : players) {
            Card lastPlayed = player.getLastPlayedCard();
            if (lastPlayed.getSuit() == led && lastPlayed.getValue() == highestValue) {
                return player;
            }
        }
        return null;
    }

    public void receiveSwapInput(boolean[] swapArray) {
        var user = players.get(players.size() - 1);
        if (user.isUser()) {
            ((PokerUser) user).swapInput(swapArray);
        } else {
            for (PokerPlayer player : players) {
                if (player.isUser()) {
                    ((PokerUser) user).swapInput(swapArray);
                }
            }
        }
        inputValid = true;
        continueGame();
    }

    public void receivePlayInput(int playDecision) {
        var user = players.get(players.size() - 1);
        if (user.isUser()) {
            ((PokerUser) user).playInput(playDecision);
        } else {
            for (PokerPlayer player : players) {
                if (player.isUser()) {
                    ((PokerUser) user).playInput(playDecision);
                }
            }
        }
        inputValid = true;
        continueGame();
    }

    public void startGame() {
        prepareGame();
        continueGame();
    }

    private void prepareGame() {
        players.clear();
        //create opponents with strategies "PlayScript", "SwapScript"
        for (int i = 0;
             i < settings.getOpponentCount(); i++) {
            //hier kann man später dem Konstruktor der Strategien den Schwierigkeitsgrad übergeben
            players.add(
                new PokerBot(this, BOT_NAME + (i + 1),
                    new RandomSamplingSwapStrategy(settings.getDifficulty()),
                    new RandomSamplingPlayStrategy(settings.getDifficulty())));
        }

        //create 1 player with strategies "InputPlay", "InputSwap"
        PokerPlayer user = new PokerUser(this, USER_NAME);
        players.add(user);
        scoreboard = new ScoreBoard(players);
        gui.writeNames(players);
        gui.updateScoreboard();
        gui.updatePlayedCards();
        inputValid = false;
        huddle.clear();
        deck = new Deck();
        phase = new DealPhase(RANDOM.nextInt(players.size()), this);
    }

    public void clearHuddle() {
        huddle.clear();
    }

    public void setPhase(PokerPhase phase) {
        this.phase = phase;
    }

    public void continueGame() {
        endOfRound = false;
        boolean phaseIsFinished;
        do {
            phaseIsFinished = phase.continuePhase();
            if (phaseIsFinished) {
                phase = phase.getNextPhase();
            }
        } while (phaseIsFinished && !endOfRound);
    }

    public void keepScore() {
        List<PokerHand> combinationList = new LinkedList<>();
        for (PokerPlayer player : players) {
            PokerHand pokerHand = PokerHand.getPokerHandWithPlayer(player);
            combinationList.add(pokerHand);
        }
        combinationList.sort(Collections.reverseOrder());
        PokerHand bestCombination = combinationList.get(0);
        var combinationWinner = bestCombination.getPlayer();
        var points = bestCombination.getCombination().getPoints();
        gui.showScoreboardChanges(combinationWinner, points, lastTrickWinner);
        scoreboard.increaseScoreOfPlayer(combinationWinner, bestCombination.getCombination().getPoints());
        scoreboard.increaseScoreOfPlayer(lastTrickWinner, POINTS_FOR_LAST_TRICK);
    }

    private void collectPlayedCards() {
        for (PokerPlayer player : players) {
            huddle.addAll(player.getPlayedCards());
            player.getPlayedCards().clear();
        }
        deck.addAll(huddle);
        huddle.clear();
    }

    public List<Card> getHuddle() {
        return huddle;
    }

    public List<PokerPlayer> getPlayers() {
        return players;
    }

    public int getPlayerCount() {
        return settings.getOpponentCount() + 1;
    }

    public ScoreBoard getScoreBoard() {
        return scoreboard;
    }

    public static List<Integer> getPlayable(List<Card> hand, Suit led, int value) {
        List<Integer> cardsInSuit = new LinkedList<>();
        for (int i = 0; i < hand.size(); i++) {
            if (hand.get(i).getSuit() == led) {
                cardsInSuit.add(i);
            }
        }
        if (cardsInSuit.isEmpty()) {
            return listFromZeroTo(hand.size());
        }
        List<Integer> higher = new LinkedList<>();
        for (int i = 0; i < cardsInSuit.size(); i++) {
            if (hand.get(cardsInSuit.get(i)).getValue() > value) {
                higher.add(cardsInSuit.get(i));
            }
        }
        if (higher.isEmpty()) {
            return cardsInSuit;
        }
        return higher;
    }

    public static List<Integer> listFromZeroTo(int length) {
        List<Integer> numbers = new LinkedList<>();
        for (int i = 0; i < length; i++) {
            numbers.add(i);
        }
        return numbers;
    }

    public boolean hasEnded() {
        return scoreboard.getHighestScore() >= SCORE_FOR_WINNING;
    }

    public void prepareNextRound() {
        keepScore();
        gui.showNextRoundButton();
        gui.updatePlayedCards();
        collectPlayedCards();

    }

    public int getPosition(PokerPlayer pokerPlayer) {
        int i = 0;
        for (PokerPlayer x : phase.orderedPlayers) {
            if (pokerPlayer == x) {
                return i;
            }
            i++;
        }
        return -1;
    }

    public void setDeck(Deck deck) {
        this.deck = deck;
    }

    public Deck getDeck() {
        return deck;
    }

    public PokerPhase getPhase() {
        return phase;
    }


    public void setScoreboard(ScoreBoard scoreboard) {
        this.scoreboard = scoreboard;
    }
}
