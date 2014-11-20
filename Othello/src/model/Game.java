/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package model;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Observable;
import java.util.Observer;
import java.util.Scanner;
import java.util.Set;

public class Game extends Observable {
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
    private ArrayList<Observer> listObservateur = new ArrayList<Observer>();

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
        player2 = new IA("Bot Ur Ass", white, 0);
    }
    /**************************************************************************
     *                      Game Tools                                        *
     * switchPlayer will change the current player
     *************************************************************************/
    private void switchPlayer(){
        if(currentPlayer.getColor()==player1.getColor())
            currentPlayer=player2;
        else currentPlayer=player1;
    }
    /**
     * 
     */
    public Player getWinner(){
        int p1 = 0, p2 = 0;
        for (int i = 0; i < gameSize; i++) {
            for (int j = 0; j < gameSize; j++) {
                if(board[i][j]==player1.getColor()) p1++;
                if(board[i][j]==player2.getColor()) p2++;
            }
        }
        if(p1>p2) return player1;
        if(p1<p2) return player2;
        return null;
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
    private Set<Location> getNeighbors(int color,int i, int  j){
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
     * playableAxis determine if, on a specified axis, pieces will rotate
     * @param i location's row
     * @param j location's column
     * @param iAxis row trajectory
     * @param jAxis column trajectory
     * @param color first color of the axis
     * @return boolean which means if the move is possible
     */
    private boolean playableAxis(int color, int i, int j, int iAxis, int jAxis){
        int iNext = i+iAxis,jNext = j+jAxis;
        if(iNext>=0&&jNext>=0&&iNext<gameSize&&jNext<gameSize){
            if(board[iNext][jNext]==color)
                return true;
            if(board[iNext][jNext]==-color)
                return playableAxis(color, iNext, jNext, iAxis, jAxis);
        }
        return false;
    }
    /**
     * rotateAxis will apply move consequences
     * @param i location's row
     * @param j location's column
     * @param iAxis row trajectory
     * @param jAxis column trajectory
     * @param color first color of the axis
     * @param res save rotate locations
     */
    private boolean rotateAxis(int color, int i, int j, int iAxis, int jAxis, Set<Location> res){
        int iNext = i+iAxis,jNext = j+jAxis;
        if(iNext>=0&&jNext>=0&&iNext<gameSize&&jNext<gameSize){
            if(board[iNext][jNext]==color){
                board[i][j]=color;
                res.add(new Location(i, j));
                return true;
            }
            if(board[iNext][jNext]==-color){
                board[i][j]=color;
                res.add(new Location(i, j));
                return rotateAxis(color, iNext, jNext, iAxis, jAxis, res);
            }
        }
        return false;
    }
    /**
     * updatePlaceable will update the placeable list after each update
     */
    private void updatePlaceable(){
        int color = currentPlayer.getColor();
        Set<Location> neighbors;//Contrary color neighbors
        placeable.clear();
        for (int i = 0; i < gameSize; i++) {
            for (int j = 0; j < gameSize; j++) {
                if(board[i][j]==empty){
                    neighbors = getNeighbors(-color, i, j);
                    if (!neighbors.isEmpty()) {
                        for(Location neighbor:neighbors){
                            if(playableAxis(color, neighbor.getRow(), neighbor.getCol(),
                                            neighbor.getRow()-i, neighbor.getCol()-j))
                                placeable.add(new Location(i, j));
                        }
                    }
                }
            }
        }
    }
    /**
     * isPlaceable
     * @param l
     * @return 
     */
    public boolean isPlaceable(Location l) {
        return placeable.contains(l);
    }
    /**
     * isPlaceable
     * @param i
     * @param j
     * @return 
     */
    public boolean isPlaceable(int i, int j) {
        return placeable.contains(new Location(i, j));
    }
     /**************************************************************************
     *                      Observer design Patern                                       *
     * 
     * 
     * @param obs
     *************************************************************************/
    @Override
    public void addObserver(Observer obs) {
        this.listObservateur.add(obs);
    }
    
    @Override
    public void notifyObservers() {
        for(Observer obs : this.listObservateur )
            obs.update(this, board);
    }
    
    
    /**************************************************************************
     *                      Public System functions                                  *
     * updateBoard will update for each move the board
     * @param i row of the new move
     * @param j column of the new move
     * @return Set<Location> modified locations list
     *************************************************************************/
    public Set<Location> updateBoard(int i,int j){
        if(!isPlaceable(i, j))
            throw new GameException("updateBoard : unplaceable location");
        Set<Location> res = new HashSet<>();
        int color = currentPlayer.getColor();
        board[i][j]=color;
        res.add(new Location(i, j));
        Set<Location> neighbors = getNeighbors(-color, i, j);
        if (!neighbors.isEmpty()) {
            for(Location neighbor:neighbors){
                rotateAxis(color, neighbor.getRow(), neighbor.getCol(),
                           neighbor.getRow()-i, neighbor.getCol()-j,res);
            }
        }
        switchPlayer();
        updatePlaceable();
        notifyObservers();
        //On testera ici si placeable.isEmpty puis on getWinner et fin du game
        return res;
    }
    
    public int getColor(int i, int j){
        return board[i][j];
    }
    /**
     * Display functions with an highlight of a specified list of location
     * @param highlight locations'list we want to highlight
     * @return String to show the game in the consol
     */
    public String toString(Set<Location> highlight) {
        String res="    0  1  2  3  4  5  6  7\n___________________________\n";
        for (int i = 0; i < gameSize; i++) {
            res+=i+"| ";
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
        Location randomIA = new Location();
        Game game = new Game();
        Scanner sc = new Scanner(System.in);
        int i=0,j=0;
        while(!game.placeable.isEmpty()){
            System.out.println("Player : "+game.currentPlayer.getName());
            System.out.println(RED+"Placeable locations"+RESET);
            System.out.println(game.toString(game.placeable));
            do{
                System.out.print("Row : ");
                if(sc.hasNextInt())
                    i=sc.nextInt();
                System.out.print("Column : ");
                if (sc.hasNextInt())
                    j=sc.nextInt();
            }while(!game.isPlaceable(i, j));
            System.out.println(RED+"Move Result"+RESET);
            System.out.println(game.toString(game.updateBoard(i, j)));
            //randomIA=game.placeable.iterator().next();
            //System.out.println(game.toString(game.updateBoard(randomIA.getRow(), randomIA.getCol())));
        }
        Player winner = game.getWinner();
        if(winner!=null)
            System.out.println("The winner is "+winner.getName()+"!!");
        else System.out.println("Null Game : There's no winner ...");
    }
    
}
