package uwu.smsgamer.sml.parser;

import org.jetbrains.annotations.NotNull;
import uwu.smsgamer.sml.exceptions.SMLParseException;
import uwu.smsgamer.sml.map.*;
import uwu.smsgamer.sml.map.values.*;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.stream.Stream;

public class SMLParser {
    public static SMLMap parse(@NotNull final File file) throws IOException, SMLParseException {
        final StringBuilder contentBuilder = new StringBuilder();
        final Stream<String> stream = Files.lines(file.toPath(), StandardCharsets.UTF_8);
        stream.forEach(s -> contentBuilder.append(s).append("\n"));
        return parse(contentBuilder.toString());
    }

    public static SMLMap parse(@NotNull final String text) throws SMLParseException {
        return new SMLParser(new BufferedChars(text)).parse();
    }

    private final BufferedChars bufferedChars;
    private final SMLMap map = new SMLMap();

    public SMLParser(final BufferedChars bufferedChars) {
        this.bufferedChars = bufferedChars;
    }

    public SMLMap parse() throws SMLParseException {
        try {
            StringBuilder startComments = new StringBuilder();
            while (bufferedChars.get() != '{') {
                startComments.append(bufferedChars.get());
                bufferedChars.next();
            }
            startingParse();
            StringBuilder endComments = new StringBuilder();
            while (bufferedChars.hasNext()) {
                endComments.append(bufferedChars.next());
            }
            map.startComments = startComments.toString();
            map.endComments = endComments.toString();
        } catch (IndexOutOfBoundsException e) {
            throw new SMLParseException("Ended unexpectedly.", e);
        }
        return map;
    }

    private void startingParse() throws SMLParseException {
        if (!bufferedChars.hasNext()) return;
        if (bufferedChars.get() != '{') expected("{", String.valueOf(bufferedChars.get()));
        skipWhitespaces();
        while (bufferedChars.get() != '}') {
            map.parentNode.value.add(parseNode());
            skipWhitespaces();
        }
    }

    @NotNull
    private SMLNode parseNode() throws SMLParseException {
        if (bufferedChars.get() != ':') expected(":", String.valueOf(bufferedChars.get()));
        final StringBuilder name = new StringBuilder();
        while (bufferedChars.next() != ':' &&
          bufferedChars.get() != '{' &&
          bufferedChars.get() != '}' &&
          bufferedChars.get() != '(' &&
          bufferedChars.get() != ')' &&
          bufferedChars.get() != '"' &&
          bufferedChars.get() != '\'' &&
          bufferedChars.get() != '[' &&
          bufferedChars.get() != ']') name.append(bufferedChars.get());
        char c = bufferedChars.get();
        if (c == '}' || c == ')' || c == ']') unexpected(c);
        final SMLNode n = new SMLNode(name.toString());
        n.value = parseValue();
        bufferedChars.next();
        return n;
    }

    @NotNull
    private SMLValue parseValue() throws SMLParseException {
        final char c = bufferedChars.get();
        if (c == ':') {
            return SMLNull.getInstance();
        } else if (c == '"' || c == '\'') {
            final String[] s = parseString(c);
            return new SMLString(s[0], s[1]);
        } else if (c == '(') {
            if (bufferedChars.peek(1) == 't') {
                skipToChar(')', "({}:[]\"'");
                return SMLBool.TRUE;
            } else if (bufferedChars.peek(1) == 'f') {
                skipToChar(')', "({}:[]\"'");
                return SMLBool.FALSE;
            } else {
                return parseNumber();
            }
        } else if (c == '{') {
            final SMLObject obj = new SMLObject();
            skipWhitespaces();
            while (bufferedChars.get() != '}') {
                SMLNode node = parseNode();
                obj.add(node);
                skipWhitespaces();
            }
            return obj;
        } else if (c == '[') {
            skipToAnyChar("{}()[]\"'", "");
            final SMLArray arr = new SMLArray();
            while (bufferedChars.get() != ']') {
                arr.add(parseValue());
                skipToAnyChar("{}()[]\"'", "");
            }
            return arr;
        }
        unexpected(c);
        return SMLNull.getInstance();
    }

    @NotNull
    private SMLNum parseNumber() throws SMLParseException {
        final String s = parseString(')')[0];
        if (s.equals("0")) return new SMLNum(0);
        else if (s.startsWith("0x")) return new SMLNum(Integer.parseInt(s.substring(2), 16));
        else if (s.startsWith("0b")) return new SMLNum(Integer.parseInt(s.substring(2), 2));
        else return new SMLNum(Double.parseDouble(s));
    }

    @NotNull
    private String[] parseString(char endChar) throws SMLParseException {
        final StringBuilder s = new StringBuilder();
        final StringBuilder s1 = new StringBuilder();
        while (bufferedChars.next() != endChar) {
            s1.append(bufferedChars.get());
            if (bufferedChars.get() == '\\') {
                if (bufferedChars.peek(1) != '\n') {
                    final char[] chars = getChar();
                    final char[] cs = new char[chars.length - 1];
                    System.arraycopy(chars, 1, cs, 0, chars.length - 1);
                    s.append(chars[0]);
                    s1.append(cs);
                } else {
                    bufferedChars.next();
                    s1.append(bufferedChars.get());
                }
            } else s.append(bufferedChars.get());
        }
        return new String[]{s.toString(), s1.toString()};
    }

    @NotNull
    private char[] getChar() throws SMLParseException {
        char c = bufferedChars.next();
        switch (c) {
            case '\\':
                return new char[]{'\\', c};
            case '\'':
                return new char[]{'\'', c};
            case '"':
                return new char[]{'\"', c};
            case 'b':
                return new char[]{'\b', c};
            case 'f':
                return new char[]{'\f', c};
            case 'n':
                return new char[]{'\n', c};
            case 'r':
                return new char[]{'\r', c};
            case 't':
                return new char[]{'\t', c};
            case 'u':
                int codePoint = 0;
                char[] result = new char[]{'\u0000', c, bufferedChars.peek(1), bufferedChars.peek(2), bufferedChars.peek(3), bufferedChars.peek(4),};
                for (int i = 0; i < 4; i++) {
                    c = bufferedChars.next();
                    int m = (int) Math.pow(16, 3 - i);
                    if (c >= '0' && c <= '9') codePoint += (c - '0') * m;
                    else if (c >= 'a' && c <= 'f') codePoint += (c - 'a' + 0xA) * m;
                    else if (c >= 'A' && c <= 'F') codePoint += (c - 'A' + 0xA) * m;
                    else unexpected(c);
                }
                result[0] = (char) codePoint;
                return result;
            default:
                unexpected(c);
        }
        return new char[0];
    }

    @SuppressWarnings("UnnecessaryContinue")
    private void skipWhitespaces() {
        while (Character.isWhitespace(bufferedChars.next())) continue;
    }

    private void skipToChar(final char c, @NotNull final String exc) throws SMLParseException {
        while (bufferedChars.next() != c)
            if (exc.contains(String.valueOf(bufferedChars.get()))) unexpected(bufferedChars.get());
    }

    private void skipToAnyChar(@NotNull final String s, @NotNull final String exc) throws SMLParseException {
        while (!s.contains(String.valueOf(bufferedChars.next()))) {
            if (exc.contains(String.valueOf(bufferedChars.get()))) unexpected(bufferedChars.get());
        }
    }

    private void expected(@NotNull final String s, @NotNull final String got) throws SMLParseException {
        throw SMLParseException.expected(s, got, bufferedChars);
    }

    private void unexpected(@NotNull final String s) throws SMLParseException {
        throw SMLParseException.unexpected(s, bufferedChars);
    }

    private void unexpected(final char c) throws SMLParseException {
        unexpected(String.valueOf(c));
    }
}
