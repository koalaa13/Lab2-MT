import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import static org.junit.jupiter.api.Assertions.*;

class ParserTest {
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
}