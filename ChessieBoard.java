import java.util.ArrayList;

import edu.kzoo.grid.BoundedGrid;
import edu.kzoo.grid.GridObject;
import edu.kzoo.grid.Location;

/**
 * ChessieBoard
 * Programs the ChessieBoard
 *
 * @author David Shen
 * @version 5/26/21
 **/

public class ChessieBoard extends BoundedGrid {
    public ChessieBoard() {
        super(true, BOARD_SIZE, BOARD_SIZE);
        // infoBoard = new ChessieSquare[BOARD_SIZE][BOARD_SIZE];
        whiteKing = new King(true);
        blackKing = new King(false);
        reset();
    }

    public ChessieBoard(ChessieBoard original) {
        super(true, BOARD_SIZE, BOARD_SIZE);
        for (GridObject go : original.allObjects()) {
            ChessPiece cp = ((ChessPiece) go).duplicate();
            add(cp, go.location());
            if (cp instanceof King) {
                if (cp.isWhite())
                    whiteKing = (King) cp;
                else
                    blackKing = (King) cp;
            }
        }
    }

    public void doMove(Move m) {
        if (m.getFrom().equals(m.getTo()))
            return;
        pieceAt(m.getFrom()).updateLastStepNum(moveNum);
        if (m.getType().equals(Move.Type.REMOVE) || !isEmpty(m.getTo())) {
            remove(m.getTo());
        }
        if (m.getType().equals(Move.Type.PROMOTION)) {
            remove(m.getFrom());
            add(m.getPromotionPiece().duplicate(), m.getTo());
            return;
        }
        pieceAt(m.getFrom()).moveTo(m.getTo());
        if (m.hasAssociated())
            doMove(m.getAssociatedMove());
    }

    public King getKing(boolean isWhite) {
        King k = isWhite ? whiteKing : blackKing;
        return k;
    }

    public void notifyMoveComplete() {
        moveNum++;
    }

    public int getMoveNum() {
        return moveNum;
    }

    public void reset() {
        removeAll();
        moveNum = 1;
        // Black pieces
        add(new Rook(false), new Location(0, 0));
        add(new Knight(false), new Location(0, 1));
        add(new Bishop(false), new Location(0, 2));
        add(new Queen(false), new Location(0, 3));
        add(blackKing, new Location(0, 4));
        add(new Bishop(false), new Location(0, 5));
        add(new Knight(false), new Location(0, 6));
        add(new Rook(false), new Location(0, 7));
        add(new Pawn(false), new Location(1, 0));
        add(new Pawn(false), new Location(1, 1));
        add(new Pawn(false), new Location(1, 2));
        add(new Pawn(false), new Location(1, 3));
        add(new Pawn(false), new Location(1, 4));
        add(new Pawn(false), new Location(1, 5));
        add(new Pawn(false), new Location(1, 6));
        add(new Pawn(false), new Location(1, 7));

        // White pieces
        add(new Rook(true), new Location(7, 0));
        add(new Knight(true), new Location(7, 1));
        add(new Bishop(true), new Location(7, 2));
        add(new Queen(true), new Location(7, 3));
        add(whiteKing, new Location(7, 4));
        add(new Bishop(true), new Location(7, 5));
        add(new Knight(true), new Location(7, 6));
        add(new Rook(true), new Location(7, 7));
        add(new Pawn(true), new Location(6, 0));
        add(new Pawn(true), new Location(6, 1));
        add(new Pawn(true), new Location(6, 2));
        add(new Pawn(true), new Location(6, 3));
        add(new Pawn(true), new Location(6, 4));
        add(new Pawn(true), new Location(6, 5));
        add(new Pawn(true), new Location(6, 6));
        add(new Pawn(true), new Location(6, 7));

    }

    public ChessPiece pieceAt(Location loc) {
        return (ChessPiece) objectAt(loc);
    }

    public ArrayList<Move> getDiagonalMoves(Location loc) {
        ArrayList<Move> locs = new ArrayList<Move>();
        Location l = loc;
        // locs.add(new Move(loc,l));
        // Get diagonal moves
        while (isValid(l)) {
            l = new Location(l.row() + 1, l.col() + 1);
            locs.add(new Move(loc, l));
            if (!isEmpty(l))
                break;
        }
        l = loc;
        while (isValid(l)) {
            l = new Location(l.row() - 1, l.col() + 1);
            locs.add(new Move(loc, l));
            if (!isEmpty(l))
                break;
        }
        l = loc;
        while (isValid(l)) {
            l = new Location(l.row() + 1, l.col() - 1);
            locs.add(new Move(loc, l));
            if (!isEmpty(l))
                break;
        }
        l = loc;
        while (isValid(l)) {
            l = new Location(l.row() - 1, l.col() - 1);
            locs.add(new Move(loc, l));
            if (!isEmpty(l))
                break;
        }
        removeClashingMoves(locs);
        return locs;
    }

    public ArrayList<Move> getHorizontalAndVerticalMoves(Location loc) {
        ArrayList<Move> locs = new ArrayList<Move>();
        Location l = loc;
        // locs.add(new Move(loc,l));
        // Get Vertical and Horizontal moves
        while (isValid(l)) {
            l = new Location(l.row() + 1, l.col());
            locs.add(new Move(loc, l));
            if (!isEmpty(l))
                break;
        }
        l = loc;
        while (isValid(l)) {
            l = new Location(l.row() - 1, l.col());
            locs.add(new Move(loc, l));
            if (!isEmpty(l))
                break;
        }
        l = loc;
        while (isValid(l)) {
            l = new Location(l.row(), l.col() - 1);
            locs.add(new Move(loc, l));
            if (!isEmpty(l))
                break;
        }
        l = loc;
        while (isValid(l)) {
            l = new Location(l.row(), l.col() + 1);
            locs.add(new Move(loc, l));
            if (!isEmpty(l))
                break;
        }
        removeClashingMoves(locs);
        return locs;
    }

    public ArrayList<Move> getKnightMoves(Location l) {
        ArrayList<Move> locs = new ArrayList<Move>();
        // locs.add(new Move(l,l));
        locs.add(new Move(l, new Location(l.row() + 2, l.col() - 1)));
        locs.add(new Move(l, new Location(l.row() + 2, l.col() + 1)));
        locs.add(new Move(l, new Location(l.row() - 2, l.col() + 1)));
        locs.add(new Move(l, new Location(l.row() - 2, l.col() - 1)));
        locs.add(new Move(l, new Location(l.row() + 1, l.col() + 2)));
        locs.add(new Move(l, new Location(l.row() + 1, l.col() - 2)));
        locs.add(new Move(l, new Location(l.row() - 1, l.col() + 2)));
        locs.add(new Move(l, new Location(l.row() - 1, l.col() - 2)));
        removeClashingMoves(locs);
        return locs;
    }

    public ArrayList<Move> removeClashingMoves(ArrayList<Move> m) {
        for (int i = 0; i < m.size(); i++) {
            if (!isValid(m.get(i).getTo()) || (!isEmpty(m.get(i).getTo())
                    && (pieceAt(m.get(i).getTo())).isWhite() == (pieceAt(m.get(i).getFrom())).isWhite()
                    && !m.get(i).getFrom().equals(m.get(i).getTo()))) {
                m.remove(i);
                i--;
            }
            // else if(!isEmpty(m.get(i).getTo())){
            // m.set(i, new Move(m.get(i).getTo(), m.get(i).getTo(), m.get(i),
            // Move.Type.REMOVE));
            // }
        }
        return m;
    }

    public int getAttackScore(Location l) {
        int score = 0;
        ChessieBoard g = new ChessieBoard(this);
        if (g.isEmpty(l))
            g.add(new Pawn(true), l);
        for (Move move : g.getDiagonalMoves(l)) {
            Location to = move.getTo();
            if (!g.isEmpty(to) && (g.objectAt(to) instanceof Queen || g.objectAt(to) instanceof Bishop)) {
                score += pieceAt(to).isWhite() ? 1 : -1;
                break;
            }
        }
        for (Move move : g.getHorizontalAndVerticalMoves(l)) {
            Location to = move.getTo();
            if (!g.isEmpty(to) && (g.objectAt(to) instanceof Queen || g.objectAt(to) instanceof Rook)) {
                score += pieceAt(to).isWhite() ? 1 : -1;
                break;
            }
        }
        for (Move move : g.getKnightMoves(l)) {
            Location to = move.getTo();
            if (!g.isEmpty(to) && (g.objectAt(to) instanceof Knight)) {
                score += pieceAt(to).isWhite() ? 1 : -1;
                break;
            }
        }
        for (Location loc : (g.neighborsOf(l))) {
            if (!g.isEmpty(loc) && (g.objectAt(loc) instanceof King)) {
                score += pieceAt(loc).isWhite() ? 1 : -1;
                break;
            }
        }
        Location p1 = new Location(l.row() + 1, l.col() + 1);
        Location p2 = new Location(l.row() - 1, l.col() - 1);
        Location p3 = new Location(l.row() + 1, l.col() - 1);
        Location p4 = new Location(l.row() - 1, l.col() + 1);
        if (g.objectAt(p1) instanceof Pawn)
            score += pieceAt(p1).isWhite() ? 1 : -1;
        if (g.objectAt(p2) instanceof Pawn)
            score += pieceAt(p2).isWhite() ? 1 : -1;
        if (g.objectAt(p3) instanceof Pawn)
            score += pieceAt(p3).isWhite() ? 1 : -1;
        if (g.objectAt(p4) instanceof Pawn)
            score += pieceAt(p4).isWhite() ? 1 : -1;

        g = new ChessieBoard(this);
        if (g.isEmpty(l))
            g.add(new Pawn(false), l);
        for (Move move : g.getDiagonalMoves(l)) {
            Location to = move.getTo();
            if (!g.isEmpty(to) && (g.objectAt(to) instanceof Queen || g.objectAt(to) instanceof Bishop)) {
                score += pieceAt(to).isWhite() ? 1 : -1;
                break;
            }
        }
        for (Move move : g.getHorizontalAndVerticalMoves(l)) {
            Location to = move.getTo();
            if (!g.isEmpty(to) && (g.objectAt(to) instanceof Queen || g.objectAt(to) instanceof Rook)) {
                score += pieceAt(to).isWhite() ? 1 : -1;
                break;
            }
        }
        for (Move move : g.getKnightMoves(l)) {
            Location to = move.getTo();
            if (!g.isEmpty(to) && (g.objectAt(to) instanceof Knight)) {
                score += pieceAt(to).isWhite() ? 1 : -1;
                break;
            }
        }
        for (Location loc : (g.neighborsOf(l))) {
            if (!g.isEmpty(loc) && (g.objectAt(loc) instanceof King)) {
                score += pieceAt(loc).isWhite() ? 1 : -1;
                break;
            }
        }
        p1 = new Location(l.row() + 1, l.col() + 1);
        p2 = new Location(l.row() - 1, l.col() - 1);
        p3 = new Location(l.row() + 1, l.col() - 1);
        p4 = new Location(l.row() - 1, l.col() + 1);
        if (g.objectAt(p1) instanceof Pawn)
            score += pieceAt(p1).isWhite() ? 1 : -1;
        if (g.objectAt(p2) instanceof Pawn)
            score += pieceAt(p2).isWhite() ? 1 : -1;
        if (g.objectAt(p3) instanceof Pawn)
            score += pieceAt(p3).isWhite() ? 1 : -1;
        if (g.objectAt(p4) instanceof Pawn)
            score += pieceAt(p4).isWhite() ? 1 : -1;
        return score;
    }

    // private ChessieSquare[][] infoBoard;
    private int moveNum;
    private King whiteKing;
    private King blackKing;
    private static final int BOARD_SIZE = 8;
}
