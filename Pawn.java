import java.util.ArrayList;

import edu.kzoo.grid.Location;

/**
 * Pawn
 * Programs the Pawn
 *
 * @author David Shen
 * @version 5/26/21
 **/

public class Pawn extends ChessPiece {
    public Pawn(boolean isWhite) {
        super((isWhite) ? WHITE_IMG_FILE : BLACK_IMG_FILE, "Pawn", PAWN_VALUE, isWhite);
        moveCount = 0;
    }

    public ArrayList<Move> getPossibleMoves() {
        ArrayList<Move> locs = new ArrayList<Move>();
        Location l = location();
        ChessieBoard g = board();
        Location x;
        // locs.add(makeMove(l));

        x = new Location(l.row() + ((isWhite()) ? -1 : 1), l.col());
        if (g.isValid(x) && g.isEmpty(x)) {
            if (x.row() == (isWhite() ? 0 : 7)) {
                locs.addAll(getPromotionMoves(x));
            } else
                locs.add(makeMove(x));
        }
        if (x.row() == (isWhite() ? 3 : 4) && !g.isEmpty(new Location(l.row(), x.col()))
                && g.objectAt(new Location(l.row(), x.col())) instanceof Pawn
                && ((Pawn) g.objectAt(new Location(l.row(), x.col()))).getMoveCount() == 1
                && (((Pawn) g.objectAt(new Location(l.row(), x.col()))).getLastStepNum() == g.getMoveNum() && !isWhite()
                        || ((Pawn) g.objectAt(new Location(l.row(), x.col()))).getLastStepNum() + 1 == g.getMoveNum()
                                && isWhite())) {
            locs.add(new Move(l, x, new Move(x, x, Move.Type.REMOVE)));
        }

        x = new Location(l.row() + ((isWhite()) ? -2 : 2), l.col());
        if (getLastStepNum() == 0 && g.isValid(x) && g.isEmpty(x))
            locs.add(makeMove(x));

        x = new Location(l.row() + ((isWhite()) ? -1 : 1), l.col() + 1);
        if (g.isValid(x) && !g.isEmpty(x)) {
            if (x.row() == (isWhite() ? 0 : 7)) {
                locs.addAll(getPromotionMoves(x));
            } else
                locs.add(makeMove(x));
        }
        x = new Location(l.row() + ((isWhite()) ? -1 : 1), l.col() - 1);
        if (g.isValid(x) && !g.isEmpty(x)) {
            if (x.row() == (isWhite() ? 0 : 7)) {
                locs.addAll(getPromotionMoves(x));
            } else
                locs.add(makeMove(x));
        }
        if (x.row() == (isWhite() ? 3 : 4) && !g.isEmpty(new Location(l.row(), x.col()))
                && g.objectAt(new Location(l.row(), x.col())) instanceof Pawn
                && ((Pawn) g.objectAt(new Location(l.row(), x.col()))).getMoveCount() == 1
                && (((Pawn) g.objectAt(new Location(l.row(), x.col()))).getLastStepNum() == g.getMoveNum() && !isWhite()
                        || ((Pawn) g.objectAt(new Location(l.row(), x.col()))).getLastStepNum() + 1 == g.getMoveNum()
                                && isWhite())) {
            locs.add(new Move(l, x, new Move(x, x, Move.Type.REMOVE)));
        }
        return locs;
    }

    private ArrayList<Move> getPromotionMoves(Location l) {
        ArrayList<Move> x = new ArrayList<Move>();
        x.add(new Move(location(), l, makeMove(l), Move.Type.PROMOTION, new Queen(isWhite())));
        x.add(new Move(location(), l, makeMove(l), Move.Type.PROMOTION, new Rook(isWhite())));
        x.add(new Move(location(), l, makeMove(l), Move.Type.PROMOTION, new Knight(isWhite())));
        x.add(new Move(location(), l, makeMove(l), Move.Type.PROMOTION, new Bishop(isWhite())));
        return x;
    }

    public int getMoveCount() {
        return moveCount;
    }

    @Override
    public void moveTo(Location l) {
        super.moveTo(l);
        moveCount++;
    }

    private int moveCount;
    private static final String WHITE_IMG_FILE = "./PieceImgs/white_pawn.png";
    private static final String BLACK_IMG_FILE = "./PieceImgs/black_pawn.png";
    private static final int PAWN_VALUE = 1;
}
