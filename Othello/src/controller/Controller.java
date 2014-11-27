/*
 * Class : Controller
 * Description : The unique class which do the link between the view & the game
 */

package controller;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import model.Game;
import view.Case;
import view.View;

/**
 *
 * @author Rémy
 */
public class Controller {
    private Game     m_game;
    private View     m_view;
    private boolean play = false;

    public Controller(Game m_game, View m_view) {
        this.m_game = m_game;
        this.m_view = m_view;
        m_game.addObserver(this.m_view);
        initListeners();
    }
    private void initListeners(){
        m_view.addLocalMouseListener(new LocalMouseListener());
    }
    public class LocalMouseListener extends MouseAdapter{
        @Override
        public void mouseClicked(MouseEvent e) {
           Case c = (Case) e.getSource();
           if(c.isPlaceable() && m_game.isRunning()){
               m_game.updateBoard(c.getRow(), c.getCol());
           }
        }

        @Override
        public void mouseEntered(MouseEvent e) {
            if(((Case)e.getSource()).isPlaceable()){
               ((Case)e.getSource()).placeableHover(m_game.getCurrentColor());
            }
        }

        @Override
        public void mouseExited(MouseEvent e) {
            Case c = (Case) e.getSource();
            if(c.isPlaceable())
                c.setPlaceable(true);
        }
    }
}