package Parsing.Operator;

import DatabaseException.InterpretException;
import Tools.Column;

import java.util.Vector;

public class Smaller extends Op {
    @Override
    public Vector<Integer> getRow() throws InterpretException {
        Vector<Integer> output = new Vector<>();
        Vector<Column> columns = table.getColumns();
        canConvertToDouble(value.getValue());
        for (Column column : columns) {
            if (columnName.equals(column.getName())) {
                for (int i = 0; i < column.size(); i++) {
                    canConvertToDouble(column.getValue(i));
                    if (Double.parseDouble(column.getValue(i)) < Double.parseDouble(value.getValue())) {
                        output.add(i);
                    }
                }
            }
        }
        return output;
    }
}
