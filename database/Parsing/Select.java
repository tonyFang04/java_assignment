package Parsing;

import DatabaseException.*;
import Tokenizing.Tokenizer;
import Tools.Table;

import java.io.File;
import java.io.IOException;
import java.util.Vector;

public class Select extends Command {
    private Condition condition;
    private String tableName;
    private Table table;
    WildAttribList list;
    public Select(Tokenizer ref, Directory databaseDirectory) {
        super(ref,databaseDirectory);
        condition = null;
    }

    @Override
    public void parse() throws DBException, IOException {
        list = new WildAttribList(ref);
        list.parse();
        ref.counter++;
        checkTokenType(ref.getTokenAt(ref.counter++), FROMregex);
        checkTokenType(ref.getTokenAt(ref.counter), alphaNumericRegex);
        tableName = databaseDirectory.getAddress() + File.separator + ref.getTokenAt(ref.counter) + fileSuffix;
        table = new Table(tableName);
        if (isTokenType(ref.getTokenAt(ref.counter + 1),WHEREregex)) {
            ref.counter+=2;
            condition = new Condition(ref,table);
            condition.parse();
        }
        interp();
    }

    void interp() throws InterpretException {
        if (list.getWildAttribList().get(0).equals("*") && condition == null) {
            output += table.printAll();
            return;
        }

        if (list.getWildAttribList().get(0).equals("*") && condition != null) {
            output += table.printAllColumns(condition.getRow());
            return;
        }

        if (condition == null) {
            Vector<Integer> columnsToPrint = table.queryColumns(list.getWildAttribList());
            output += table.printAllRows(columnsToPrint);
            return;
        }

        if (condition != null) {
            Vector<Integer> columnsToPrint = table.queryColumns(list.getWildAttribList());
            output += table.printRowsAndColumns(condition.getRow(),columnsToPrint);
            return;
        }
    }
}
