/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package view;

import java.awt.Color;
import java.awt.Cursor;
import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.border.Border;
import model.Game;

/**
 *
 * @author Jo
 */
public class Case extends JPanel{
    private static final Border greyBorder = BorderFactory.createLineBorder(Color.DARK_GRAY,1);
    private static final Border redBorder = BorderFactory.createBevelBorder(1,Color.RED,Color.RED);
    private int row,col;
    private boolean placeable;
    
    Case(int i, int j) {
        row = i;
        col = j;
        setBackground(Color.lightGray);
    }

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }
    
    
    public boolean isPlaceable(){
        return placeable;
    }

    public void setWhite() {
        this.setBackground(Color.WHITE);
    }

    public void setBlack() {
        this.setBackground(Color.BLACK);
    }

    public void setPlaceable(boolean b) {
        if(b){
          this.setBackground(Color.lightGray);
          setBorder(redBorder);
          placeable = true;
        }
        else{
            setBorder(greyBorder);
            this.setBackground(Color.lightGray);
            placeable = false;
        }
          
    }
    
    public void placeableHover(int color) {
        if(color==Game.black)
            this.setBlack();
        else this.setWhite();
        setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
    }

    void setEmpty() {
        this.setBackground(Color.lightGray);
        setBorder(greyBorder);
        placeable = false;
    }
    
    
}
