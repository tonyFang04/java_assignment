package OXOExceptions;

public class InvalidIdentifierException extends CellDoesNotExistException {
    @Override
    public String toString() {
        return "InvalidIdentifierException";
    }
}
