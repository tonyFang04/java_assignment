package Parsing;

import DatabaseException.*;
import Tokenizing.Tokenizer;

public class Join extends Command {
    String AND_regex = "^AND$";
    String ON_regex = "^ON$";
    public Join(Tokenizer ref) {
        super(ref);
    }

    @Override
    public void parse() throws DBException {
        checkTokenType(ref.getTokenAt(ref.counter++), alphaNumericRegex);
        checkTokenType(ref.getTokenAt(ref.counter++), AND_regex);
        checkTokenType(ref.getTokenAt(ref.counter++), alphaNumericRegex);
        checkTokenType(ref.getTokenAt(ref.counter++), ON_regex);
        checkTokenType(ref.getTokenAt(ref.counter++), alphaNumericRegex);
        checkTokenType(ref.getTokenAt(ref.counter++), AND_regex);
        checkTokenType(ref.getTokenAt(ref.counter), alphaNumericRegex);
    }
}
