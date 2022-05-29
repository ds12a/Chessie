import java.util.ArrayList;

/**
 * Bishop
 * Programs the Bishop
 *
 * @author David Shen
 * @version 5/26/21
 **/

public class Bishop extends ChessPiece {
    public Bishop(boolean isWhite) {
        super((isWhite) ? WHITE_IMG_FILE : BLACK_IMG_FILE, "Bishop", BISHOP_VALUE, isWhite);
    }

    public ArrayList<Move> getPossibleMoves() {
        return board().removeClashingMoves(board().getDiagonalMoves(location()));
    }

    private static final String WHITE_IMG_FILE = "./PieceImgs/white_bishop.png";
    private static final String BLACK_IMG_FILE = "./PieceImgs/black_bishop.png";
    private static final int BISHOP_VALUE = 3;
}
