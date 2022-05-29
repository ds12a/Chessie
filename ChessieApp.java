import edu.kzoo.grid.display.DisplayMap;
import edu.kzoo.grid.display.PictureBlockDisplay;

/**
 * Chessie:<br>
 *
 * The <code>Chess110App</code> class provides the <code>main</code>
 * method for a Chessie program.
 *
 * @author David Shen
 * @version 5/26/21
 **/
public class ChessieApp {

    public static void main(String[] args) {
        // Create the object that will control the game.
        ChessieController controller = new ChessieController();

        // Create the object that will provide the Graphical User Interface.
        DisplayMap.associate("edu.kzoo.grid.PictureBlock", new PictureBlockDisplay());
        ChessieGUI gui = new ChessieGUI(controller);
    }
}