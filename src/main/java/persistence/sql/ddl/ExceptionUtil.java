package persistence.sql.ddl;

public class ExceptionUtil {

    public static void requireNonNull(Class<?> clazz) {
        if (clazz == null) {
            throw new IllegalArgumentException("class가 존재하지 않습니다.");
        }
    }

}
