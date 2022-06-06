/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.mycompany.pokermedfemkort.model;

import com.mycompany.pokermedfemkort.model.player.PokerPlayer;

import java.util.List;


/**
 * @author erikr
 */
public class InputPlayStrategy extends PlayStrategy {

    private int playDecision = -1;

    public InputPlayStrategy() {
    }
    @Override
    public Card chooseCardToPlay(Suit led, int value, List<PokerPlayer> players, PokerPlayer perspective) {
        boolean[] enabled = PokerGame.getEnabled(led, value, perspective.getHand());
        if(enabled[playDecision]) {
            return perspective.getHand().get(playDecision);
        }
        else{
            int i = 0;
            while(!enabled[i]){
                i++;
            }
            return perspective.getHand().get(i);
        }
    }

    @Override
    public Card chooseCardToBeginWith(List<PokerPlayer> players, PokerPlayer perspective) {
        return perspective.getHand().get(playDecision);
    }

    public void playInput(int playDecision) {
         this.playDecision = playDecision;
    }


}
