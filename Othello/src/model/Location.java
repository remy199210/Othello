package model;

public class Location{
    private int row;
    private int col;

    public Location() {
        this.row = 0;
        this.col = 0;
    }

    public Location(int row, int col) {
        if(row<0 || col<0 || row>=Game.gameSize || col>=Game.gameSize)
            throw new GameException("Location constructor : out of bound");
        this.row = row;
        this.col = col;
    }

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }
    
    @Override
    public boolean equals(Object o){
        Location l = (Location) o;
        return row==l.row && col==l.col;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 47 * hash + this.row;
        hash = 47 * hash + this.col;
        return hash;
    }
}
