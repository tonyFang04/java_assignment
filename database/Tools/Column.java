package Tools;

import java.util.Vector;

public class Column {
    private String name;
    private Vector<String> values;
    public Column() {
        values = new Vector<>();
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void addValue(String value) {
        values.add(value);
    }

    public String getValue(int i) {
        return values.get(i);
    }

    public int size() {
        return values.size();
    }

    public void popValue(int i) {
        values.remove(i);
    }

    public void setValue(int i, String value) {
        values.set(i,value);
    }
}
