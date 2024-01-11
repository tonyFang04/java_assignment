package OXOExceptions;

public class CellDoesNotExistException extends OXOMoveException {
    public CellDoesNotExistException() {}

    public CellDoesNotExistException(int row, int column) {
        super(row,column);
    }

    @Override
    public String toString() {
        return "CellDoesNotExistException";
    }

    protected int getRow()
    {
        return super.getRow();
    }

    protected int getColumn()
    {
        return super.getColumn();
    }
}
