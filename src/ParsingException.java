public class ParsingException extends Exception {
    public ParsingException(String reason, int pos) {
        super(reason);
        System.out.printf("Parsing exception at pos %d", pos);
    }
}
