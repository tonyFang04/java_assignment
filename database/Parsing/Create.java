package Parsing;

import DatabaseException.*;
import Tokenizing.Tokenizer;

import java.io.IOException;
import java.util.HashMap;

public class Create extends Command {
    public Create(Tokenizer ref) {
        super(ref);
    }

    public Create(Tokenizer ref, Directory databaseDirectory) {
        super(ref,databaseDirectory);
    }

    @Override
    public void parse() throws DBException, IOException {
        HashMap<String, Create> map = new HashMap<>();
        map.put("DATABASE",new CreateDatabase(ref));
        map.put("TABLE",new CreateTable(ref,databaseDirectory));
        if (!map.containsKey(ref.getTokenAt(ref.counter))) {
            throw new BNFSyntaxException("");
        }
        Create createCMD = map.get(ref.getTokenAt(ref.counter));
        ref.counter++;
        createCMD.parse();
    }
}
