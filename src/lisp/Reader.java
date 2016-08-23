package lisp;

import static lisp.Lisp.*;

import java.io.IOException;

public class Reader {

    public static final Obj EOF_OBJECT = new Obj(){};
    static final int EOF = -1;
    static final Obj DOT = new Obj(){};

    private final java.io.Reader reader;
    int ch;
    
    public Reader(java.io.Reader reader) {
        this.reader = reader;
        get();
    }
    
    int get() {
        if (ch == EOF)
            return EOF;
        try {
            return ch = reader.read();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
   
    static boolean isSpace(int ch) {
        return Character.isWhitespace(ch);
    }
    
    static boolean isDigit(int ch) {
        return Character.isDigit(ch);
    }
    
    static boolean isDelimiter(int ch) {
        switch (ch) {
        case '\'': case '(': case ')':
        case ',':
        case '"':
            return true;
        }
        return false;
    }
    
    static boolean isSymbol(int ch) {
        return ch != EOF && !isSpace(ch) && !isDelimiter(ch);
    }

    Obj readList() {
        Pair.Builder builder = Pair.builder();
        while (true) {
            skipSpaces();
            switch (ch) {
            case ')':
                get();
                return builder.build();
            case EOF:
                throw new RuntimeException("')' expected");
            default:
                Obj r = readObject();
                if (r == DOT) {
                    Obj last = read();
                    skipSpaces();
                    if (ch != ')')
                        throw new RuntimeException("')' expected");
                    get();
                    builder.last(last);
                    return builder.build();
                }
                builder.tail(r);
                break;
            }
        }
    }
    
    Obj readSymbol(StringBuilder sb) {
        while (isSymbol(ch)) {
            sb.append((char)ch);
            get();
        }
        String s = sb.toString();
        switch (s) {
        case "#t": return TRUE;
        case "#f": return FALSE;
        default: return symbol(sb.toString());
        }
    }
    
    Obj readNumber(StringBuilder sb) {
        while (isDigit(ch)) {
            sb.append((char)ch);
            get();
        }
        return Num.of(sb.toString());
    }

    Obj readAtom() {
        int first = ch;
        StringBuilder sb = new StringBuilder();
        sb.append((char)first);
        get();
        switch (first) {
        case '+': case '-':
            return isDigit(ch) ? readNumber(sb) : readSymbol(sb);
        case '.':
            return isSymbol(ch) ? readSymbol(sb) : DOT;
        default:
            return isDigit(first) ? readNumber(sb) : readSymbol(sb);
        }
    }

    void skipSpaces() {
        while (isSpace(ch))
            get();
    }

    Obj readObject() {
        skipSpaces();
        switch (ch) {
        case EOF:
            return EOF_OBJECT;
        case '(':
            get();
            return readList();
        case '\'':
            get();
            return list(QUOTE, read());
        default:
            return readAtom();
        }
    }

    public Obj read() {
        Obj r = readObject();
        if (r == DOT)
            throw new RuntimeException("unexpected dot");
        return r;
    }
}
