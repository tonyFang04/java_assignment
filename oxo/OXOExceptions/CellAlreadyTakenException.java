package OXOExceptions;

public class CellAlreadyTakenException extends OXOMoveException {
    public CellAlreadyTakenException(int row, int column) {
        super(row,column);
    }
    @Override
    public String toString() {
        return "CellAlreadyTakenException: cell '" + (char)(super.getRow() + 'a')
                + (char)(super.getColumn() + '1') + "' has already been taken.";
    }
}
