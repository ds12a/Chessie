import java.util.ArrayList;

import edu.kzoo.grid.Location;
import edu.kzoo.grid.PictureBlock;

/**
 * ChessPiece
 * Programs ChessPieces
 *
 * @author David Shen
 * @version 5/26/21
 **/

public class ChessPiece extends PictureBlock {
    public ChessPiece(String file, String descriptionString, int val, boolean isWhite) {
        super(file, descriptionString);
        value = val;
        white = isWhite;
        lastStepNum = 0;
    }

    public ChessPiece duplicate() {
        ChessPiece cp;
        if (this instanceof King)
            cp = new King(isWhite());
        else if (this instanceof Bishop)
            cp = new Bishop(isWhite());
        else if (this instanceof Knight)
            cp = new Knight(isWhite());
        else if (this instanceof Rook)
            cp = new Rook(isWhite());
        else if (this instanceof Queen)
            cp = new Queen(isWhite());
        else if (this instanceof Pawn)
            cp = new Pawn(isWhite());
        else
            throw new IllegalStateException("Grid Object is unknown");
        return cp;
    }

    public int getValue() {
        return value;
    }

    public boolean isWhite() {
        return white;
    }

    protected Move makeMove(Location dest) {
        return new Move(location(), dest);
    }

    public ArrayList<Move> getPossibleMoves() {
        return new ArrayList<Move>();
    }

    public int getLastStepNum() {
        return lastStepNum;
    }

    public King getKing() {
        return board().getKing(isWhite());
    }

    public void updateLastStepNum(int num) {
        lastStepNum = num;
    }

    public void moveTo(Location l) {
        changeLocation(l);
    }

    public ChessieBoard board() {
        return (ChessieBoard) grid();
    }

    public ArrayList<Move> getValidMoves() {
        ArrayList<Move> m = getPossibleMoves();
        King k = getKing();
        board().removeClashingMoves(m);
        for (int i = 0; i < m.size(); i++) {
            if (k.putsInCheck(m.get(i))) {
                m.remove(i);
                i--;
            }
        }
        return m;
    }

    private int lastStepNum;
    private int value;
    private boolean white;
}
