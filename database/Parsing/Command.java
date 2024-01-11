package Parsing;

import DatabaseException.*;
import Tokenizing.Tokenizer;

import java.io.IOException;
import java.util.HashMap;

public class Command extends Parser {
    public  Command(Tokenizer ref) {
        super(ref);
    }

    public Command(Tokenizer ref, Directory databaseDirectory) {
        super(ref,databaseDirectory);
    }

    @Override
    public void parse() throws DBException, IOException {
        HashMap<String, Command> map = new HashMap<>();
        map.put("USE",new Use(ref,databaseDirectory));
        map.put("CREATE",new Create(ref,databaseDirectory));
        map.put("DROP",new Drop(ref,databaseDirectory));
        map.put("ALTER",new Alter(ref));
        map.put("INSERT",new Insert(ref,databaseDirectory));
        map.put("SELECT",new Select(ref,databaseDirectory));
        map.put("UPDATE",new Update(ref,databaseDirectory));
        map.put("DELETE",new Delete(ref,databaseDirectory));
        map.put("JOIN",new Join(ref));
        if (!map.containsKey(ref.getTokenAt(ref.counter))) {
            throw new BNFSyntaxException("");
        }
        Command parseCommand = map.get(ref.getTokenAt(ref.counter++));
        parseCommand.parse();
        ref.counter++;
        checkTokenType(ref.getTokenAt(ref.counter++),semiColonRegex);
        output = parseCommand.getMessage();
    }

    public String getMessage() {
        return output;
    }

    private String semiColonRegex = "^;$";
}
