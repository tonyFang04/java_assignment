package Parsing;

import DatabaseException.*;
import Tokenizing.Tokenizer;

public class StringLiteral extends Parser {
    private String stringLiteral;
    public StringLiteral(Tokenizer ref) {
        super(ref);
        stringLiteral = "";
    }

    @Override
    public void parse() throws DBException {
        checkTokenType(ref.getTokenAt(ref.counter), alphaNumericRegex);
        stringLiteral += ref.getTokenAt(ref.counter) + " ";
        ref.counter++;
        if (isTokenType(ref.getTokenAt(ref.counter),alphaNumericRegex)) {
            this.parse();
        }
    }

    public String getStringLiteral() {
        stringLiteral = stringLiteral.substring(0,stringLiteral.length() - 1);
        return stringLiteral;
    }
}
