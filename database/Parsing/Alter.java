package Parsing;

import DatabaseException.*;
import Tokenizing.Tokenizer;

public class Alter extends Command {
    String TABLEregex = "^TABLE$";
    String AlterationType = "^(ADD|DROP)$";
    public Alter(Tokenizer ref) {
        super(ref);
    }

    @Override
    public void parse() throws DBException {
        checkTokenType(ref.getTokenAt(ref.counter++), TABLEregex);
        checkTokenType(ref.getTokenAt(ref.counter++), alphaNumericRegex);
        checkTokenType(ref.getTokenAt(ref.counter++), AlterationType);
        checkTokenType(ref.getTokenAt(ref.counter), alphaNumericRegex);
    }
}
