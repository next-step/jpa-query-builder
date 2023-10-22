package persistence.sql.schema;

public class ValueMeta {

    private static final String STRING_TYPE_FORMAT = "'%s'";
    private final Object value;

    private ValueMeta(Object value) {
        this.value = value;
    }

    public static ValueMeta of(Object value) {
        return new ValueMeta(value);
    }

    public Object getValue() {
        return value;
    }

    public String getFormattedValue() {
        return formatValueAsString(this.value);
    }

    public static String formatValueAsString(Object object) {
        if (object == null) {
            return null;
        }

        if (object instanceof String) {
            return String.format(STRING_TYPE_FORMAT, object);
        }

        return String.valueOf(object);
    }
}
