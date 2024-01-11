package Parsing;

import DatabaseException.*;
import Tokenizing.Tokenizer;

import java.io.*;
import java.util.Vector;

public class Insert extends Command {
    private String tableName;
    private ValueList values;

    public Insert(Tokenizer ref, Directory databaseDirectory) {
        super(ref,databaseDirectory);
        values = null;
    }

    @Override
    public void parse() throws DBException, IOException {
        checkTokenType(ref.getTokenAt(ref.counter++), INTOregex);
        checkTokenType(ref.getTokenAt(ref.counter), alphaNumericRegex);
        tableName = databaseDirectory.getAddress() + File.separator + ref.getTokenAt(ref.counter++) + fileSuffix;
        checkTokenType(ref.getTokenAt(ref.counter++), VALUESregex);
        checkTokenType(ref.getTokenAt(ref.counter++), leftParenthesis);
        values = new ValueList(ref);
        values.parse();
        ref.counter++;
        checkTokenType(ref.getTokenAt(ref.counter), rightParenthesis);
        interp();
    }

    void interp() throws InterpretException, IOException {
        File fileForTable = makeTableFile();
        int id = getRowID();
        writeToTableFile(fileForTable,id);
    }

    File makeTableFile() throws InterpretException, IOException {
        File fileForTable = new File(tableName);
        if (!fileForTable.exists()) {
            throw new InterpretException("Table does not exist.");
        }
        fileForTable.createNewFile();
        return fileForTable;
    }

    int getRowID() throws IOException {
        File f = new File(tableName);
        FileReader reader = new FileReader(f);
        BufferedReader buffReader = new BufferedReader(reader);
        int i = 0;
        while (buffReader.readLine() != null) {
            i++;
        }
        return i;
    }

    void writeToTableFile(File fileForTable, int id) throws IOException {
        FileWriter writer = new FileWriter(fileForTable,true);
        writer.append("\n");
        writer.append(Integer.toString(id));
        Vector<String> list = values.getValueList();
        for (int i = 0; i < list.size(); i++) {
            writer.append("\t");
            writer.append(list.get(i));
        }
        writer.flush();
        writer.close();
    }

    private String INTOregex = "^INTO$";
    private String VALUESregex = "^VALUES$";
}
