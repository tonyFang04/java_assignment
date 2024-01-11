package OXOExceptions;

public class InvalidIdentifierLengthException extends InvalidIdentifierException {
    int length;
    public InvalidIdentifierLengthException(int length) {
        this.length = length;
    }
    @Override
    public String toString() {
        return "InvalidIdentifierLengthException: the length of incoming command is '" + length
                + "', but the VALID length is 2.";
    }
}
