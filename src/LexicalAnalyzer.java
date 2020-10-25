import java.io.IOException;
import java.io.InputStream;

public class LexicalAnalyzer {
    private final InputStream is;
    private int curChar;

    public char getVariableName() {
        return variableName;
    }

    private char variableName;

    private int curPos;

    private Token curToken;

    public LexicalAnalyzer(InputStream is) throws ParsingException {
        this.is = is;
        curPos = 0;
        nextChar();
    }

    private void nextChar() throws ParsingException {
        curPos++;
        try {
            curChar = is.read();
        } catch (IOException e) {
            throw new ParsingException(e.getMessage(), curPos);
        }
    }

    public int getCurPos() {
        return curPos;
    }

    public Token getCurToken() {
        return curToken;
    }

    public void nextToken() throws ParsingException {
        while (Character.isWhitespace(curChar)) {
            nextChar();
        }
        switch (curChar) {
            case '(':
                nextChar();
                curToken = Token.LPAREN;
                break;
            case ')':
                nextChar();
                curToken = Token.RPAREN;
                break;
            case '!':
                nextChar();
                curToken = Token.NOT;
                break;
            case '&':
                nextChar();
                curToken = Token.AND;
                break;
            case '|':
                nextChar();
                curToken = Token.OR;
                break;
            case '^':
                nextChar();
                curToken = Token.XOR;
                break;
            case -1:
                nextChar();
                curToken = Token.END;
                break;
            default:
                if (Character.isLetter(curChar)) {
                    variableName = (char) curChar;
                    nextChar();
                    curToken = Token.VARIABLE;
                } else {
                    throw new ParsingException("Illegal character " + curChar, curPos);
                }
        }
    }
}
