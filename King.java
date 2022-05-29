import java.util.ArrayList;

import edu.kzoo.grid.Grid;
import edu.kzoo.grid.Location;

/**
 * King
 * Programs the King
 *
 * @author David Shen
 * @version 5/26/21
 **/

public class King extends ChessPiece {
    public King(boolean isWhite) {
        super((isWhite) ? WHITE_IMG_FILE : BLACK_IMG_FILE, "King", KING_VALUE, isWhite);
    }

    public ArrayList<Move> getPossibleMoves() {
        ArrayList<Move> locs = new ArrayList<Move>();
        Location l = location();
        Grid g = grid();
        for (Location x : g.neighborsOf(l)) {
            if (g.isValid(x))
                locs.add(makeMove(x));
        }
        if (canCastleLong()) {
            locs.add(new Move(l, new Location(l.row(), 2),
                    new Move(new Location(l.row(), 0), new Location(l.row(), 3))));
        }
        if (canCastleShort()) {
            locs.add(new Move(l, new Location(l.row(), 6),
                    new Move(new Location(l.row(), 7), new Location(l.row(), 5))));
        }
        // locs.add(makeMove(l));
        return locs;
    }

    public boolean putsInCheck(Move m) {
        ChessieBoard g = new ChessieBoard(board());
        g.doMove(m);

        return g.getKing(isWhite()).isInCheck();
    }

    public boolean isInCheck() {
        ChessieBoard g = board();
        Location l = location();

        for (Move move : g.getDiagonalMoves(l)) {
            Location to = move.getTo();
            if (!g.isEmpty(to) && g.pieceAt(to).isWhite() != isWhite()
                    && (g.objectAt(to) instanceof Queen || g.objectAt(to) instanceof Bishop)) {
                return true;
            }
        }
        for (Move move : g.getHorizontalAndVerticalMoves(l)) {
            Location to = move.getTo();
            if (!g.isEmpty(to) && g.pieceAt(to).isWhite() != isWhite()
                    && (g.objectAt(to) instanceof Queen || g.objectAt(to) instanceof Rook)) {
                return true;
            }
        }
        for (Move move : g.getKnightMoves(l)) {
            Location to = move.getTo();
            if (!g.isEmpty(to) && g.pieceAt(to).isWhite() != isWhite() && (g.objectAt(to) instanceof Knight)) {
                return true;
            }
        }
        for (Location loc : (g.neighborsOf(l))) {
            if (!g.isEmpty(loc) && g.pieceAt(loc).isWhite() != isWhite() && (g.objectAt(loc) instanceof King)) {
                return true;
            }
        }
        Location p1 = new Location(l.row() + (isWhite() ? -1 : 1), l.col() + 1);
        Location p2 = new Location(l.row() + (isWhite() ? -1 : 1), l.col() - 1);
        if (g.objectAt(p1) instanceof Pawn && g.pieceAt(p1).isWhite() != isWhite()
                || g.objectAt(p2) instanceof Pawn && g.pieceAt(p2).isWhite() != isWhite()) {
            return true;
        }
        return false;
    }

    private boolean canCastleLong() {
        Location rook = new Location(location().row(), 0);
        Rook r;
        ChessieBoard g = board();
        if (getLastStepNum() != 0)
            return false;
        if (g.objectAt(rook) instanceof Rook) {
            r = (Rook) g.pieceAt(rook);
            if (r.getLastStepNum() != 0)
                return false;
        } else
            return false;
        for (int i = 1; i < 4; i++) {
            Location l = new Location(location().row(), i);
            if (!g.isEmpty(l) || putsInCheck(new Move(location(), l)))
                return false;
        }
        return true;
    }

    private boolean canCastleShort() {
        Location rook = new Location(location().row(), 7);
        Rook r;
        Grid g = grid();
        if (getLastStepNum() != 0)
            return false;
        if (g.objectAt(rook) instanceof Rook) {
            r = (Rook) g.objectAt(rook);
            if (r.getLastStepNum() != 0)
                return false;
        } else
            return false;
        for (int i = 5; i < 7; i++) {
            Location l = new Location(location().row(), i);
            if (!g.isEmpty(l) || putsInCheck(new Move(location(), l)))
                return false;
        }
        return true;
    }

    private static final String WHITE_IMG_FILE = "./PieceImgs/white_king.png";
    private static final String BLACK_IMG_FILE = "./PieceImgs/black_king.png";
    private static final int KING_VALUE = Integer.MAX_VALUE;
}
