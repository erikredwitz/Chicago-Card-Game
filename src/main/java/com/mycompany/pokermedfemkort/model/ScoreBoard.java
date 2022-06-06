package com.mycompany.pokermedfemkort.model;


import com.mycompany.pokermedfemkort.model.player.PokerPlayer;
import java.util.LinkedList;
import java.util.List;

public class ScoreBoard {
    private final List<ScoreBoardColumn> columns;

    public ScoreBoard(List<PokerPlayer> players) {
        columns = new LinkedList<>();
        for (PokerPlayer player : players) {
            columns.add(new ScoreBoardColumn(player, 0));
        }
    }

    public int getScoreOfPlayer(PokerPlayer player) {
        for (ScoreBoardColumn column : columns) {
            if(column.getPlayer() == player){
                return column.getScore();
            }
        }
        return 0;
    }
    public void increaseScoreOfPlayer(PokerPlayer player, int additional) {
        for (ScoreBoardColumn column : columns) {
            if(column.getPlayer() == player){
                column.increaseScore(additional);
            }
        }
    }

    public int getHighestScore() {
        var highest = 0;
        for (ScoreBoardColumn column : columns) {
            if(column.getScore() > highest){
                highest = column.getScore();
            }
        }
        return highest;
    }

    public void resetScore(){
        for(ScoreBoardColumn column : columns){
            column.resetScore();
        }
    }
}
