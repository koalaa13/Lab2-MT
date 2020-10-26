import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import static org.junit.jupiter.api.Assertions.*;

class ParserStringRepresentaionTest {
    private final static Parser parser = new Parser();

    private InputStream getInputStreamFromString(final String str) {
        return new ByteArrayInputStream(str.getBytes());
    }

    private void test(final String test) {
        try {
            assertEquals(parser.parse(getInputStreamFromString(test)).toString(), test);
        } catch (ParsingException e) {
            fail();
        }
    }

    private String removeWhitespaces(final String str) {
        StringBuilder res = new StringBuilder();
        for (char c : str.toCharArray()) {
            if (!Character.isWhitespace(c)) {
                res.append(c);
            }
        }
        return res.toString();
    }

    @Test
    void simpleExpressionTest() {
        final String test = "a|b";
        test(test);
    }

    @Test
    void hardExpressionTest() {
        final String test = "!(a|b)^c&d";
        test(test);
    }

    @Test
    void emptyTest() {
        final String test = "";
        test(test);
    }

    @Test
    void incorrectBracketsTest() {
        final String notPairedOpenBracketTest = "(a|b)&(c";
        assertThrows(ParsingException.class, () -> parser.parse(getInputStreamFromString(notPairedOpenBracketTest)));

        final String notPairedCloseBracketTest = "a)&b";
        assertThrows(ParsingException.class, () -> parser.parse(getInputStreamFromString(notPairedCloseBracketTest)));
    }

    @Test
    void simpleExpressionWithWhitespacesTest() {
        final String test = "   a  \n   |     \r     b\t";
        final String testWithoutWhitespaces = removeWhitespaces(test);
        try {
            assertEquals(testWithoutWhitespaces, parser.parse(getInputStreamFromString(test)).toString());
        } catch (ParsingException e) {
            fail();
        }
    }

    @Test
    void incorrectExpressionTest() {
        final String emptyBrackets = "()";
        assertThrows(ParsingException.class, () -> parser.parse(getInputStreamFromString(emptyBrackets)));

        final String operationWithoutOperands = "a&&b";
        assertThrows(ParsingException.class, () -> parser.parse(getInputStreamFromString(operationWithoutOperands)));

        final String incorrectSymbols = "a&b{c}";
        assertThrows(ParsingException.class, () -> parser.parse(getInputStreamFromString(incorrectSymbols)));
    }
}