package Parsing;

import DatabaseException.*;

public class Directory {
    private String address;
    private boolean isSet;

    public Directory() {
        isSet = false;
    }

    public void setAddress(String address) {
        this.address = address;
        isSet = true;
    }

    public String getAddress() throws InterpretException {
        if (!isSet) {
            throw new InterpretException("No database available.");
        }
        return address;
    }
}
