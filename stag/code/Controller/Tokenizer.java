package Controller;

public class Tokenizer {
    private final String name;
    private final String command;
    public Tokenizer(String input) {
        int i = 0;
        while (input.charAt(i) != ':') {
            i++;
        }
        name = input.substring(0,i);
        command = input.substring(i + 1);
    }

    public String getName() {
        return name;
    }

    public String getCommand() {
        return command;
    }
}
