import java.util.ArrayList;

import edu.kzoo.grid.GridObject;
import edu.kzoo.grid.gui.SteppedGridAppController;
import edu.kzoo.util.Debug;

/**
 * Chessie:<br>
 *
 * A <code>Chess110Controller</code> object controls the action
 * of a Chess 110 chess game.
 *
 * @author David Shen
 * @version 5/30/21
 **/
public class ChessieController extends SteppedGridAppController {
    private boolean whitesTurn = true;
    private ChessieGUI gui = null;
    private Player white;
    private Player black;

    public ChessieController() {
    }

    /**
     * Sets the graphical user interface that the controller will
     * communicate with as the game progresses.
     **/
    public void setGUI(ChessieGUI gui) {
        this.gui = gui;
    }

    /*
     * (non-Javadoc)
     * 
     * @see edu.kzoo.grid.gui.GridAppController#step()
     */
    @Override
    public void step() {
        // If the game is over, don't do anything.
        if (hasReachedStoppingState())
            return;
        // Otherwise, move all the pieces that can move for the current player.
        ChessieBoard cb = (ChessieBoard) getGrid();
        Debug.println("Move number " + cb.getMoveNum() + ", " + (whitesTurn ? "White's" : "Black's") + " turn");
        if (whitesTurn)
            white.takeTurn(cb);
        else {
            black.takeTurn(cb);
            cb.notifyMoveComplete();
        }

        // Next time it should be the other player's turn.
        whitesTurn = !whitesTurn;
    }

    /*
     * (non-Javadoc)
     * 
     * @see edu.kzoo.grid.gui.GridAppController#hasReachedStoppingState()
     */
    @Override
    public boolean hasReachedStoppingState() {
        ChessieBoard cb = (ChessieBoard) getGrid();
        King k = cb.getKing(whitesTurn);
        if (!k.isInAGrid()) {
            if (k.isWhite()) {
                gui.notifyUserBlackWon();
            } else {
                gui.notifyUserWhiteWon();
            }
            return true;
        }
        ArrayList<Move> moves = k.getValidMoves();
        GridObject[] piecesLeft = cb.allObjects();
        ArrayList<ChessPiece> whiteLeft = new ArrayList<ChessPiece>();
        ArrayList<ChessPiece> blackLeft = new ArrayList<ChessPiece>();
        for (GridObject go : piecesLeft) {
            ChessPiece cp = (ChessPiece) go;
            if (cp instanceof King)
                continue;
            if (cp.isWhite())
                whiteLeft.add(cp);
            else
                blackLeft.add(cp);
        }
        if (moves.size() == 0 && k.isInCheck()) { // Win
            for (GridObject go : piecesLeft) {
                if (((ChessPiece) go).isWhite() != whitesTurn)
                    continue;
                if (((ChessPiece) go).getValidMoves().size() != 0)
                    return false;
            }
            if (whitesTurn)
                gui.notifyUserBlackWon();
            else
                gui.notifyUserWhiteWon();
            return true;
        } else if ((whiteLeft.size() == 1 && (whiteLeft.get(0) instanceof Knight || whiteLeft.get(0) instanceof Bishop)
                || whiteLeft.size() == 0)
                && (blackLeft.size() == 1 && (blackLeft.get(0) instanceof Knight || blackLeft.get(0) instanceof Bishop)
                        || blackLeft.size() == 0)
                || whiteLeft.size() == 0 && blackLeft.size() == 0) { // Draw, Insuffiecent material
            gui.notifyUserDraw();
            return true;
        }
        return false;
    }

    /*
     * (non-Javadoc)
     * 
     * @see edu.kzoo.grid.gui.GridAppController#init()
     */
    @Override
    public void init() {
        Debug.println("=== Beginning of Game ===");
        gui.setGrid(new ChessieBoard());
        white = new SmarterPlayer(true);
        black = new Player(false);
        whitesTurn = true;
    }

}
