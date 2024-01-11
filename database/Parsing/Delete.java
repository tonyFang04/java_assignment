package Parsing;

import DatabaseException.*;
import Tokenizing.Tokenizer;
import Tools.Table;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Vector;

public class Delete extends Command {
    private Table table;
    private String tableName;
    private Condition condition;
    public Delete(Tokenizer ref, Directory databaseDirectory) {
        super(ref,databaseDirectory);
    }

    @Override
    public void parse() throws DBException, IOException {
        checkTokenType(ref.getTokenAt(ref.counter++),FROMregex);
        checkTokenType(ref.getTokenAt(ref.counter), alphaNumericRegex);
        tableName = databaseDirectory.getAddress() + File.separator + ref.getTokenAt(ref.counter++) + fileSuffix;
        table = new Table(tableName);
        checkTokenType(ref.getTokenAt(ref.counter++),WHEREregex);
        condition = new Condition(ref,table);
        condition.parse();
        interp();
    }

    public void interp() throws IOException {
        Vector<Integer> rowsToDelete = condition.getRow();
        table.deleteRows(rowsToDelete);
        File f = new File(tableName);
        FileWriter fw = new FileWriter(f);
        fw.write(table.printAll());
        fw.flush();
        fw.close();
    }
}
