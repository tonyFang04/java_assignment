package Parsing;

import DatabaseException.*;
import Tokenizing.Tokenizer;

public class Value extends Parser {
    private String valStr;
    public Value(Tokenizer ref) {
        super(ref);
    }

    @Override
    public void parse() throws DBException {
        if (isTokenType(ref.getTokenAt(ref.counter),floatRegex)) {
            valStr = ref.getTokenAt(ref.counter);
            return;
        }
        if (isTokenType(ref.getTokenAt(ref.counter),intRegex)) {
            valStr = ref.getTokenAt(ref.counter);
            return;
        }
        if (isTokenType(ref.getTokenAt(ref.counter),boolRegex)) {
            valStr = ref.getTokenAt(ref.counter);
            return;
        }
        if (isTokenType(ref.getTokenAt(ref.counter++),singleQuoteRegex)) {
            StringLiteral name = new StringLiteral(ref);
            name.parse();
            valStr = name.getStringLiteral();
            checkTokenType(ref.getTokenAt(ref.counter),singleQuoteRegex);
            return;
        }
        throw new BNFSyntaxException("");
    }

    public String getValue() {
        return valStr;
    }

    private String singleQuoteRegex = "^'$";
}
