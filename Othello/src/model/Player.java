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
public class Player {
    private String name;
    private int color;

    public Player(String name, int color) {
        this.name = name;
        this.color = color;
    }

    public String getName() {
        return name;
    }

    public int getColor() {
        return color;
    }
    
    
}
