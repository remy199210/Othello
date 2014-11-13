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
    private Player player2;
    private Player currentPlayer;
    private int [][] board;
    private Set<Integer> placeable;

    /******************
     * Initialisation *
     ******************/
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
        
        //Players initialization (Temporar init)
        player1 = new Player("Bernard", black);
        currentPlayer = player1;
        player2 = new IA("Bot Ur Ass", white);
    }
    
    /**************************************************************************
    *                        Location tools                                   *
    * desc : The location will be an integer generated thanks to the position
    * coordinates. The minLocation=0, maxlocation = gameSize x gameSize -1.
    * 
    * - int getLocation(i,j) permits to generate the location integer
    * 
    * - setLocation(location, i, j) permits to set position coordinates from
    *   the integer location
    ***************************************************************************/
    static int getLocation(int i, int j){
        return gameSize*i +j;
    }
    
    static Location setLocation(int location){
        int temp = location%gameSize;
        return new Location((location-temp)/gameSize, temp);
    }
    
    /**************************************************************************
     *                      System tools                                      *
     * - getEnemiesNeighbors(i, j)
     * - getAlliesNeighbors(i, j)
     * @param i,j : Location coordinates
     * @return Set<Integer> : Enemies/Allies Neighbors Array
     *************************************************************************/
    Set<Integer> getOppositeNeighboors(int i, int  j){
        Set res = new HashSet<>();
        /*int iMin, iMax, jMin, jMax;
        (i>0) ? iMin=(i-1):iMin=i;
        (i<(gameSize-1)) ? iMax=i+1:iMax=i;
        for(;iMin<iMax;iMin++){
            
        }*/
        return res;
    }
    /**************************************************************************
     *                      System functions                                  *
     * - updateBoard(i, j)
     * - updatePlaceable()
     *************************************************************************/
    public void updateBoard(int i,int j){
        
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
        Location l = setLocation(35);
        int i= l.getRow();
        int j= l.getCol();
        System.out.println("i = "+i+"; j = "+j);
    }
    
}
