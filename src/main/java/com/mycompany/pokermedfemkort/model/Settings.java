package com.mycompany.pokermedfemkort.model;

public class Settings {

    private int opponentCount;
    private int difficulty;

    public void setOpponentCount(int opponentCount) {
        this.opponentCount = opponentCount;
    }

    public void setDifficulty(int difficulty) {
        this.difficulty = difficulty;
    }

    public int getOpponentCount() {
        return opponentCount;
    }

    public int getDifficulty() {
        return difficulty;
    }
}
