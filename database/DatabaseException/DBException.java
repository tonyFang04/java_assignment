package DatabaseException;

public class DBException extends Exception {
    protected String errorMessage;
    public DBException(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    @Override
    public String toString() {
        return "[ERROR]: " + errorMessage;
    }
}
