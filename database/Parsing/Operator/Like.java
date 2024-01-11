package Parsing.Operator;

import DatabaseException.InterpretException;
import Tokenizing.Tokenizer;
import Tools.Column;

import java.util.Vector;

public class Like extends Op {
    Tokenizer ref;
    public Like(Tokenizer ref) {
        this.ref = ref;
    }

    @Override
    public void parse() throws InterpretException {
        if (!ref.getTokenAt(ref.counter + 1).equals("'")) {
            System.out.println(ref.getTokenAt(ref.counter + 1));
            throw new InterpretException("string expected.");
        }
    }

    @Override
    public Vector<Integer> getRow() throws InterpretException {
        Vector<Integer> output = new Vector<>();
        Vector<Column> columns = table.getColumns();
        for (Column column : columns) {
            if (columnName.equals(column.getName())) {
                for (int i = 0; i < column.size(); i++) {
                    if (column.getValue(i).contains(value.getValue())) {
                        output.add(i);
                    }
                }
            }
        }
        return output;
    }

}
