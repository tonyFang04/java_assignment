package Parsing;

import DatabaseException.*;
import Parsing.Operator.*;
import Tokenizing.Tokenizer;
import Tools.Table;

import java.util.HashMap;
import java.util.Vector;

public class Condition extends Parser {
    private Condition left, right;
    private String logicalConnective;
    private String columnName;
    private Value value;
    private Table table;
    private Vector<Integer> rowsToExecute;
    public Condition(Tokenizer ref, Table table) {
        super(ref);
        this.table = table;
    }

    @Override
    public void parse() throws DBException {
        if (isTokenType(ref.getTokenAt(ref.counter),leftParenthesis)) {
            ref.counter++;

            left = new Condition(ref,table); left.parse();
            ref.counter++;

            checkTokenType(ref.getTokenAt(ref.counter),rightParenthesis);
            ref.counter++;

            checkTokenType(ref.getTokenAt(ref.counter),booleanLiteralRegex);
            logicalConnective = ref.getTokenAt(ref.counter);
            ref.counter++;

            checkTokenType(ref.getTokenAt(ref.counter),leftParenthesis);
            ref.counter++;

            right = new Condition(ref,table);right.parse();
            ref.counter++;

            checkTokenType(ref.getTokenAt(ref.counter),rightParenthesis);
            interp();
            return;
        }
        checkTokenType(ref.getTokenAt(ref.counter),alphaNumericRegex);
        columnName = ref.getTokenAt(ref.counter++);
        HashMap<String,Op> map = new HashMap<>();
        map.put("==",new Equal());
        map.put(">",new Greater());
        map.put("<",new Smaller());
        map.put(">=",new GreaterAndEqual());
        map.put("<=",new SmallerAndEqual());
        map.put("!=",new NotEqual());
        map.put("LIKE",new Like(ref));
        if (!map.containsKey(ref.getTokenAt(ref.counter))) {
            throw new BNFSyntaxException("");
        }
        Op operator = map.get(ref.getTokenAt(ref.counter));
        operator.parse();
        ref.counter++;
        value = new Value(ref);
        value.parse();
        operator.setOp(table,value,columnName);
        rowsToExecute = operator.getRow();
    }

    public Vector<Integer> getRow() {
        return rowsToExecute;
    }

    void interp() {
        rowsToExecute = left.getRow();
        Vector<Integer> rowsFromRight = right.getRow();
        if (logicalConnective.equals("AND")) {
            rowsToExecute.retainAll(rowsFromRight);
        } else {
            for (int i: rowsFromRight) {
                if (!rowsToExecute.contains(i)) {
                    rowsToExecute.add(i);
                }
            }
        }

    }

    private String booleanLiteralRegex = "^(AND|OR)$";
}
