package Parsing;

import DatabaseException.*;
import Tokenizing.Tokenizer;

import java.io.File;

public class DropDatabase extends Drop {
    public DropDatabase(Tokenizer ref) {
        super(ref);
    }

    @Override
    public void parse() throws DBException {
        checkTokenType(ref.getTokenAt(ref.counter), alphaNumericRegex);
        interp();
    }

    void interp() {
        File f = new File(ref.getTokenAt(ref.counter));
        if (f.exists()) {
            deleteDirectory(f);
        }
    }

    //https://www.baeldung.com/java-delete-directory
    boolean deleteDirectory(File directoryToBeDeleted) {
        File[] allContents = directoryToBeDeleted.listFiles();
        if (allContents != null) {
            for (File file : allContents) {
                deleteDirectory(file);
            }
        }
        return directoryToBeDeleted.delete();
    }
}
