/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.mycompany.pokermedfemkort.model.player;

import com.mycompany.pokermedfemkort.model.BotPlayStrategy;
import com.mycompany.pokermedfemkort.model.BotSwapStrategy;
import com.mycompany.pokermedfemkort.model.Card;
import com.mycompany.pokermedfemkort.model.PokerGame;

import java.util.List;

/**
 * @author erikr
 */
public class PokerBot extends PokerPlayer {


    public PokerBot(PokerGame game, String name) {
        super(game, name, new BotSwapStrategy(), new BotPlayStrategy(), false);
    }


}
