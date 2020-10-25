import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

import static org.junit.jupiter.api.Assertions.*;

class LexicalAnalyzerTest {

    private LexicalAnalyzer lex;

    private static class CorruptedInputStream extends InputStream {
        @Override
        public int read() throws IOException {
            throw new IOException("I am broken");
        }
    }

    private InputStream getInputStreamFromString(final String str) {
        return new ByteArrayInputStream(str.getBytes());
    }

    private void checkVariable(final LexicalAnalyzer lex, char variableName) throws ParsingException {
        assertEquals(lex.getNextToken(), Token.VARIABLE);
        assertEquals(lex.getVariableName(), variableName);
    }

    @Test
    void simpleExpressionTest() {
        final String test = "a|b";
        try {
            lex = new LexicalAnalyzer(getInputStreamFromString(test));
            checkVariable(lex, 'a');
            assertEquals(lex.getNextToken(), Token.OR);
            checkVariable(lex, 'b');
            assertEquals(lex.getNextToken(), Token.END);
        } catch (ParsingException e) {
            fail();
        }
    }

    @Test
    void hardExpressionTest() {
        final String test = "!(a|b)^c&d";
        try {
            lex = new LexicalAnalyzer(getInputStreamFromString(test));
            assertEquals(lex.getNextToken(), Token.NOT);
            assertEquals(lex.getNextToken(), Token.LPAREN);
            checkVariable(lex, 'a');
            assertEquals(lex.getNextToken(), Token.OR);
            checkVariable(lex, 'b');
            assertEquals(lex.getNextToken(), Token.RPAREN);
            assertEquals(lex.getNextToken(), Token.XOR);
            checkVariable(lex, 'c');
            assertEquals(lex.getNextToken(), Token.AND);
            checkVariable(lex, 'd');
        } catch (ParsingException e) {
            fail();
        }
    }

    @Test
    void corruptedInputStreamTest() {
        assertThrows(ParsingException.class, () -> new LexicalAnalyzer(new CorruptedInputStream()));
    }

    @Test
    void simpleExpressionWithWhitespacesTest() {
        final String test = "   a  \n   |     \r     b\t";
        try {
            lex = new LexicalAnalyzer(getInputStreamFromString(test));
            checkVariable(lex, 'a');
            assertEquals(lex.getNextToken(), Token.OR);
            checkVariable(lex, 'b');
            assertEquals(lex.getNextToken(), Token.END);
            assertEquals(lex.getNextToken(), Token.END);
            assertEquals(lex.getNextToken(), Token.END);
            assertEquals(lex.getNextToken(), Token.END);
            assertEquals(lex.getNextToken(), Token.END);
        } catch (ParsingException e) {
            fail();
        }
    }

    @Test
    void trashTest() {
        final String test = "$";
        try {
            lex = new LexicalAnalyzer(getInputStreamFromString(test));
            assertThrows(ParsingException.class, lex::nextToken);
        } catch (ParsingException e) {
            fail();
        }
    }
}