package model;

import java.util.HashSet;
import java.util.Set;
import static model.Game.gameSize;

public class Location{
    protected int row;
    protected int col;
    int weight;

  

    public Location() {
        this.row = -1;
        this.col = -1;
    }

    public Location(int row, int col) {
        if(row<0 || col<0 || row>=Game.gameSize || col>=Game.gameSize)
            throw new GameException("Location constructor : out of bound");
        this.row = row;
        this.col = col;
    }
    public Set<Location> getNeighbors(int[][] board, int color){
        Set<Location> res = new HashSet<>();
        int iMax=row<gameSize-1?row+1:row;
        int jMax=col<gameSize-1?col+1:col;
        for(int iMin=row>0?row-1:row;iMin<=iMax;iMin++){
            for(int jMin=col>0?col-1:col;jMin<=jMax;jMin++){
                if((iMin!=row || jMin!=col)&&board[iMin][jMin]==color){
                    res.add(new Location(iMin, jMin));
                }
            }
        }
        return res;
    }
    @Override
    public boolean equals(Object o){
        Location l = (Location) o;
        return row==l.row && col==l.col;
    }
    
    public void copy(Location l){
        this.row = l.row;
        this.col = l.col;
    }
    
      public void raiseWeight(int weight) {
        this.weight+=weight;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 47 * hash + this.row;
        hash = 47 * hash + this.col;
        return hash;
    }
}
