package Tools;

import DatabaseException.InterpretException;

import java.io.*;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Vector;

public class Table {
    private Vector<Column> columns;
    public Table() {}
    public Table(String tableName) throws IOException, InterpretException {
        columns = new Vector<>();
        File fileToOpen = new File(tableName);
        if (!fileToOpen.exists()) {
            throw new InterpretException("Table does not exist.");
        }
        FileReader reader = new FileReader(fileToOpen);
        BufferedReader buffReader = new BufferedReader(reader);


        String currentLine = buffReader.readLine();
        Vector<String> row = new Vector<>(Arrays.asList(currentLine.split("\t")));
        for (String s : row) {
            Column col = new Column();
            col.setName(s);
            columns.add(col);
        }
        while ((currentLine = buffReader.readLine()) != null) {
            row = new Vector<>(Arrays.asList(currentLine.split("\t")));
            if (row.size() > columns.size()) {
                throw new InterpretException("not enough columns.");
            }
            for (int i = 0; i < row.size(); i++) {
                Column update = columns.get(i);
                update.addValue(row.get(i));
                columns.set(i,update);
            }
            for (int i = row.size(); i < columns.size(); i++) {
                Column update = columns.get(i);
                update.addValue("");
                columns.set(i,update);
            }
        }
        buffReader.close();
    }

    public String printAll() {
        String output = printColumnNames();

        int colSize = columns.get(0).size();
        for (int i = 0; i < colSize; i++) {
            output += "\n";
            for (int j = 0; j < columns.size(); j++) {
                output += columns.get(j).getValue(i);
                output += "\t";
            }
            output = output.substring(0,output.length() - 1);
        }
        return output;
    }

    public String printAllColumns(Vector<Integer> rows) {
        String output = printColumnNames();

        if (rows.size() == 0) {
            return output;
        }

        for (int i : rows) {
            output += "\n";
            for (int j = 0; j < columns.size(); j++) {
                output += columns.get(j).getValue(i);
                output += "\t";
            }
            output = output.substring(0,output.length() - 1);
        }
        return output;
    }

    public String printAllRows(Vector<Integer> cols) {
        String output = "";
        for (int i : cols) {
            output += columns.get(i).getName();
            output += "\t";
        }
        output = output.substring(0,output.length() - 1);

        int colSize = columns.get(0).size();
        for (int i = 0; i < colSize; i++) {
            output += "\n";
            for (int j : cols) {
                output += columns.get(j).getValue(i);
                output += "\t";
            }
            output = output.substring(0,output.length() - 1);
        }
        return output;
    }

    public Vector<Integer> queryColumns(Vector<String> columnNames) throws InterpretException {
        Vector<Integer> output = new Vector<>();
        for (String name : columnNames) {
            for (int i = 0; i < columns.size(); i++) {
                if (name.equals(columns.get(i).getName())) {
                    output.add(i);
                }
            }
        }
        if (output.size() != columnNames.size()) {
            throw new InterpretException("attribute does not exist");
        }
        return output;
    }

    public String printColumnNames() {
        String output = "";
        for (Column column : columns) {
            output += column.getName();
            output += "\t";
        }
        output = output.substring(0,output.length() - 1);
        return output;
    }

    public String printRowsAndColumns(Vector<Integer> rows, Vector<Integer> cols) {
        String output = "";
        for (int i : cols) {
            output += columns.get(i).getName();
            output += "\t";
        }
        output = output.substring(0,output.length() - 1);

        for (int i : rows) {
            output += "\n";
            for (int j : cols) {
                output += columns.get(j).getValue(i);
                output += "\t";
            }
            output = output.substring(0,output.length() - 1);
        }
        return output;
    }

    public Vector<Column> getColumns() {
        return columns;
    }

    public void deleteRows(Vector<Integer> rows) {
        Collections.sort(rows,Collections.reverseOrder());
        for (int i = 0; i < columns.size(); i++) {
            for (int j: rows) {
                Column column = columns.get(i);
                column.popValue(j);
                columns.set(i,column);
            }
        }
    }

    public void setValue(Vector<Integer> rows, int col, String value) {
        Column column = columns.get(col);
        for (int row : rows) {
            column.setValue(row,value);
        }
        columns.set(col,column);
    }

    public int isInColumn(String name) throws InterpretException {
        for (int i = 0; i < columns.size(); i++) {
            if (name.equals(columns.get(i).getName())) {
                return i;
            }
        }
        throw new InterpretException("attribute name does not exist.");
    }

}
