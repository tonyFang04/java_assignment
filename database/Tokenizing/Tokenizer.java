package Tokenizing;
import java.util.*;
import DatabaseException.BNFSyntaxException;
public class Tokenizer {
    private Vector<String> tokens;
    public int counter;
    public Tokenizer() {
        tokens = new Vector<String> ();
        counter = 0;
    }

    public void tokenizeIncomingCommands(String command) throws BNFSyntaxException {
        checkInput(command);
        while (command.length() > 0) {
            if(isDelimiter(command.charAt(0))) {
                command = addDelimiter(command);
            } else {
                command = addWord(command);
            }
        }
    }

    void checkInput(String command) throws BNFSyntaxException {
        if (command == null) {
            throw new BNFSyntaxException("Command cannot be null");
        }
        if (command.charAt(command.length() - 1) != ';') {
            throw new BNFSyntaxException("Semi colon missing at end of line");
        }
    }

    String addWord(String command) {
        int i = 0;
        while(isDelimiter(command.charAt(i)) == false) {
            i++;
        }
        tokens.add(command.substring(0,i));
        return command.substring(i);
    }

    String addDelimiter(String command) {
        if (command.charAt(0) != ' ') {
            tokens.add(command.substring(0,1));
        }
        return command.substring(1);
    }

    boolean isDelimiter(char input) {
        if (input == ' ' || input == ';' || input == '\'' || input == ',' || input == '(' || input == ')') {
            return true;
        }
        return false;
    }

    public String getTokenAt(int i) {
        return tokens.get(i);
    }

    int getNoOfTokens() {
        return tokens.size();
    }
}
