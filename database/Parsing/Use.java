package Parsing;

import DatabaseException.*;
import Tokenizing.Tokenizer;

import java.io.File;

public class Use extends Command {
    public Use(Tokenizer ref, Directory databaseDirectory) {
        super(ref,databaseDirectory);
    }

    @Override
    public void parse() throws DBException {
        checkTokenType(ref.getTokenAt(ref.counter), alphaNumericRegex);
        interp();
    }

    void interp() throws InterpretException {
        File f = new File(ref.getTokenAt(ref.counter));
        if (f.exists()) {
            databaseDirectory.setAddress(ref.getTokenAt(ref.counter));
        } else {
            throw new InterpretException("Database does not exist.");
        }
    }
}
