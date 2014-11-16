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
    public static final int empty  =0;
    public static final int white =1;
    public static final int gameSize = 8;
    
    //Attributes
    private Player player1;
    private Player player2;
    private Player currentPlayer;
    private int [][] board;
    private Set<Location> placeable;

    /******************
     * Initialisation *
     ******************/
    public Game() {
        board = new int [gameSize][gameSize];
        // In the all code "i" will represent row et "j" column
        for (int i = 0; i < gameSize; i++) {
            for (int j = 0; j < gameSize; j++) {
                board[i][j]=empty;
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
        placeable.add(new Location(3, 2));
        placeable.add(new Location(2, 3));
        placeable.add(new Location(4, 5));
        placeable.add(new Location(5, 4));
        
        //Players initialization (Temporar init)
        player1 = new Player("Bernard", black);
        currentPlayer = player1;
        player2 = new IA("Bot Ur Ass", white);
    }
    
    /**************************************************************************
    *                        Location tools                                   *
    * - setBoard
    * @param Location / i,j
    * @param color
    ***************************************************************************/
    
    private void setBoard(Location location, int color){
        board[location.getRow()][location.getCol()]=color;
    }
    private void setBoard(int i, int j, int color){
        board[i][j]=color;
    }
    
    /**************************************************************************
     *                      System tools                                      *
     * - hasNeighbors return a boolean
     * - getNeighbors
     * @param color : to know which type of neighbors we're searching for
     * @param i,j : Location coordinates
     * @return Set<Integer> : Enemies/Allies Neighbors Array
     *************************************************************************/
    boolean hasNeighbors(int color, int i, int j){
        int iMax=i<gameSize-1?i+1:i;
        int jMax=j<gameSize-1?j+1:j;
        for(int iMin=i>0?i-1:i;iMin<=iMax;iMin++){
            for(int jMin=j>0?j-1:j;jMin<=jMax;jMin++){
                if((iMin!=i || jMin!=j)&&board[iMin][jMin]==color){
                    return true;
                }
            }
        }
        return false;
    }
    Set<Location> getNeighbors(int color,int i, int  j){
        Set<Location> res = new HashSet<>();
        int iMax=i<gameSize-1?i+1:i;
        int jMax=j<gameSize-1?j+1:j;
        for(int iMin=i>0?i-1:i;iMin<=iMax;iMin++){
            for(int jMin=j>0?j-1:j;jMin<=jMax;jMin++){
                if((iMin!=i || jMin!=j)&&board[iMin][jMin]==color){
                    res.add(new Location(iMin, jMin));
                }
            }
        }
        return res;
    }
    /**
     * - getModifiedPieces()
     * @param i,j
     * @return Set<Integer>
     */
    private Set<Integer> getModifiedPieces(){
        Set<Integer> res = new HashSet<>();
        return res;
    }
    /**************************************************************************
     *                      System functions                                  *
     * - updateBoard(i, j)
     * - updatePlaceable()
     * @param i
     * @param j
     *************************************************************************/
    public Set<Integer> updateBoard(int i,int j){
        Set<Integer> res = new HashSet<>();
        
        return res;
    }
    
    public void updatePlaceable(){
        int color = currentPlayer.getColor();
        Set<Location> neighbors;//Contrary color neighbors
        placeable.clear();
        for (int i = 0; i < gameSize; i++) {
            for (int j = 0; j < gameSize; j++) {
                if(board[i][j]==empty){
                    neighbors = getNeighbors(-color, i, j);
                    if (!neighbors.isEmpty()) {
                        
                    }
                }
            }
        }
    }
    /**
     * Display functions
     * @param highlight
     * @return String
     */
    public String toString(Set<Location> highlight) {
        String res="";
        for (int i = 0; i < gameSize; i++) {
            for (int j = 0; j < gameSize; j++) {
                if(highlight.contains(new Location(i, j))){
                    res+=RED;
                }
                if(board[i][j]>=0)
                    res+=" ";
                res+=board[i][j]+" "+RESET;
            }
            res+="\n";
        }
        return res;
    }
    @Override
    public String toString() {
        String res="";
        for (int i = 0; i < gameSize; i++) {
            for (int j = 0; j < gameSize; j++) {
                if(board[i][j]>=0)
                    res+=" ";
                res+=board[i][j]+" ";
            }
            res+="\n";
        }
        return res;
    }
    
    public static void main(String[] args) {
        Game game = new Game();
        System.out.println(game.toString(game.getNeighbors(black,3, 3)));
        
    }
    
}
