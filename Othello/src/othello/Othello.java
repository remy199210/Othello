/*
 * author : Jordan Daita & Rémy Drouet
 *
 * project : Developing an IA for un simple 2-players game
 *
 * game : Reversi (Othello in french)
 *
 * description :
 *          " Reversi is a strategy board game for two players, played on an 8×8
 *      uncheckered board. There are sixty-four identical game pieces called
 *      disks(often spelled "discs"), which are light on one side and dark on
 *      the other.
 *          Players take turns placing disks on the board with their assigned
 *      color facing up. During a play, any disks of the opponent's color that
 *      are in a straight line and bounded by the disk just placed and another
 *      disk of the current player's color are turned over to the current
 *      player's color.
 *
 *          The object of the game is to have the majority of disks turned to
 *      display your color when the last playable empty square is filled. "
 *      -- source : Wikipedia.org/Reversi
 */

package othello;

import controller.Controller;
import javax.swing.SwingUtilities;
import model.Game;
import view.View;

public class Othello {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                //Game & View created and initialised
                Game        game        = new Game();
                View        view        = new View();
                view.setVisible(true);
                Controller controller = new Controller(game,view);
            }
        });
    }
    
}
