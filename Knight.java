import java.util.ArrayList;

/**
 * Knight
 * Programs the Knight
 *
 * @author David Shen
 * @version 5/26/21
 **/

public class Knight extends ChessPiece {
    public Knight(boolean isWhite) {
        super((isWhite) ? WHITE_IMG_FILE : BLACK_IMG_FILE, "Knight", KNIGHT_VALUE, isWhite);
    }

    public ArrayList<Move> getPossibleMoves() {
        return board().getKnightMoves(location());
    }

    private static final String WHITE_IMG_FILE = "./PieceImgs/white_knight.png";
    private static final String BLACK_IMG_FILE = "./PieceImgs/black_knight.png";
    private static final int KNIGHT_VALUE = 3;
}
