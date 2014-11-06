package model;

class Location {
    private int row;
    private int col;

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
}
