package OXOExceptions;

public class InvalidIdentifierCharacterException extends InvalidIdentifierException {
    char character;
    RowOrColumn type;
    public InvalidIdentifierCharacterException(char character, RowOrColumn type) {
        this.character = character;
        this.type = type;
    }
    @Override
    public String toString() {
        if (this.type == RowOrColumn.ROW) {
            return "InvalidIdentifierCharacterException: incoming character for row is '" + this.character
                    + "', but the VALID character for row is within 'a - i' or 'A - I'.";
        } else {
            return "InvalidIdentifierCharacterException: incoming character for column is '" + this.character
                    + "', but the VALID character for row is within '1 - 9'.";
        }
    }
}
