package Parsing;

import DatabaseException.*;
import Tokenizing.Tokenizer;

import java.io.File;

public class CreateDatabase extends Create {
    public CreateDatabase(Tokenizer ref) {
        super(ref);
    }

    @Override
    public void parse() throws DBException {
        checkTokenType(ref.getTokenAt(ref.counter), alphaNumericRegex);
        interp();
    }

    void interp() throws InterpretException {
        File f = new File(ref.getTokenAt(ref.counter));
        if (f.isDirectory()) {
            throw new InterpretException("Database already exists.");
        }
        f.mkdir();
    }
}
