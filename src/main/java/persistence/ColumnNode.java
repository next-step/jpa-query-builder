package persistence;

public class ColumnNode implements Node {
    private static final String SPACE = " ";

    private final String name;
    private final String type;
    private final boolean unique;
    private final boolean nullable;
    private final StringBuilder stringBuilder;

    public ColumnNode(String name, String type, boolean unique, boolean nullable) {
        this.name = name;
        this.type = type;
        this.unique = unique;
        this.nullable = nullable;
        this.stringBuilder = new StringBuilder();
    }

    public static ColumnNode of(String name, Class<?> type, int size, boolean unique) {
        return new ColumnNode(name, Type.valueOf(type, size), unique, false);
    }

    public static ColumnNode of(String name, Class<?> type, int size, boolean unique, boolean nullable) {
        return new ColumnNode(name, Type.valueOf(type, size), unique, nullable);
    }

    @Override
    public String expression() {
        stringBuilder.append(token(name()));
        stringBuilder.append(token(type()));
        stringBuilder.append(token(nullable()));

        if (isEndOfBlank()) {
            deleteEndChar();
        }

        return stringBuilder.toString();
    }

    private String token(String token) {
        return token + SPACE;
    }

    public String name() {
        return name;
    }

    public String type() {
        return type;
    }

    public boolean unique() {
        return unique;
    }

    private String nullable() {
        if (nullable || unique()) {
            return "";
        }

        return "not null";
    }

    private void deleteEndChar() {
        stringBuilder.deleteCharAt(stringBuilder.length() - 1);
    }

    private boolean isEndOfBlank() {
        return stringBuilder.length() > 0 && stringBuilder.charAt(stringBuilder.length() - 1) == ' ';
    }

    public boolean sameName(String other) {
        return name.equals(other);
    }
}
