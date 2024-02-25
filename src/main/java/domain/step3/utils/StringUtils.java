package domain.step3.utils;

public class StringUtils {

    private StringUtils() {

    }

    public static boolean isBlankOrEmpty(String target) {
        return target.isBlank() || target.isEmpty();
    }
}
