package Parsing;

import DatabaseException.*;
import Tokenizing.Tokenizer;

public class NameValuePair extends Parser {
    String equalRegex = "^=$";
    String[] pair;
    public NameValuePair(Tokenizer ref) {
        super(ref);
        pair = new String[2];
    }

    @Override
    public void parse() throws DBException {
        checkTokenType(ref.getTokenAt(ref.counter),alphaNumericRegex);
        pair[0] = ref.getTokenAt(ref.counter++);
        checkTokenType(ref.getTokenAt(ref.counter++),equalRegex);
        Value value = new Value(ref);
        value.parse();
        pair[1] = value.getValue();
    }

    public String[] getPair() {
        return pair;
    }
}
