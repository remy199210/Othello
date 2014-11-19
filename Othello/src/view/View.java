/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package view;

import controller.Controller;
import java.awt.Color;
import java.util.Observable;
import java.util.Observer;
import javax.swing.BorderFactory;
import javax.swing.border.Border;
import model.Game;
import static model.Game.black;
import static model.Game.empty;
import static model.Game.gameSize;
import static model.Game.white;

/**
 *
 * @author Rémy
 */
public class View extends javax.swing.JFrame implements Observer {

    //The view has an instance of both the model and the controller in MVC design pattern
    private model.Game gameModel;
    private Case[][] board;

    
    /**
     * Initialisation *
     * @param model
     */
    public View(Game model) {
        this.gameModel = model;
        board = new Case [gameSize][gameSize];
        initComponents();
        init();
    }
    
     /**
     * init initialize the components not created by the form editor : othello grid
     */
    private void init(){
 
        Border myborder = BorderFactory.createLineBorder(Color.DARK_GRAY,1);
        
        for(int i = 0; i<gameSize;i++){
            for(int j = 0; j< gameSize; j++){
                board[i][j]= new Case(i,j);
                board[i][j].setBorder(myborder);
                panel_board.add(board[i][j]);
            }
        }
        initBoard();
    }
    
    public void initBoard() {
        for (int i = 0; i < gameSize; i++) {
            for (int j = 0; j < gameSize; j++) {
                if(gameModel.isPlaceable(i, j))
                    board[i][j].setPlaceable(true);
                else if(gameModel.getColor(i, j)==black)
                    board[i][j].setBlack();
                else if(gameModel.getColor(i, j)==white)
                    board[i][j].setWhite();
            }
        }
    }
    
    /**
     * To add a controller for this view
     * @param controller 
     */
     public void addController(Controller controller) {
        for(int i = 0; i<gameSize;i++){
            for(int j = 0; j< gameSize; j++){
                board[i][j].addMouseListener(controller);
            }
        }
    
     }
        /**
         * Implementation of Observer disgn pattern
         * 
         * @param o
         * @param arg 
         */
    @Override
    public void update(Observable o, Object arg) {
        for (int i = 0; i < gameSize; i++) {
            for (int j = 0; j < gameSize; j++) {
                board[i][j].setEmpty();
                if(gameModel.isPlaceable(i, j))
                    board[i][j].setPlaceable(true);
                else if(gameModel.getColor(i, j)==black)
                    board[i][j].setBlack();
                else if(gameModel.getColor(i, j)==white)
                    board[i][j].setWhite();
            }
        }
    }
   
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        panel_board = new javax.swing.JPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        panel_board.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        panel_board.setOpaque(false);
        panel_board.setLayout(new java.awt.GridLayout(8, 8));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(78, 78, 78)
                .addComponent(panel_board, javax.swing.GroupLayout.PREFERRED_SIZE, 230, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(92, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(35, 35, 35)
                .addComponent(panel_board, javax.swing.GroupLayout.PREFERRED_SIZE, 230, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(35, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel panel_board;
    // End of variables declaration//GEN-END:variables


        /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(View.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(View.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(View.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(View.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                new View(new Game()).setVisible(true);
            }
        });
    }
}

