package OXOExceptions;

public class OutsideCellRangeException extends CellDoesNotExistException {
    int position;
    RowOrColumn type;
    public OutsideCellRangeException(int row, int column,int position, RowOrColumn type) {
        super(row,column);
        this.position = position;
        this.type = type;
    }

    @Override
    public String toString() {
        if (this.type == RowOrColumn.ROW) {
            return "OutsideCellRangeException: row '" + (char)(position + 'a')
                    + "' outside of board with height '" + super.getRow() + "'";
        } else {
            return "OutsideCellRangeException: column '" + (char)(position + '1')
                    + "' outside of board with width '" + super.getColumn() + "'";
        }
    }
}
