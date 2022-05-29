import edu.kzoo.grid.Location;

/**
 * Move
 * Moves consist of a from location, to location, and any associated moves
 *
 * @author David Shen
 * @version 5/26/21
 **/

public class Move implements Comparable<Move> {
    public Move(Location f, Location d) {
        this(f, d, null, Type.MOVE);
    }

    public Move(Location f, Location d, Type type) {
        this(f, d, null, type);
    }

    public Move(Location f, Location d, Move associated) {
        this(f, d, associated, Type.MOVE);
    }

    public Move(Location f, Location d, Move associated, Type type) {
        this(f, d, associated, type, null);
    }

    public Move(Location f, Location d, Move associated, Type type, ChessPiece promote) {
        from = f;
        to = d;
        associatedMove = associated;
        t = type;
        promotion = promote;
    }

    public void setType(Type type) {
        t = type;
    }

    public Type getType() {
        return t;
    }

    public Move getAssociatedMove() {
        if (t == null)
            throw new IllegalCallerException("This move does not have an associated move.");
        return associatedMove;
    }

    public ChessPiece getPromotionPiece() {
        return promotion;
    }

    public Location getTo() {
        return to;
    }

    public Location getFrom() {
        return from;
    }

    public String toString() {
        return "Move from " + from.toString() + " to " + to.toString() + " of type " + t.toString();
    }

    public boolean hasAssociated() {
        return associatedMove != null;
    }

    public void incrementSmartScore(double n) {
        smartScore += n;
    }

    public double getSmartScore() {
        return smartScore;
    }

    public int compareTo(Move other) {
        double s = getSmartScore();
        double o = other.getSmartScore();
        if (s == o)
            return 0;
        else if (s < o)
            return -1;
        else
            return 1;
    }

    private Move associatedMove;
    private Location to;
    private Location from;
    private Type t;
    private ChessPiece promotion;
    private double smartScore = 0;

    public static enum Type {
        MOVE, REMOVE, PROMOTION
    }
}
