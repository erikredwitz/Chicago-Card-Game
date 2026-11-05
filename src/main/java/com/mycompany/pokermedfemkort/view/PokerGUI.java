/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package com.mycompany.pokermedfemkort.view;

import com.mycompany.pokermedfemkort.model.Card;
import com.mycompany.pokermedfemkort.model.PokerGame;

import java.awt.Component;
import java.awt.Container;
import java.awt.Font;
import java.awt.GridLayout;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.mycompany.pokermedfemkort.model.player.PokerPlayer;
import javax.swing.SwingConstants;

/**
 * @author erikr
 */
public class PokerGUI extends javax.swing.JFrame {

    protected PokerGame game;
    private static final String OPPONENTS_SETTING = "Anzahl Gegner: ";
    private static final String DIFFICULTY_SETTING = "Schwierigkeitsstufe: ";
    private static final String NEWLINE = System.getProperty("line.separator");
    private String fontName = "Tahoma";
    private int fontStyle = Font.PLAIN;
    private int fontSize = 18;

    /**
     * Creates new form PokerGUI
     */
    public PokerGUI(PokerGame game) {
        initComponents();
        closeFontSettings();
        this.game = game;
    }

    public PokerGUI() {
        initComponents();
        closeFontSettings();
    }

    public void setGame(PokerGame game) {
        this.game = game;
    }

    public void writeNames(List<PokerPlayer> players) {
        for (int i = 0; i < players.size(); i++) {
            nameLabels.get(i).setText(players.get(i).getName());
        }
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
        bigButton.setVisible(false);
        playPanel.setVisible(true);
        throwPanel.setVisible(false);

    }

    public void preparePlayCard(List<Card> cards) {
        boolean[] enabled = new boolean[cards.size()];
        Arrays.fill(enabled, true);
        preparePlayCard(cards, enabled);
    }

    public void updatePlayedCards() {
        List<PokerPlayer> players = game.getPlayers();
        for (int i = 0; i < players.size(); i++) {
            PokerPlayer player = players.get(i);
            List<Card> playedCards = player.getPlayedCards();
            for (int j = 0; j < PokerGame.CARD_HAND_SIZE; j++) {
                var label = playedCardsLabels.get(i)[j];
                if (j < playedCards.size()) {
                    label.setVisible(true);
                    Card card = playedCards.get(j);
                    label.setText(card.getRepresentation());
                    label.setForeground(card.getSuit().getColor());
                } else {
                    label.setVisible(false);
                }
            }
        }
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
        bigButton.setVisible(true);
        bigButton.setText("Schmeißen");
        playPanel.setVisible(false);
        throwPanel.setVisible(true);

    }

    public void showScoreboardChanges(PokerPlayer combinationWinner, int combinationPoints,
            PokerPlayer lastTrickWinner) {
        var players = game.getPlayers();
        var scoreboard = game.getScoreBoard();
        for (int i = 0; i < players.size(); i++) {
            PokerPlayer player = players.get(i);
            int currentScore = scoreboard.getScoreOfPlayer(player);
            var scoreString = new StringBuilder();
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
            pointsLabels.get(i).setText(scoreString.toString());
        }
    }

    private static final String SCORE_CHANGE_EQUALS = " = ";
    private static final String SCORE_CHANGE_PLUS = " + ";

    public void updateScoreboard() {
        var players = game.getPlayers();
        var scoreboard = game.getScoreBoard();
        for (int i = 0; i < players.size(); i++) {
            pointsLabels.get(i).setText(String.valueOf(scoreboard.getScoreOfPlayer(players.get(i))));
        }
    }

    public void showNextRoundButton() {
        if (game.hasEnded()) {
            bigButton.setText(PLAY_AGAIN_TEXT);
        } else {
            bigButton.setText(NEXT_ROUND_TEXT);
        }
        bigButton.setVisible(true);
        playButtonList.forEach(button -> {
            button.setVisible(false);
        });
    }

    public static void changeFont(Component component, Font font) {
        component.setFont(font);
        if (component instanceof Container container) {
            for (Component child : container.getComponents()) {
                changeFont(child, font);
            }
        }
    }

    public void updateFont() {
        changeFont(getContentPane(), new Font(fontName, fontStyle, fontSize));
        /*
        confirmThrow.getFont().deriveFont((float) size);
        difficultyLabel.getFont().deriveFont((float) size);
        jMenu1.getFont().deriveFont((float) size);
        jMenuBar1.getFont().deriveFont((float) size);
        jMenuBar1.getFont().deriveFont((float) size);
        for (var label : nameLabels) {
            label.getFont().deriveFont((float) size);
        }
        newGameChangeSettings.getFont().deriveFont((float) size);
        newGameSameSettings.getFont().deriveFont((float) size);
        nextRoundButton.getFont().deriveFont((float) size);
        opponentCountLabel.getFont().deriveFont((float) size);
        for (var array : playedCardsLabels) {
            for (var label : array) {
                label.getFont().deriveFont((float) size);
            }
        }
        for (var label : pointsLabels) {
            label.getFont().deriveFont((float) size);
        }
        for (var x : playButtonList) {
            x.getFont().deriveFont((float) size);
        }
        for (var x : throwCheckBoxList) {
            x.getFont().deriveFont((float) size);
        }
        startGameButton.getFont().deriveFont((float) size);
         */
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
        gameDisplay = new javax.swing.JPanel();
        leftPanel = new javax.swing.JPanel();
        namePanel = new javax.swing.JPanel();
        middlePanel = new javax.swing.JPanel();
        playedCardsPanelContainer = new javax.swing.JPanel();
        throwAndPlayPane = new javax.swing.JLayeredPane();
        playPanel = new javax.swing.JPanel();
        playOptionsPanel = new javax.swing.JPanel();
        playOptionOne = new javax.swing.JButton();
        playOptionTwo = new javax.swing.JButton();
        playOptionThree = new javax.swing.JButton();
        playOptionFour = new javax.swing.JButton();
        playOptionFive = new javax.swing.JButton();
        throwPanel = new javax.swing.JPanel();
        throwOptionPanel = new javax.swing.JPanel();
        throwOptionOne = new javax.swing.JCheckBox();
        throwOptionTwo = new javax.swing.JCheckBox();
        throwOptionThree = new javax.swing.JCheckBox();
        throwOptionFour = new javax.swing.JCheckBox();
        throwOptionFive = new javax.swing.JCheckBox();
        rightPanel = new javax.swing.JPanel();
        pointsPanel = new javax.swing.JPanel();
        bigButtonPanel = new javax.swing.JPanel();
        bigButton = new javax.swing.JButton();
        fontSizePanel = new javax.swing.JPanel();
        fontSizeCancel = new javax.swing.JButton();
        fontSizeSlider = new javax.swing.JSlider();
        fontSizeLabel = new javax.swing.JLabel();
        fontSizeSave = new javax.swing.JButton();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        newGameSameSettings = new javax.swing.JMenuItem();
        newGameChangeSettings = new javax.swing.JMenuItem();
        fontSettings = new javax.swing.JMenu();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Poker med fem kort");
        setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N

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
        difficultyLabel.setText("Schwierigkeitsstufe:");
        difficultyLabel.setFocusable(false);
        difficultyPanel.add(difficultyLabel);

        difficultySlider.setFocusable(false);
        difficultySlider.setMinimum(0);
        difficultySlider.setMaximum(12);
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

        gamePanel.setLayout(new java.awt.GridLayout(2, 1));

        gameDisplay.setLayout(new java.awt.GridLayout(1, 3));

        leftPanel.setLayout(new java.awt.GridLayout(2, 1));

        namePanel.setLayout(new java.awt.GridLayout(5, 1));
        leftPanel.add(namePanel);

        gameDisplay.add(leftPanel);

        middlePanel.setLayout(new java.awt.GridLayout(2, 1));

        playedCardsPanelContainer.setLayout(new java.awt.GridLayout(5, 5));
        middlePanel.add(playedCardsPanelContainer);

        throwAndPlayPane.setPreferredSize(new java.awt.Dimension(542, 75));
        throwAndPlayPane.setLayout(new java.awt.CardLayout());

        playPanel.setAlignmentX(1.0F);
        playPanel.setFocusable(false);
        playPanel.setPreferredSize(new java.awt.Dimension(409, 75));
        playPanel.setLayout(new java.awt.GridLayout(1, 1));

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

        throwAndPlayPane.add(playPanel, "card2");
        playPanel.getAccessibleContext().setAccessibleDescription("");

        throwPanel.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        throwPanel.setName(""); // NOI18N
        throwPanel.setLayout(new java.awt.GridLayout(1, 1));

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

        throwAndPlayPane.add(throwPanel, "card3");

        middlePanel.add(throwAndPlayPane);

        gameDisplay.add(middlePanel);

        rightPanel.setLayout(new java.awt.GridLayout(2, 1));

        pointsPanel.setLayout(new java.awt.GridLayout(5, 1));
        rightPanel.add(pointsPanel);

        gameDisplay.add(rightPanel);

        gamePanel.add(gameDisplay);

        bigButtonPanel.setLayout(new java.awt.GridLayout());

        bigButton.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        bigButton.setText("Nächste Runde");
        bigButton.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        bigButton.setFocusable(false);
        bigButton.setPreferredSize(new java.awt.Dimension(300, 200));
        bigButton.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        bigButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bigButtonActionPerformed(evt);
            }
        });
        bigButtonPanel.add(bigButton);

        gamePanel.add(bigButtonPanel);

        gameAndOptionsPane.add(gamePanel, "card2");

        fontSizePanel.setFocusable(false);

        fontSizeCancel.setFont(new java.awt.Font("Tahoma", 0, 50)); // NOI18N
        fontSizeCancel.setText("Abbrechen");
        fontSizeCancel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                fontSizeCancelActionPerformed(evt);
            }
        });
        fontSizePanel.add(fontSizeCancel);

        fontSizeSlider.setMaximum(60);
        fontSizeSlider.setMinimum(12);
        fontSizeSlider.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                fontSizeSliderStateChanged(evt);
            }
        });
        fontSizePanel.add(fontSizeSlider);

        fontSizeLabel.setFont(new java.awt.Font("Tahoma", 0, 50)); // NOI18N
        fontSizeLabel.setText("Schriftgröße");
        fontSizePanel.add(fontSizeLabel);

        fontSizeSave.setFont(new java.awt.Font("Tahoma", 0, 50)); // NOI18N
        fontSizeSave.setText("Speichern");
        fontSizeSave.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                fontSizeSaveActionPerformed(evt);
            }
        });
        fontSizePanel.add(fontSizeSave);

        gameAndOptionsPane.add(fontSizePanel, "card4");

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

        fontSettings.setText("Schriftgröße");
        fontSettings.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                fontSettingsMouseClicked(evt);
            }
        });
        jMenuBar1.add(fontSettings);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(gameAndOptionsPane, javax.swing.GroupLayout.DEFAULT_SIZE, 1023, Short.MAX_VALUE)
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
        switchToGamePanel();
        initButtonLists();
        initLabels();
        updateFont();
        game.startGame();
    }

    private void switchToGamePanel() {
        gamePanel.setVisible(true);
        inGamePanel = true;
        optionPanel.setVisible(false);
    }

    private void initLabels() {
        initNameLabels();
        initPlayedCardsLabels();
        initPointsLabels();
    }

    private void initNameLabels() {
        namePanel.removeAll();
        nameLabels.clear();
        var playerCount = game.getPlayerCount();
        namePanel.setLayout(new GridLayout(playerCount, 1));
        for (int i = 0; i < playerCount; i++) {
            var name = new JLabel();
            name.setHorizontalAlignment(SwingConstants.RIGHT);
            namePanel.add(name);
            nameLabels.add(name);
        }
    }

    private void initPointsLabels() {
        pointsPanel.removeAll();
        pointsLabels.clear();
        var playerCount = game.getPlayerCount();
        pointsPanel.setLayout(new GridLayout(playerCount, 1));
        for (int i = 0; i < playerCount; i++) {
            var points = new JLabel();
            pointsPanel.add(points);
            pointsLabels.add(points);
        }
    }

    private void initPlayedCardsLabels() {
        playedCardsPanelContainer.removeAll();
        playedCardsPanels.clear();
        playedCardsLabels.clear();
        var playerCount = game.getPlayerCount();
        playedCardsPanelContainer.setLayout(new GridLayout(playerCount, 1));
        for (int i = 0; i < playerCount; i++) {
            var panel = new JPanel();
            panel.setLayout(new GridLayout(1, PokerGame.CARD_HAND_SIZE));
            playedCardsPanelContainer.add(panel);
            playedCardsPanels.add(panel);
            playedCardsLabels.add(new JLabel[PokerGame.CARD_HAND_SIZE]);
            for (int j = 0; j < PokerGame.CARD_HAND_SIZE; j++) {
                var label = new JLabel();
                label.setHorizontalAlignment(SwingConstants.CENTER);
                playedCardsLabels.get(i)[j] = label;
                playedCardsPanels.get(i).add(label);
            }
        }
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
        switchToOptionPanel();
    }//GEN-LAST:event_newGameChangeSettingsActionPerformed

    private void switchToOptionPanel() {
        gamePanel.setVisible(false);
        inGamePanel = false;
        optionPanel.setVisible(true);
    }

    private void bigButtonActionPerformed(
        java.awt.event.ActionEvent evt) {//GEN-FIRST:event_nextRoundButtonActionPerformed
        if (playPanel.isVisible()) {
            if (game.hasEnded()) {
                startGame();
            } else {
                game.continueGame();
            }
        } else {
        boolean[] swapArray = new boolean[PokerGame.CARD_HAND_SIZE];
        for (int i = 0; i < swapArray.length; i++) {
            swapArray[i] = throwCheckBoxList.get(i).isSelected();
        }
        game.receiveSwapInput(swapArray);
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

    private void fontSizeSliderStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_fontSizeSliderStateChanged
        updateFontSizeLabel();
    }//GEN-LAST:event_fontSizeSliderStateChanged

    private void updateFontSizeLabel() {
        var value = fontSizeSlider.getValue();
        fontSizeLabel.setText("Schriftgröße " + value);
        fontSizeLabel.setFont(fontSizeLabel.getFont().deriveFont((float) value));
    }

    private void fontSizeCancelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_fontSizeCancelActionPerformed
        closeFontSettings();
    }//GEN-LAST:event_fontSizeCancelActionPerformed

    private void closeFontSettings() {
        fontSizePanel.setVisible(false);
        if (inGamePanel) {
            gamePanel.setVisible(true);
        } else {
            optionPanel.setVisible(true);
        }
    }

    private void fontSizeSaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_fontSizeSaveActionPerformed
        fontSize = fontSizeSlider.getValue();
        updateFont();
        closeFontSettings();
    }//GEN-LAST:event_fontSizeSaveActionPerformed

    private void fontSettingsMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_fontSettingsMouseClicked
        fontSizePanel.setVisible(true);
        if (inGamePanel) {
            gamePanel.setVisible(false);
        } else {
            optionPanel.setVisible(false);
        }
        updateFontSizeLabel();
    }//GEN-LAST:event_fontSettingsMouseClicked

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
                var x = new PokerGUI();
                x.setGame(new PokerGame(x));
                x.setVisible(true);
            }
        });

    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton bigButton;
    private javax.swing.JPanel bigButtonPanel;
    private javax.swing.JLabel difficultyLabel;
    private javax.swing.JPanel difficultyPanel;
    private javax.swing.JSlider difficultySlider;
    private javax.swing.JMenu fontSettings;
    private javax.swing.JButton fontSizeCancel;
    private javax.swing.JLabel fontSizeLabel;
    private javax.swing.JPanel fontSizePanel;
    private javax.swing.JButton fontSizeSave;
    private javax.swing.JSlider fontSizeSlider;
    private javax.swing.JLayeredPane gameAndOptionsPane;
    private javax.swing.JPanel gameDisplay;
    private javax.swing.JPanel gamePanel;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JPanel leftPanel;
    private javax.swing.JPanel middlePanel;
    private javax.swing.JPanel namePanel;
    private javax.swing.JMenuItem newGameChangeSettings;
    private javax.swing.JMenuItem newGameSameSettings;
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
    private javax.swing.JPanel playedCardsPanelContainer;
    private javax.swing.JPanel pointsPanel;
    private javax.swing.JPanel rightPanel;
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
    private final List<JLabel> nameLabels = new LinkedList<>();
    private final List<JPanel> playedCardsPanels = new LinkedList<>();
    private final List<JLabel[]> playedCardsLabels = new LinkedList<>();
    private final List<JLabel> pointsLabels = new LinkedList<>();
    private boolean inGamePanel = false;

}
