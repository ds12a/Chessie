import java.util.ArrayList;

/**
 * Rook
 * Programs the Rook
 *
 * @author David Shen
 * @version 5/26/21
 **/

public class Rook extends ChessPiece {
    public Rook(boolean isWhite) {
        super((isWhite) ? WHITE_IMG_FILE : BLACK_IMG_FILE, "Rook", ROOK_VALUE, isWhite);
    }

    public ArrayList<Move> getPossibleMoves() {
        return board().getHorizontalAndVerticalMoves(location());
    }

    private static final String WHITE_IMG_FILE = "./PieceImgs/white_rook.png";
    private static final String BLACK_IMG_FILE = "./PieceImgs/black_rook.png";
    private static final int ROOK_VALUE = 5;
}
