package persistence;

public class Column implements Node {
    private static final String SPACE = " ";

    private final String name;
    private final String type;
    private final boolean unique;
    private final StringBuilder stringBuilder;


    public Column(String name, String type, boolean unique) {
        this.name = name;
        this.type = type;
        this.unique = unique;
        this.stringBuilder = new StringBuilder();
    }

    public static Column of(String name, Class<?> type, boolean unique) {
        return new Column(name, Type.valueOf(type), unique);
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

    private String token(String token) {
        return token + SPACE;
    }

    @Override
    public String expression() {
        stringBuilder.append(token(name()));
        stringBuilder.append(token(type()));

        if (isEndOfBlank()) {
            deleteEndChar();
        }

        return stringBuilder.toString();
    }

    private void deleteEndChar() {
        stringBuilder.deleteCharAt(stringBuilder.length() - 1);
    }

    private boolean isEndOfBlank() {
        return stringBuilder.length() > 0 && stringBuilder.charAt(stringBuilder.length() - 1) == ' ';
    }

}
