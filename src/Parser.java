import java.io.InputStream;

public class Parser {
    LexicalAnalyzer lex;

    Tree S() throws ParsingException {
        switch (lex.getCurToken()) {
            case END:
                return new Tree("S");
            default:
                throw new ParsingException("Error while parsing expression", lex.getCurPos());
        }
    }

    Tree parse(final InputStream is) throws ParsingException {
        lex = new LexicalAnalyzer(is);
        lex.nextToken();
        return S();
    }
}
