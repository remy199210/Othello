/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package model;

/**
 *
 * @author Rémy
 */
class Player {
    String name;
    int color;

    public Player(String name, int color) {
        this.name = name;
        this.color = color;
    }

    public int getColor() {
        return color;
    }
    
    
}
