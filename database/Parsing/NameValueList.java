package Parsing;

import DatabaseException.*;
import Tokenizing.Tokenizer;

import java.util.Vector;

public class NameValueList extends Parser {
    private Vector<String[]> pairList;
    public NameValueList(Tokenizer ref) {
        super(ref);
        pairList = new Vector<>();
    }

    @Override
    public void parse() throws DBException {
        NameValuePair pair = new NameValuePair(ref);
        pair.parse();
        pairList.add(pair.getPair());
        if (isTokenType(ref.getTokenAt(ref.counter + 1),commaRegex)) {
            ref.counter += 2;
            this.parse();
        }
    }

    public Vector<String[]> getPairList() {
        return pairList;
    }
}
