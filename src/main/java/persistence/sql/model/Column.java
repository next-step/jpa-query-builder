package persistence.sql.model;

public class Column {

    private static final String NOT_NULL = "NOT NULL";
    private static final String PRIMARY_KEY = "PRIMARY KEY";

    private final String name;
    private final String type;
    private final boolean nullable;
    private final boolean primary;

    private Column(String name, String type, boolean nullable, boolean primary) {
        this.name = name;
        this.type = type;
        this.nullable = nullable;
        this.primary = primary;
    }

    public static Column create(String name, String type, boolean nullable, boolean primary) {
        return new Column(name, type, nullable, primary);
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public boolean isNullable() {
        return nullable;
    }

    public boolean isPrimary() {
        return primary;
    }

    public String getDefinition() {

        StringBuilder definition = new StringBuilder();
        definition.append(this.name).append(" ");
        definition.append(this.type).append(" ");

        if (!nullable) {
            definition.append(NOT_NULL).append(" ");
        }

        if (primary) {
            definition.append(PRIMARY_KEY).append(" ");
        }
        return definition.toString();
    }
}
