package Parsing;

import DatabaseException.*;
import Tokenizing.Tokenizer;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Vector;


public class CreateTable extends Create {
    private String tableName;
    private AttributeList attributeNames;

    public CreateTable(Tokenizer ref, Directory databaseDirectory) {
        super(ref,databaseDirectory);
        attributeNames = null;
    }

    @Override
    public void parse() throws DBException, IOException {
        checkDatabaseExists();
        checkTokenType(ref.getTokenAt(ref.counter), alphaNumericRegex);
        tableName = databaseDirectory.getAddress() + File.separator + ref.getTokenAt(ref.counter) + fileSuffix;
        if (isTokenType(ref.getTokenAt(ref.counter + 1),leftParenthesis)) {
            ref.counter += 2;
            attributeNames = new AttributeList(ref);
            attributeNames.parse();
            ref.counter++;
            checkTokenType(ref.getTokenAt(ref.counter), rightParenthesis);
        }
        interp();
    }

    void checkDatabaseExists() throws InterpretException {
        File f = new File(databaseDirectory.getAddress());
        if (!f.exists()) {
            throw new InterpretException("Database does not exist");
        }
    }

    void interp() throws InterpretException, IOException {
        File fileForTable = makeTableFile();
        writeToTableFile(fileForTable);
    }

    File makeTableFile() throws InterpretException, IOException {
        File fileForTable = new File(tableName);
        if (fileForTable.exists()) {
            throw new InterpretException("Table already exists.");
        }
        fileForTable.createNewFile();
        return fileForTable;
    }

    void writeToTableFile(File fileForTable) throws IOException {
        FileWriter writer = new FileWriter(fileForTable);
        writer.write("id");
        if (attributeNames != null) {
            Vector<String> columnNames = attributeNames.getColumnNames();
            for (int i = 0; i < columnNames.size(); i++) {
                writer.write("\t");
                writer.write(columnNames.get(i));
            }
        }
        writer.flush();
        writer.close();
    }
}
