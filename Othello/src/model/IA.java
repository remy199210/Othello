/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.util.HashSet;
import java.util.Set;

/**
 *
 * @author Rémy
 */
class IA extends Player{
    
    private int level;
    
    public IA(String name, int color, int level) {
        super(name, color);
        this.level  = level;
    }
    /**
     * 
     * @param board
     * @param color
     * @param i
     * @param j
     * @param iAxis
     * @param jAxis
     * @return 
     */
    private int getAxisModifications(int[][] board, int color, int i, int j, int iAxis, int jAxis){
        int iNext = i+iAxis,jNext = j+jAxis;
        if(iNext>=0&&jNext>=0&&iNext<Game.gameSize&&jNext<Game.gameSize){
            if(board[iNext][jNext]==color)
                return 1;
            if(board[iNext][jNext]==-color)
                return 1+getAxisModifications(board,color, iNext, jNext, iAxis, jAxis);
        }
        return 0;
    }
    private Location getMaxModificationsLocation(){
        return null;
    }
    
    public Location getMove(Set<Location> placeable){
        /*if(!isPlaceable(i, j))
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
        //On testera ici si placeable.isEmpty puis on getWinner et fin du game
        return res;*/
        return null;
    }
}
