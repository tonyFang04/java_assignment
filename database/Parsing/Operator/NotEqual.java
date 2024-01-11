package Parsing.Operator;

import Tools.Column;

import java.util.Vector;

public class NotEqual extends Op {
    @Override
    public Vector<Integer> getRow() {
        Vector<Integer> output = new Vector<>();
        Vector<Column> columns = table.getColumns();
        for (Column column : columns) {
            if (columnName.equals(column.getName())) {
                for (int i = 0; i < column.size(); i++) {
                    if (!(value.getValue().equals(column.getValue(i)))) {
                        output.add(i);
                    }
                }
            }
        }
        return output;
    }
}
