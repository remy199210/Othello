/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.util.Scanner;

/**
 *
 * @author Rémy
 */
public class Player {

    protected String name;
    protected int color;

    public Player(String name, int color) {
        this.name = name;
        this.color = color;
    }

    public Location getMove(Game game) {
        Scanner sc = new Scanner(System.in);
        int i=-1, j=-1;
        do {
            System.out.print("Row : ");
            if (sc.hasNextInt()) {
                i = sc.nextInt();
            }
            System.out.print("Column : ");
            if (sc.hasNextInt()) {
                j = sc.nextInt();
            }
        } while (!game.isPlaceable(i, j));
        return new Location(i, j);

    }

    public String getName() {
        return name;
    }

}
