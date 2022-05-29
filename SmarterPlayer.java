import java.util.ArrayList;
import java.util.Collections;

import edu.kzoo.grid.Location;

/**
 * SmartPlayer
 * Programs the Player. This player plays with a little logic. It takes free
 * pieces and puts its pieces in safe places (or it should!). Occastionally
 * loses to random player.
 * 
 * @author David Shen
 * @version 5/30/21
 **/

public class SmarterPlayer extends Player {
    public SmarterPlayer(boolean white) {
        super(white);
    }

    @Override
    protected Move chooseMove(ArrayList<Move> moves, ChessieBoard cb) {
        ArrayList<Move> copy = new ArrayList<Move>(moves);
        for (Move m : moves) {
            Location to = m.getTo();
            if (cb.pieceAt(to) instanceof King) {
                // Can capture enemy king!
                return m;
            }
            ChessPiece cp = cb.pieceAt(to);
            ChessPiece from = cb.pieceAt(m.getFrom());
            int attackScore = cb.getAttackScore(to);
            if (!cb.isEmpty(to) && isSafeSquare(to, attackScore, from)) {
                m.incrementSmartScore(cp.getValue() + 1);
            } else if (isSafeSquare(to, attackScore, from))
                m.incrementSmartScore(attackScore);
            else
                m.incrementSmartScore(-from.getValue() - 1);

            if (cb.getKing(!from.isWhite()).putsInCheck(m)) { // check
                m.incrementSmartScore(0.7);
            }
            if (from instanceof King && !cb.neighborsOf(from.location()).contains(m.getTo())) { // Castle
                m.incrementSmartScore(1);
            } else if (!(from instanceof King)) {
                // m.incrementSmartScore(positionScores[from.isWhite()?to.row():(8-to.row()-1)][to.col()]);
            }
        }
        Collections.sort(copy);
        return copy.get(copy.size() - 1);
    }

    protected boolean isSafeSquare(Location l, int attackScore, ChessPiece cp) {
        return (cp.isWhite() && attackScore >= 0 || !cp.isWhite() && attackScore <= 0);
    }

    protected static double[][] positionScores = {
            { 0, 0, 0, 0, 0, 0, 0, 0 },
            { 0, 0, 0, 0, 0, 0, 0, 0 },
            { 0, 0.1, 0.2, 0.2, 0.2, 0.2, 0.1, 0 },
            { 0.1, 0.1, 0.2, 0.5, 0.5, 0.2, 0.1, 0.1 },
            { 0.1, 0.1, 0.3, 0.5, 0.5, 0.3, 0.1, 0.1 },
            { 0.2, 0.3, 0.3, 0.4, 0.4, 0.3, 0.3, 0.2 },
            { 0.3, 0.4, 0.4, 0.4, 0.4, 0.4, 0.4, 0.3 },
            { 0.4, 0.5, 0.5, 0.5, 0.5, 0.5, 0.5, 0.4 },
    };
}
