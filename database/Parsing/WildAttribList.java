package Parsing;

import DatabaseException.*;
import Tokenizing.Tokenizer;

import java.util.Vector;

public class WildAttribList extends Parser {
    private Vector<String> wildAttributes;
    public WildAttribList(Tokenizer ref) {
        super(ref);
    }

    @Override
    public void parse() throws DBException {
        if (isTokenType(ref.getTokenAt(ref.counter),starRegex)) {
            wildAttributes = new Vector<>();
            wildAttributes.add(ref.getTokenAt(ref.counter));
            return;
        }
        AttributeList list = new AttributeList(ref);
        list.parse();
        wildAttributes = list.getColumnNames();
    }

    public Vector<String> getWildAttribList() {
        return wildAttributes;
    }

    private String starRegex = "^\\*$";
}
