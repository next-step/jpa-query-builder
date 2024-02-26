package persistence.sql.dml;

public class ValueUtil {
    public static String getValueString(Object value) {
        if (value == null) {
            return "null";
        }
        if (value instanceof String) {
            return String.format("'%s'", value);
        }
        return value.toString();
    }
}
