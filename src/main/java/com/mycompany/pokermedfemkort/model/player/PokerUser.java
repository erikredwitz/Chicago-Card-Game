/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.pokermedfemkort.model.player;

import com.mycompany.pokermedfemkort.model.InputPlayStrategy;
import com.mycompany.pokermedfemkort.model.InputSwapStrategy;
import com.mycompany.pokermedfemkort.model.PlayStrategy;
import com.mycompany.pokermedfemkort.model.PokerGame;
import com.mycompany.pokermedfemkort.model.SwapStrategy;
import com.mycompany.pokermedfemkort.view.PokerGUI;

/**
 *
 * @author erikr
 */
public class PokerUser extends PokerPlayer {

    public PokerUser(PokerGame game, String name) {
        super(game, name, new InputSwapStrategy(), new InputPlayStrategy(), true);
    }

    public void swapInput(boolean[] swapArray) {
        ((InputSwapStrategy) swapStrategy).swapInput(swapArray);
    }
    
    public void playInput(int playDecision) {
        ((InputPlayStrategy) playStrategy).playInput(playDecision);
    }

}
