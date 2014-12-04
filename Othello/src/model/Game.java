/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package model;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashSet;
import java.util.Observable;
import java.util.Observer;
import java.util.Scanner;
import java.util.Set;

public class Game extends Observable implements Runnable {
    //colors
    public static final String RED = "\u001B[31m";
    public static final String RESET = "\u001B[0m";
    // Statics variables
    public static final int black =-1;
    public static final int empty  =0;
    public static final int white =1;
    public static final int gameSize = 8;
    private  static final int IATEMPO = 50;
    
    //Attributes
    protected Player player1;
    protected Player player2;
    protected int nbBlack;
    protected int nbWhite;
    protected Player currentPlayer;
    protected int [][] board;
    protected int [][] poids;
    protected Set<Location> placeable;
    protected boolean runningIA;
    protected boolean runningGame;
    protected boolean copy;
    protected Thread gameThread;

    /******************
     * Initialisation *
     ******************/
    public Game() {
        copy=false;
        board = new int [gameSize][gameSize];
        poids = charge("poids.txt");
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
        
        nbWhite = 2;
        nbBlack = 2;
        //Now we can initialize the placeable tab because among rules Black Player always begins
        placeable = new HashSet<>();
        placeable.add(new Location(3, 2));
        placeable.add(new Location(2, 3));
        placeable.add(new Location(4, 5));
        placeable.add(new Location(5, 4));
        
        //Players initialization (Temporar init)
        player1 = new Player("Bernard", black);
//        player1 = new IA("Bot what ?",black, 2);
        player2 = new IA("Bot ur fucking ass",white, 3);
//        player1 = new IA("Bot what ?", black,1);
        currentPlayer = player1;
        runningGame=true;
    }
    
    public boolean isRunningIA() {
        return runningIA;
    }
    public boolean isRunningGame() {
        return runningGame;
    }
    private int[][] charge(String fileName){
        int[][] res =new int[gameSize][gameSize];
        int i=0, j=0;
        try {
            Scanner sc = new Scanner(new File(fileName));
            while(sc.hasNext()){
                if(j>=gameSize){
                    j=0;
                    i++;
                }
                res[i][j]=Integer.parseInt(sc.next());
                j++;
            }
        } catch (Exception e) {
        }
        return res;
    }
    public void majPoids(){
        for(Location l:getWinner().coups){
            poids[l.row][l.col]++;
        }
        for(Location l:getLooser().coups){
            poids[l.row][l.col]--;
        }
    }
    public void save(String fileName){
        try{
            FileWriter fw = new FileWriter(fileName);
            BufferedWriter out = new BufferedWriter(fw);
            for (int i = 0; i < gameSize; i++) {
                for (int j = 0; j < gameSize; j++) {
                    out.write(poids[i][j]+(poids[i][j]>-1?"  ":" "));
                    out.flush();
                }
                out.write("\n");
            }
            out.close();
        }catch(IOException ioe){
                ioe.printStackTrace();
        }
    }
    public Game(Game g) {
        copy=true;
        board = new int[gameSize][gameSize];
        for (int i = 0; i < gameSize; i++) {
            for (int j = 0; j < gameSize; j++) {
                board[i][j]=g.board[i][j];
            }
        }
        currentPlayer = g.currentPlayer;
        player1=g.player1;
        player2=g.player2;
        nbBlack = g.nbBlack;
        nbWhite = g.nbWhite;
        placeable = new HashSet<>();
        updatePlaceable();
        runningGame=true;
    }
    
     /**************************************************************************
     *                      Observer design Patern
     *************************************************************************/
    @Override
    public void addObserver(Observer obs) {
        super.addObserver(obs);
    }   
    /**************************************************************************
     *                      Game Tools                                        *
     * switchPlayer will change the current player
     *************************************************************************/
    private void switchPlayer(){
        if(currentPlayer.color==player1.color)
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
                if(board[i][j]==player1.color) p1++;
                if(board[i][j]==player2.color) p2++;
            }
        }
        if(p1>p2) return player1;
        if(p1<p2) return player2;
        return null;
    }
    
    public Player getLooser(){
        if(player1.equals(getWinner()))
            return player2;
        else return player1;
    }

    public boolean isIAPlaying() {
        return (currentPlayer instanceof  IA);
    }
    
    public void runThread(){
        gameThread = new Thread(this);
        gameThread.start();
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
                if(rotateAxis(color, iNext, jNext, iAxis, jAxis, res)){
                    board[i][j]=color;
                    res.add(new Location(i, j));
                    return true;
                }
            }
        }
        return false;
    }
    /**
     * updatePlaceable will update the placeable list after each update
     */
    private void updatePlaceable(){
        int color = currentPlayer.color;
        Set<Location> neighbors;//Contrary color neighbors
        placeable.clear();
        if(!copy)
            System.out.println("Je clean "+ currentPlayer.name);
        for (int i = 0; i < gameSize; i++) {
            for (int j = 0; j < gameSize; j++) {
                if(board[i][j]==empty){
                    neighbors = new Location(i, j).getNeighbors(board, -color);
                    if (!neighbors.isEmpty()) {
                        for(Location neighbor:neighbors){
                            if(playableAxis(color, neighbor.row, neighbor.col,
                                            neighbor.row-i, neighbor.col-j)){
                                placeable.add(new Location(i, j));
                                if(!copy)
                                    System.out.println("+1 placable");
                            }
                        }
                    }
                }
            }
        }
        if(placeable.isEmpty()){
            runningGame=false;
            if(!copy){
                setChanged();
                notifyObservers("end");
                Thread.currentThread().interrupt();
                majPoids();
                save("poids.txt");
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
     *                      Public System functions                                  *
     * updateBoard will update for each move the board
     * @param i row of the new move
     * @param j column of the new move
     * @return Set<Location> modified locations list
     *************************************************************************/
    public Set<Location> updateBoard(int i,int j){
        if(runningGame){
            if(!isPlaceable(i, j))
                throw new GameException("updateBoard : unplaceable location");
            Set<Location> res = new HashSet<>();
            int color = currentPlayer.color;
            board[i][j]=color;
            res.add(new Location(i, j));
            Set<Location> neighbors = new Location(i, j).getNeighbors(board, -color);
            if (!neighbors.isEmpty()) {
                for(Location neighbor:neighbors){
                    rotateAxis(color, neighbor.row, neighbor.col,
                               neighbor.row-i, neighbor.col-j,res);
                }
            }
            setNbColor(color, res.size()-1);
            switchPlayer();
            updatePlaceable();
            if(!copy){
                System.out.println("Je joue");
                currentPlayer.coups.add(new Location(i, j));
                if(currentPlayer instanceof IA){
                    setChanged();
                    notifyObservers("IA");
                    runThread();
                }else{
                    setChanged();
                    notifyObservers("Your turn "+currentPlayer.name);
                }
            }
            return res;
        }
        return new HashSet<>();
    }
    
    private void setNbColor(int color, int score){
        if(color == black){
            nbWhite = nbWhite-score;
            nbBlack+= score+1;
        }
        else if(color == white){
            nbBlack = nbBlack-score;
            nbWhite+= score+1;
        }
    }
    
    public int getScoreColor(int color){
        if(color == black)
            return nbBlack-nbWhite;
        else if(color == white)
            return nbWhite-nbBlack;
        else
            throw new GameException("Game color doesn't exist");
    }
    
    
    public int getColor(int i, int j){
        return board[i][j];
    }
    public int getCurrentColor(){
        return currentPlayer.color;
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
    }

    @Override
    public void run() {
        if(!runningIA){
            runningIA=true;
            synchronized(this){
                for (int i = 3; i>0; i--) {
                    setChanged();
                    notifyObservers(currentPlayer.name+" "+i+"sec");
                    try {
                        gameThread.sleep(IATEMPO);
                    } catch (InterruptedException ex) {
                        //Logger.getLogger(Game.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                setChanged();
                notifyObservers("IA 0 sec");
                Location l = currentPlayer.getMove(this);
                updateBoard(l.row, l.col);
                runningIA=false;
                gameThread.interrupt();
            }
        }
    }
}
