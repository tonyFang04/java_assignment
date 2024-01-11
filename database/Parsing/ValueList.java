package Parsing;

import DatabaseException.*;
import Tokenizing.Tokenizer;

import java.util.Vector;

public class ValueList extends Parser {
    private Vector<String> valueList;
    public ValueList(Tokenizer ref) {
        super(ref);
        valueList = new Vector<>();
    }

    @Override
    public void parse() throws DBException {
        Value currentValue = new Value(ref);
        currentValue.parse();
        valueList.add(currentValue.getValue());
        if (isTokenType(ref.getTokenAt(ref.counter + 1),commaRegex)) {
            ref.counter += 2;
            this.parse();
        }
    }

    public Vector<String> getValueList() {
        return valueList;
    }
}
