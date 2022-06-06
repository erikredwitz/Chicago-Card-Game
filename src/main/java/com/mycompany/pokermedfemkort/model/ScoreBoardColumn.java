package com.mycompany.pokermedfemkort.model;

import com.mycompany.pokermedfemkort.model.player.PokerPlayer;

public class ScoreBoardColumn {
    private final PokerPlayer player;
    private int score;

    public ScoreBoardColumn(PokerPlayer player, int score) {
        this.player = player;
        this.score = score;
    }

    public PokerPlayer getPlayer() {
        return player;
    }

    public int getScore() {
        return score;
    }

    public void increaseScore(int additional) {
        score += additional;
    }
    public void resetScore() {
        score = 0;
    }

}
