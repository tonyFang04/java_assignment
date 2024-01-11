package Parsing;

import DatabaseException.*;
import Tokenizing.Tokenizer;

import java.util.HashMap;

public class Drop extends Command {
    public Drop(Tokenizer ref) {
        super(ref);
    }

    public Drop(Tokenizer ref, Directory databaseDirectory) {
        super(ref, databaseDirectory);
    }

    @Override
    public void parse() throws DBException {
        HashMap<String, Drop> map = new HashMap<>();
        map.put("DATABASE",new DropDatabase(ref));
        map.put("TABLE",new DropTable(ref,databaseDirectory));
        if (!map.containsKey(ref.getTokenAt(ref.counter))) {
            throw new BNFSyntaxException("");
        }
        Drop dropCMD = map.get(ref.getTokenAt(ref.counter));
        ref.counter++;
        dropCMD.parse();
    }
}
