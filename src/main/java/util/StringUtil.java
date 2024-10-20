package util;

public class StringUtil {

    private final static String SINGLE_QUOTE = "'";

    public static String wrapSingleQuote(Object value) {
        return SINGLE_QUOTE + value + SINGLE_QUOTE;
    }
}
