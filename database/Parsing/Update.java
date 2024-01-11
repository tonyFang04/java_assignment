package Parsing;

import DatabaseException.*;
import Tokenizing.Tokenizer;
import Tools.Table;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Vector;

public class Update extends Command {
    private Table table;
    private String tableName;
    private Condition condition;
    private NameValueList list;
    String SETregex = "^SET$";
    public Update(Tokenizer ref, Directory databaseDirectory) {
        super(ref,databaseDirectory);
    }

    @Override
    public void parse() throws DBException, IOException {
        checkTokenType(ref.getTokenAt(ref.counter), alphaNumericRegex);
        tableName = databaseDirectory.getAddress() + File.separator + ref.getTokenAt(ref.counter++) + fileSuffix;
        table = new Table(tableName);
        checkTokenType(ref.getTokenAt(ref.counter++),SETregex);
        list = new NameValueList(ref);
        list.parse();
        ref.counter++;
        checkTokenType(ref.getTokenAt(ref.counter++),WHEREregex);
        condition = new Condition(ref,table);
        condition.parse();
        interp();
    }

    void interp() throws InterpretException, IOException {
        Vector<Integer> rowsToExecute = condition.getRow();
        Vector<String[]> pairList = list.getPairList();
        for (String[] pair : pairList) {
            int colNumber = table.isInColumn(pair[0]);
            table.setValue(rowsToExecute,colNumber,pair[1]);
        }

        File f = new File(tableName);
        FileWriter fw = new FileWriter(f);
        fw.write(table.printAll());
        fw.flush();
        fw.close();
    }
}
