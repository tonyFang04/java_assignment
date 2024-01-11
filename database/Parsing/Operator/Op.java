package Parsing.Operator;

import DatabaseException.*;
import Parsing.Value;
import Tools.*;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Op {
    protected Table table;
    protected Value value;
    protected String columnName;

    public void setOp(Table table, Value value, String columnName) {
        this.table = table;
        this.value = value;
        this.columnName = columnName;
    }

    public Vector<Integer> getRow() throws InterpretException {
        Vector<Integer> output = new Vector<>();
        Vector<Column> columns = table.getColumns();
        for (Column column : columns) {
            if (columnName.equals(column.getName())) {
                for (int i = 0; i < column.size(); i++) {
                    if (value.getValue().equals(column.getValue(i))) {
                        output.add(i);
                    }
                }
            }
        }
        return output;
    }

    void canConvertToDouble(String input) throws InterpretException {
        if (isTokenType(input, intRegex) || isTokenType(input, floatRegex)) {
            return;
        }
        throw new InterpretException("Can't convert to double");
    }

    public boolean isTokenType(String token, String regex) {
        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(token);
        return m.matches();
    }

    public void parse() throws InterpretException {}

    protected String intRegex = "^([+-]?)([0-9]+)$";
    protected String floatRegex = "^([+-]?)([0-9]+)(\\.)([0-9]+)$";
}
