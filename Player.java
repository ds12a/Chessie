import java.util.ArrayList;

import edu.kzoo.grid.GridObject;
import edu.kzoo.util.Debug;

/**
 * Player
 * Programs the Player. The default player is random.
 * 
 * @author David Shen
 * @version 5/30/21
 **/

public class Player {
    public Player(boolean white) {
        whitePlayer = white;
    }

    public void takeTurn(ChessieBoard cb) {
        for (GridObject go : cb.allObjects()) {
            if (!cb.getKing(!whitePlayer).isInAGrid())
                return;
            ChessPiece cp = (ChessPiece) go;
            if (whitePlayer != cp.isWhite() || cp.getLastStepNum() == cb.getMoveNum())
                continue;
            ArrayList<Move> moves = cp.getValidMoves();
            Debug.println(cp.toString() + " :" + moves.toString());
            if (moves.size() == 0)
                continue;
            cb.doMove(chooseMove(cp.getValidMoves(), cb));
        }
    }

    protected Move chooseMove(ArrayList<Move> moves, ChessieBoard cb) {
        return moves.get((int) (Math.random() * moves.size()));
    }

    protected boolean whitePlayer;
}
