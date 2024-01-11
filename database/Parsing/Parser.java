package Parsing;

import DatabaseException.*;
import Tokenizing.Tokenizer;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public abstract class Parser {
    protected Tokenizer ref;
    protected Directory databaseDirectory;
    protected String output;

    public Parser(Tokenizer ref) {
        this.ref = ref;
        output = "[OK]\n";
    }

    public Parser(Tokenizer ref, Directory databaseDirectory) {
        this.ref = ref;
        this.databaseDirectory = databaseDirectory;
        output = "[OK]\n";
    }

    public abstract void parse() throws DBException, IOException;

    public String getOutput() {
        return output;
    }

    public void checkTokenType(String token, String type) throws DBException {
        if (!isTokenType(token,type)) {
            throw new BNFSyntaxException("");
        }
    }

    public boolean isTokenType(String token, String regex) {
        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(token);
        return m.matches();
    }

    protected String alphaNumericRegex = "^([a-zA-Z0-9]+)$";
    protected String intRegex = "^([+-]?)([0-9]+)$";
    protected String boolRegex = "^(true|false)$";
    protected String floatRegex = "^([+-]?)([0-9]+)(\\.)([0-9]+)$";
    protected String leftParenthesis = "^\\($";
    protected String rightParenthesis = "^\\)$";
    protected String FROMregex = "^FROM$";
    protected String WHEREregex = "^WHERE$";
    protected String commaRegex = "^,$";
    protected String fileSuffix = ".tab";
}
