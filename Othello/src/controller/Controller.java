/*
 * Class : Controller
 * Description : The unique class which do the link between the view & the game
 */

package controller;

import model.Game;
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
    }
    
    
}
