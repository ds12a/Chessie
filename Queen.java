import java.util.ArrayList;

/**
 * Queen
 * Programs the Queen
 *
 * @author David Shen
 * @version 5/26/21
 **/

public class Queen extends ChessPiece {
    public Queen(boolean isWhite) {
        super((isWhite) ? WHITE_IMG_FILE : BLACK_IMG_FILE, "Queen", QUEEN_VALUE, isWhite);
    }

    public ArrayList<Move> getPossibleMoves() {
        ArrayList<Move> moves = board().getHorizontalAndVerticalMoves(location());
        moves.addAll(board().getDiagonalMoves(location()));
        return moves;
    }

    private static final String WHITE_IMG_FILE = "./PieceImgs/white_queen.png";
    private static final String BLACK_IMG_FILE = "./PieceImgs/black_queen.png";
    private static final int QUEEN_VALUE = 9;
}
