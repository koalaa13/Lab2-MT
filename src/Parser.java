import java.io.InputStream;

public class Parser {
    public static Tree EPS = new Tree("EPS");
    private LexicalAnalyzer lex;

    private Tree S() throws ParsingException {
        if (lex.getCurToken() == Token.END) {
            return new Tree("S", EPS);
        } else {
            return new Tree("S", E());
        }
    }

    private Tree E() throws ParsingException {
        return new Tree("E", O(), EPrime());
    }

    private Tree EPrime() throws ParsingException {
        if (lex.getCurToken() == Token.OR) {
            lex.nextToken();
            return new Tree("E`", new Tree("|"), O(), EPrime());
        } else if (lex.getCurToken() == Token.XOR) {
            lex.nextToken();
            return new Tree("E`", new Tree("^"), O(), EPrime());
        } else {
            return new Tree("E`", EPS);
        }
    }

    private Tree O() throws ParsingException {
        return new Tree("O", F(), OPrime());
    }

    private Tree OPrime() throws ParsingException {
        if (lex.getCurToken() == Token.AND) {
            lex.nextToken();
            return new Tree("O`", new Tree("&"), F(), OPrime());
        } else {
            return new Tree("O`", EPS);
        }
    }

    private Tree F() throws ParsingException {
        if (lex.getCurToken() == Token.NOT) {
            lex.nextToken();
            return new Tree("F", new Tree("!"), F());
        } else if (lex.getCurToken() == Token.LPAREN) {
            lex.nextToken();
            Tree ch = E();
            if (lex.getCurToken() != Token.RPAREN) {
                throw new ParsingException("Can not find closing bracket", lex.getCurPos());
            }
            lex.nextToken();
            return new Tree("F", new Tree("("), ch, new Tree(")"));
        } else if (lex.getCurToken() == Token.VARIABLE) {
            lex.nextToken();
            return new Tree("F", new Tree(String.valueOf(lex.getVariableName())));
        } else {
            throw new ParsingException("Incorrect expression", lex.getCurPos());
        }
    }

    public Tree parse(final InputStream is) throws ParsingException {
        lex = new LexicalAnalyzer(is);
        lex.nextToken();
        return S();
    }
}
