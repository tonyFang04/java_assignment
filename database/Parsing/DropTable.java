package Parsing;

import DatabaseException.*;
import Tokenizing.Tokenizer;

import java.io.File;

public class DropTable extends Drop {
    public DropTable(Tokenizer ref, Directory databaseDirectory) {
        super(ref,databaseDirectory);
    }

    @Override
    public void parse() throws DBException {
        checkTokenType(ref.getTokenAt(ref.counter), alphaNumericRegex);
        interp();
    }

    void interp() throws InterpretException {
        String tableName = databaseDirectory.getAddress() + File.separator + ref.getTokenAt(ref.counter) + fileSuffix;
        File f = new File(tableName);
        if (f.exists()) {
            f.delete();
        }
    }
}
