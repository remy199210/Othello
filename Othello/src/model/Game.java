/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package model;

import java.util.HashSet;
import java.util.Set;

public class Game {
    //colors
    public static final String RED = "\u001B[31m";
    public static final String RESET = "\u001B[0m";
    // Statics variables
    public static final int black =-1;
    public static final int none  =0;
    public static final int white =1;
    public static final int gameSize = 8;
    
    //Attributes
    private Player player1;
    private IA player2;
    private Player currentPlayer;
    private int [][] board;
    private Set<Integer> placeable;

    public Game() {
        board = new int [gameSize][gameSize];
        // In the all code "i" will represent row et "j" column
        for (int i = 0; i < gameSize; i++) {
            for (int j = 0; j < gameSize; j++) {
                board[i][j]=none;
            }
        }
        /*
        * At the begining of the game there's always 2 black pieces on location
        * e4 & d5 and 2 white pieces on e5 & d4 (row=number, column=lettres)
        */
        board[3][3]=white;
        board[4][4]=white;
        board[3][4]=black;
        board[4][3]=black;    
        
        //Now we can initialize the placeable tab because among rules Black Player always begins
        placeable = new HashSet<>();
        placeable.add(3*gameSize+2);
        placeable.add(2*gameSize+3);
        placeable.add(5*gameSize+4);
        placeable.add(4*gameSize+5);
    }
    
    static int getLocation(int i, int j){
        return gameSize*i +j;
    }
    
    static void setLocation(int location, int i, int j){
        j=location%gameSize;
        i=(location-j)/gameSize;
    }
    
    @Override
    public String toString() {
        String res="";
        for (int i = 0; i < gameSize; i++) {
            for (int j = 0; j < gameSize; j++) {
                if(placeable.contains(getLocation(i, j)))
                    res+=RED;
                if(board[i][j]>=0)
                    res+=" ";
                res+=board[i][j]+" "+RESET;
            }
            res+="\n";
        }
        return res;
    }
    
    public static void main(String[] args) {
        Game game = new Game();
        System.out.println(game);
    }
    
}
