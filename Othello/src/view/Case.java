/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package view;

import java.awt.Color;
import javax.swing.JPanel;

/**
 *
 * @author Jo
 */
public class Case extends JPanel{
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
          this.setBackground(Color.RED);
          placeable = true;
        }
        else{
             this.setBackground(Color.lightGray);
             placeable = false;
        }
          
    }
    
    public void placeableHover() {
        this.setBackground(Color.GREEN);
    }

    void setEmpty() {
        this.setBackground(Color.lightGray);
        placeable = false;
    }
    
    
}
