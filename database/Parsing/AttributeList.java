package Parsing;

import DatabaseException.*;
import Tokenizing.Tokenizer;

import java.util.Vector;

public class AttributeList extends Parser {
    private Vector<String> columnNames;
    public AttributeList(Tokenizer ref) {
        super(ref);
        columnNames = new Vector<>();
    }

    @Override
    public void parse() throws DBException {
        checkTokenType(ref.getTokenAt(ref.counter),alphaNumericRegex);
        columnNames.add(ref.getTokenAt(ref.counter));
        if (isTokenType(ref.getTokenAt(ref.counter + 1),commaRegex)) {
            ref.counter += 2;
            this.parse();
        }
    }

    public Vector<String> getColumnNames() {
        return columnNames;
    }
}
