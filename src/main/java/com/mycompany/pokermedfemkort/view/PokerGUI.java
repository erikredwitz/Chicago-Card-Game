/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */

package com.mycompany.pokermedfemkort.view;

import com.mycompany.pokermedfemkort.model.Card;
import com.mycompany.pokermedfemkort.model.PokerGame;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JCheckBox;

import com.mycompany.pokermedfemkort.model.player.PokerPlayer;

/**
 * @author erikr
 */
public class PokerGUI extends javax.swing.JFrame {

    private final PokerGame game = new PokerGame(this);
    private static final String OPPONENTS_SETTING = "Anzahl Gegner: ";
    private static final String DIFFICULTY_SETTING = "Schwierigkeitsstufe: ";
    private static final String NEWLINE = System.getProperty("line.separator");

    /**
     * Creates new form PokerGUI
     */
    public PokerGUI() {
        initComponents();
    }

    public void writeNames(List<PokerPlayer> players) {
        StringBuilder names = new StringBuilder();
        for (PokerPlayer player : players) {
            names.append(player.getName()).append(NEWLINE);
        }
        nameTextPane.setText(names.toString());
    }

    public void preparePlayCard(List<Card> cards, boolean[] enabled) {
        int handSize = cards.size();
        for (int i = 0; i < playButtonList.size(); i++) {
            JButton button = playButtonList.get(i);
            if (i < handSize) {
                Card card = cards.get(i);
                button.setText(card.getRepresentation());
                button.setForeground(card.getSuit().getColor());
                button.setEnabled(enabled[i]);
                button.setVisible(true);
            } else {
                button.setVisible(false);
            }
        }
        nextRoundButton.setVisible(false);
        playPanel.setVisible(true);
        throwPanel.setVisible(false);

    }

    public void preparePlayCard(List<Card> cards) {
        boolean[] enabled = new boolean[cards.size()];
        Arrays.fill(enabled, true);
        preparePlayCard(cards, enabled);
    }

    public void updatePlayedCards() {
        StringBuilder playedCardsString = new StringBuilder();
        List<PokerPlayer> players = game.getPlayers();
        for (PokerPlayer player : players) {
            List<Card> playedCards = player.getPlayedCards();
            for (Card card : playedCards) {
                playedCardsString.append(card.getRepresentation()).append("\t");
            }
            playedCardsString.append(NEWLINE);
        }
        playedCardsTextPane.setText(playedCardsString.toString());
    }

    public void prepareSwapCards(List<Card> hand) {
        for (int i = 0; i < throwCheckBoxList.size(); i++) {
            JCheckBox checkBox = throwCheckBoxList.get(i);
            final Card card = hand.get(i);
            checkBox.setText(card.getRepresentation());
            checkBox.setForeground(card.getSuit().getColor());
            checkBox.setVisible(true);
            checkBox.setSelected(false);
        }
        playPanel.setVisible(false);
        throwPanel.setVisible(true);
    }

    public void showScoreboardChanges(PokerPlayer combinationWinner, int combinationPoints,
                                      PokerPlayer lastTrickWinner) {
        var players = game.getPlayers();
        var scoreboard = game.getScoreBoard();
        StringBuilder scoreString = new StringBuilder();
        players.forEach(player -> {
            int currentScore = scoreboard.getScoreOfPlayer(player);
            scoreString.append(currentScore);
            if (combinationWinner == player) {
                if (lastTrickWinner == player) {
                    scoreString.append(SCORE_CHANGE_PLUS)
                        .append(combinationPoints)
                        .append(SCORE_CHANGE_PLUS)
                        .append(PokerGame.POINTS_FOR_LAST_TRICK)
                        .append(SCORE_CHANGE_EQUALS)
                        .append(currentScore + combinationPoints + PokerGame.POINTS_FOR_LAST_TRICK);
                } else {
                    scoreString.append(SCORE_CHANGE_PLUS).append(combinationPoints)
                        .append(SCORE_CHANGE_EQUALS).append(currentScore + combinationPoints);
                }
            } else if (lastTrickWinner == player) {
                scoreString.append(SCORE_CHANGE_PLUS).append(PokerGame.POINTS_FOR_LAST_TRICK)
                    .append(SCORE_CHANGE_EQUALS).append(currentScore + PokerGame.POINTS_FOR_LAST_TRICK);
            }
            scoreString.append(NEWLINE);

        });
        pointsTextPane.setText(scoreString.toString());
    }

    private static final String SCORE_CHANGE_EQUALS = " = ";
    private static final String SCORE_CHANGE_PLUS = " + ";

    public void updateScoreboard() {
        var players = game.getPlayers();
        var scoreboard = game.getScoreBoard();
        StringBuilder scoreString = new StringBuilder();
        players.forEach(player -> {
            scoreString.append(scoreboard.getScoreOfPlayer(player)).append(NEWLINE);
        });
        pointsTextPane.setText(scoreString.toString());
    }

    public void showNextRoundButton() {
        if (game.hasEnded()) {
            nextRoundButton.setText(PLAY_AGAIN_TEXT);
        } else {
            nextRoundButton.setText(NEXT_ROUND_TEXT);
        }
        nextRoundButton.setVisible(true);
        playButtonList.forEach(button -> {
            button.setVisible(false);
        });
    }

    private static final String PLAY_AGAIN_TEXT = "Nochmal mit diesen Einstellungen spielen";
    private static final String NEXT_ROUND_TEXT = "Nächste Runde";

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        gameAndOptionsPane = new javax.swing.JLayeredPane();
        optionPanel = new javax.swing.JPanel();
        opponentCountPanel = new javax.swing.JPanel();
        opponentCountLabel = new javax.swing.JLabel();
        opponentCountSlider = new javax.swing.JSlider();
        difficultyPanel = new javax.swing.JPanel();
        difficultyLabel = new javax.swing.JLabel();
        difficultySlider = new javax.swing.JSlider();
        startGameButton = new javax.swing.JButton();
        gamePanel = new javax.swing.JPanel();
        nameScrollPane = new javax.swing.JScrollPane();
        nameTextPane = new javax.swing.JTextPane();
        playedCardsScrollPane = new javax.swing.JScrollPane();
        playedCardsTextPane = new javax.swing.JTextPane();
        pointsScrollPane = new javax.swing.JScrollPane();
        pointsTextPane = new javax.swing.JTextPane();
        throwAndPlayPane = new javax.swing.JLayeredPane();
        playPanel = new javax.swing.JPanel();
        playOptionsPanel = new javax.swing.JPanel();
        playOptionOne = new javax.swing.JButton();
        playOptionTwo = new javax.swing.JButton();
        playOptionThree = new javax.swing.JButton();
        playOptionFour = new javax.swing.JButton();
        playOptionFive = new javax.swing.JButton();
        nextRoundButton = new javax.swing.JButton();
        throwPanel = new javax.swing.JPanel();
        throwOptionPanel = new javax.swing.JPanel();
        throwOptionOne = new javax.swing.JCheckBox();
        throwOptionTwo = new javax.swing.JCheckBox();
        throwOptionThree = new javax.swing.JCheckBox();
        throwOptionFour = new javax.swing.JCheckBox();
        throwOptionFive = new javax.swing.JCheckBox();
        confirmThrow = new javax.swing.JButton();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        newGameSameSettings = new javax.swing.JMenuItem();
        newGameChangeSettings = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Poker med fem kort");

        gameAndOptionsPane.setLayout(new java.awt.CardLayout());

        optionPanel.setLayout(new java.awt.GridLayout(3, 1));

        opponentCountPanel.setLayout(new java.awt.GridLayout(1, 2));

        opponentCountLabel.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        opponentCountLabel.setText("Anzahl Gegner: 5");
        opponentCountLabel.setFocusable(false);
        opponentCountPanel.add(opponentCountLabel);

        opponentCountSlider.setMaximum(4);
        opponentCountSlider.setMinimum(1);
        opponentCountSlider.setFocusable(false);
        opponentCountSlider.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                opponentCountSliderStateChanged(evt);
            }
        });
        opponentCountPanel.add(opponentCountSlider);

        optionPanel.add(opponentCountPanel);

        difficultyPanel.setLayout(new java.awt.GridLayout(1, 0));

        difficultyLabel.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        difficultyLabel.setText("Schwierigkeitsstufe: 5");
        difficultyLabel.setFocusable(false);
        difficultyPanel.add(difficultyLabel);

        difficultySlider.setFocusable(false);
        difficultySlider.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                difficultySliderStateChanged(evt);
            }
        });
        difficultyPanel.add(difficultySlider);

        optionPanel.add(difficultyPanel);

        startGameButton.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        startGameButton.setText("Spielen");
        startGameButton.setFocusable(false);
        startGameButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                startGameButtonActionPerformed(evt);
            }
        });
        optionPanel.add(startGameButton);

        gameAndOptionsPane.add(optionPanel, "card3");

        nameTextPane.setEditable(false);
        nameTextPane.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Spieler",
            javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION,
            new java.awt.Font("Tahoma", 0, 14))); // NOI18N
        nameTextPane.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        nameTextPane.setText("Gu\nAl\nEr\nAn\nLi");
        nameTextPane.setFocusable(false);
        nameScrollPane.setViewportView(nameTextPane);

        playedCardsTextPane.setEditable(false);
        playedCardsTextPane.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Ausgespielte Karten",
            javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION,
            new java.awt.Font("Tahoma", 0, 14))); // NOI18N
        playedCardsTextPane.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        playedCardsTextPane.setFocusable(false);
        playedCardsScrollPane.setViewportView(playedCardsTextPane);

        pointsTextPane.setEditable(false);
        pointsTextPane.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Punkte",
            javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION,
            new java.awt.Font("Tahoma", 0, 14))); // NOI18N
        pointsTextPane.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        pointsTextPane.setFocusable(false);
        pointsScrollPane.setViewportView(pointsTextPane);

        throwAndPlayPane.setPreferredSize(new java.awt.Dimension(542, 75));
        throwAndPlayPane.setLayout(new java.awt.CardLayout());

        playPanel.setAlignmentX(1.0F);
        playPanel.setFocusable(false);
        playPanel.setPreferredSize(new java.awt.Dimension(409, 75));
        playPanel.setLayout(new java.awt.GridLayout(2, 1));

        playOptionsPanel.setLayout(new java.awt.GridLayout(1, 5));

        playOptionOne.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        playOptionOne.setText("♣10");
        playOptionOne.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        playOptionOne.setFocusable(false);
        playOptionOne.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                playOptionOneActionPerformed(evt);
            }
        });
        playOptionsPanel.add(playOptionOne);

        playOptionTwo.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        playOptionTwo.setText("♣B");
        playOptionTwo.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        playOptionTwo.setFocusable(false);
        playOptionTwo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                playOptionTwoActionPerformed(evt);
            }
        });
        playOptionsPanel.add(playOptionTwo);

        playOptionThree.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        playOptionThree.setForeground(new java.awt.Color(255, 51, 51));
        playOptionThree.setText("♦D");
        playOptionThree.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        playOptionThree.setFocusable(false);
        playOptionThree.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                playOptionThreeActionPerformed(evt);
            }
        });
        playOptionsPanel.add(playOptionThree);

        playOptionFour.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        playOptionFour.setText("♠D");
        playOptionFour.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        playOptionFour.setFocusable(false);
        playOptionFour.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                playOptionFourActionPerformed(evt);
            }
        });
        playOptionsPanel.add(playOptionFour);

        playOptionFive.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        playOptionFive.setForeground(new java.awt.Color(255, 51, 51));
        playOptionFive.setText("♦A");
        playOptionFive.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        playOptionFive.setFocusable(false);
        playOptionFive.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                playOptionFiveActionPerformed(evt);
            }
        });
        playOptionsPanel.add(playOptionFive);

        playPanel.add(playOptionsPanel);

        nextRoundButton.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        nextRoundButton.setText("Nächste Runde");
        nextRoundButton.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        nextRoundButton.setFocusable(false);
        nextRoundButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                nextRoundButtonActionPerformed(evt);
            }
        });
        playPanel.add(nextRoundButton);

        throwAndPlayPane.add(playPanel, "card2");
        playPanel.getAccessibleContext().setAccessibleDescription("");

        throwPanel.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        throwPanel.setName(""); // NOI18N
        throwPanel.setLayout(new java.awt.GridLayout(2, 1));

        throwOptionPanel.setLayout(new java.awt.GridLayout(1, 0));

        throwOptionOne.setBackground(new java.awt.Color(255, 255, 255));
        throwOptionOne.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        throwOptionOne.setText("♣B");
        throwOptionOne.setBorderPainted(true);
        throwOptionOne.setBorderPaintedFlat(true);
        throwOptionOne.setFocusable(false);
        throwOptionOne.setPreferredSize(new java.awt.Dimension(30, 15));
        throwOptionPanel.add(throwOptionOne);

        throwOptionTwo.setBackground(new java.awt.Color(255, 255, 255));
        throwOptionTwo.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        throwOptionTwo.setForeground(new java.awt.Color(255, 51, 51));
        throwOptionTwo.setText("♥2");
        throwOptionTwo.setAlignmentX(0.5F);
        throwOptionTwo.setBorderPainted(true);
        throwOptionTwo.setBorderPaintedFlat(true);
        throwOptionTwo.setFocusable(false);
        throwOptionTwo.setPreferredSize(new java.awt.Dimension(30, 15));
        throwOptionPanel.add(throwOptionTwo);

        throwOptionThree.setBackground(new java.awt.Color(255, 255, 255));
        throwOptionThree.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        throwOptionThree.setForeground(new java.awt.Color(255, 51, 51));
        throwOptionThree.setText("♦7");
        throwOptionThree.setAlignmentX(0.5F);
        throwOptionThree.setBorderPainted(true);
        throwOptionThree.setBorderPaintedFlat(true);
        throwOptionThree.setFocusable(false);
        throwOptionThree.setPreferredSize(new java.awt.Dimension(30, 15));
        throwOptionPanel.add(throwOptionThree);

        throwOptionFour.setBackground(new java.awt.Color(255, 255, 255));
        throwOptionFour.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        throwOptionFour.setForeground(new java.awt.Color(255, 51, 51));
        throwOptionFour.setText("♦A");
        throwOptionFour.setBorderPainted(true);
        throwOptionFour.setBorderPaintedFlat(true);
        throwOptionFour.setFocusable(false);
        throwOptionFour.setPreferredSize(new java.awt.Dimension(30, 15));
        throwOptionPanel.add(throwOptionFour);

        throwOptionFive.setBackground(new java.awt.Color(255, 255, 255));
        throwOptionFive.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        throwOptionFive.setText("♠B");
        throwOptionFive.setBorderPainted(true);
        throwOptionFive.setBorderPaintedFlat(true);
        throwOptionFive.setFocusable(false);
        throwOptionFive.setPreferredSize(new java.awt.Dimension(30, 15));
        throwOptionPanel.add(throwOptionFive);

        throwPanel.add(throwOptionPanel);

        confirmThrow.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        confirmThrow.setText("Schmeißen");
        confirmThrow.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        confirmThrow.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                confirmThrowActionPerformed(evt);
            }
        });
        throwPanel.add(confirmThrow);

        throwAndPlayPane.add(throwPanel, "card3");

        javax.swing.GroupLayout gamePanelLayout = new javax.swing.GroupLayout(gamePanel);
        gamePanel.setLayout(gamePanelLayout);
        gamePanelLayout.setHorizontalGroup(
            gamePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(gamePanelLayout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(nameScrollPane, javax.swing.GroupLayout.PREFERRED_SIZE, 107,
                        javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                    .addGroup(gamePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(throwAndPlayPane, javax.swing.GroupLayout.DEFAULT_SIZE, 332, Short.MAX_VALUE)
                        .addComponent(playedCardsScrollPane))
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                    .addComponent(pointsScrollPane, javax.swing.GroupLayout.PREFERRED_SIZE, 242,
                        javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(30, Short.MAX_VALUE))
        );
        gamePanelLayout.setVerticalGroup(
            gamePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(gamePanelLayout.createSequentialGroup()
                    .addContainerGap()
                    .addGroup(gamePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(nameScrollPane, javax.swing.GroupLayout.Alignment.TRAILING,
                            javax.swing.GroupLayout.DEFAULT_SIZE, 168, Short.MAX_VALUE)
                        .addComponent(playedCardsScrollPane, javax.swing.GroupLayout.Alignment.TRAILING)
                        .addComponent(pointsScrollPane))
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                    .addComponent(throwAndPlayPane, javax.swing.GroupLayout.PREFERRED_SIZE, 177,
                        javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(21, Short.MAX_VALUE))
        );

        gameAndOptionsPane.add(gamePanel, "card2");

        jMenu1.setText("Neues Spiel");

        newGameSameSettings.setText("Spiel neu starten");
        newGameSameSettings.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                newGameSameSettingsActionPerformed(evt);
            }
        });
        jMenu1.add(newGameSameSettings);

        newGameChangeSettings.setText("Spiel mit anderen Einstellungen starten");
        newGameChangeSettings.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                newGameChangeSettingsActionPerformed(evt);
            }
        });
        jMenu1.add(newGameChangeSettings);

        jMenuBar1.add(jMenu1);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(gameAndOptionsPane)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(gameAndOptionsPane)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void newGameSameSettingsActionPerformed(
        java.awt.event.ActionEvent evt) {//GEN-FIRST:event_newGameSameSettingsActionPerformed
        startGame();
    }//GEN-LAST:event_newGameSameSettingsActionPerformed

    private void startGame() {
        gamePanel.setVisible(true);
        optionPanel.setVisible(false);
        initButtonLists();
        game.startGame();
    }

    private void opponentCountSliderStateChanged(
        javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_OpponentCountSliderStateChanged
        opponentCountLabel.setText(OPPONENTS_SETTING + opponentCountSlider.getValue());
    }//GEN-LAST:event_OpponentCountSliderStateChanged

    private void difficultySliderStateChanged(
        javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_difficultySliderStateChanged
        difficultyLabel.setText(DIFFICULTY_SETTING + difficultySlider.getValue());
    }//GEN-LAST:event_difficultySliderStateChanged

    private void startGameButtonActionPerformed(
        java.awt.event.ActionEvent evt) {//GEN-FIRST:event_startGameButtonActionPerformed
        game.setOpponentCount(opponentCountSlider.getValue());
        game.setDifficulty(difficultySlider.getValue());
        startGame();
    }//GEN-LAST:event_startGameButtonActionPerformed

    private void initButtonLists() {
        if (playButtonList == null) {
            playButtonList = new LinkedList<>();
            playButtonList.add(playOptionOne);
            playButtonList.add(playOptionTwo);
            playButtonList.add(playOptionThree);
            playButtonList.add(playOptionFour);
            playButtonList.add(playOptionFive);
            throwCheckBoxList = new LinkedList<>();
            throwCheckBoxList.add(throwOptionOne);
            throwCheckBoxList.add(throwOptionTwo);
            throwCheckBoxList.add(throwOptionThree);
            throwCheckBoxList.add(throwOptionFour);
            throwCheckBoxList.add(throwOptionFive);

        }
    }

    private void newGameChangeSettingsActionPerformed(
        java.awt.event.ActionEvent evt) {//GEN-FIRST:event_newGameChangeSettingsActionPerformed
        gamePanel.setVisible(false);
        optionPanel.setVisible(true);
    }//GEN-LAST:event_newGameChangeSettingsActionPerformed

    private void confirmThrowActionPerformed(
        java.awt.event.ActionEvent evt) {//GEN-FIRST:event_confirmThrowActionPerformed
        boolean[] swapArray = new boolean[PokerGame.CARD_HAND_SIZE];
        for (int i = 0; i < swapArray.length; i++) {
            swapArray[i] = throwCheckBoxList.get(i).isSelected();
        }
        game.receiveSwapInput(swapArray);
    }//GEN-LAST:event_confirmThrowActionPerformed

    private void nextRoundButtonActionPerformed(
        java.awt.event.ActionEvent evt) {//GEN-FIRST:event_nextRoundButtonActionPerformed
        if (game.hasEnded()) {
            startGame();
        } else {
            game.continueGame();
        }
    }//GEN-LAST:event_nextRoundButtonActionPerformed

    private void playOptionOneActionPerformed(
        java.awt.event.ActionEvent evt) {//GEN-FIRST:event_playOptionOneActionPerformed
        game.receivePlayInput(0);
    }//GEN-LAST:event_playOptionOneActionPerformed

    private void playOptionTwoActionPerformed(
        java.awt.event.ActionEvent evt) {//GEN-FIRST:event_playOptionTwoActionPerformed
        game.receivePlayInput(1);
    }//GEN-LAST:event_playOptionTwoActionPerformed

    private void playOptionThreeActionPerformed(
        java.awt.event.ActionEvent evt) {//GEN-FIRST:event_playOptionThreeActionPerformed
        game.receivePlayInput(2);
    }//GEN-LAST:event_playOptionThreeActionPerformed

    private void playOptionFourActionPerformed(
        java.awt.event.ActionEvent evt) {//GEN-FIRST:event_playOptionFourActionPerformed
        game.receivePlayInput(3);
    }//GEN-LAST:event_playOptionFourActionPerformed

    private void playOptionFiveActionPerformed(
        java.awt.event.ActionEvent evt) {//GEN-FIRST:event_playOptionFiveActionPerformed
        game.receivePlayInput(4);
    }//GEN-LAST:event_playOptionFiveActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(PokerGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(PokerGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(PokerGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(PokerGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new PokerGUI().setVisible(true);
            }
        });


    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton confirmThrow;
    private javax.swing.JLabel difficultyLabel;
    private javax.swing.JPanel difficultyPanel;
    private javax.swing.JSlider difficultySlider;
    private javax.swing.JLayeredPane gameAndOptionsPane;
    private javax.swing.JPanel gamePanel;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JScrollPane nameScrollPane;
    private javax.swing.JTextPane nameTextPane;
    private javax.swing.JMenuItem newGameChangeSettings;
    private javax.swing.JMenuItem newGameSameSettings;
    private javax.swing.JButton nextRoundButton;
    private javax.swing.JLabel opponentCountLabel;
    private javax.swing.JPanel opponentCountPanel;
    private javax.swing.JSlider opponentCountSlider;
    private javax.swing.JPanel optionPanel;
    private javax.swing.JButton playOptionFive;
    private javax.swing.JButton playOptionFour;
    private javax.swing.JButton playOptionOne;
    private javax.swing.JButton playOptionThree;
    private javax.swing.JButton playOptionTwo;
    private javax.swing.JPanel playOptionsPanel;
    private javax.swing.JPanel playPanel;
    private javax.swing.JScrollPane playedCardsScrollPane;
    private javax.swing.JTextPane playedCardsTextPane;
    private javax.swing.JScrollPane pointsScrollPane;
    private javax.swing.JTextPane pointsTextPane;
    private javax.swing.JButton startGameButton;
    private javax.swing.JLayeredPane throwAndPlayPane;
    private javax.swing.JCheckBox throwOptionFive;
    private javax.swing.JCheckBox throwOptionFour;
    private javax.swing.JCheckBox throwOptionOne;
    private javax.swing.JPanel throwOptionPanel;
    private javax.swing.JCheckBox throwOptionThree;
    private javax.swing.JCheckBox throwOptionTwo;
    private javax.swing.JPanel throwPanel;
    // End of variables declaration//GEN-END:variables

    private List<JButton> playButtonList;
    private List<JCheckBox> throwCheckBoxList;
}
