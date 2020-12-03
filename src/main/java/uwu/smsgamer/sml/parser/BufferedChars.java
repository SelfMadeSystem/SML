package uwu.smsgamer.sml.parser;

public class BufferedChars {
    private final CharSequence chars;
    private int at;
    private int col = 1;
    private int line = 1;

    public BufferedChars(String chars) {
        this.chars = chars.trim();
    }

    public char next() {
        at++;
        col++;
        if (get() == '\n') {
            line++;
            col = 1;
        }
        return get();
    }

    public char get() {
        return chars.charAt(at);
    }

    public char peek(int next) {
        return chars.charAt(at + next);
    }

    public boolean hasNext() {
        return at < chars.length()-1;
    }

    public CharSequence getChars() {
        return chars;
    }

    public int getAt() {
        return at;
    }

    public int getCol() {
        return col;
    }

    public int getLine() {
        return line;
    }
}
