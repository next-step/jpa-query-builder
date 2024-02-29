package persistence.sql.ddl.column;

import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.util.Map;
import java.util.function.Function;

public class ColumnClause {
    private static final Map<Type, Function<String, String>> typeToSqlConverter = Map.of(
            String.class, fieldName -> String.format("%s VARCHAR(30)", fieldName),
            Integer.class, fieldName -> String.format("%s INT", fieldName),
            int.class, fieldName -> String.format("%s INT", fieldName)
    );
    private final String name;
    private final Class<?> type;
    private final NullClause nullClause;

    public ColumnClause(Field field) {
        this.name = getNameFrom(field);
        this.type = field.getType();
        this.nullClause = new NullClause(field);
    }

    public String getQuery() {
        return typeToSqlConverter.get(type).apply(name) + " " + nullClause.getQuery();
    }

    public String getNameFrom(Field field) {
        jakarta.persistence.Column column = field.getAnnotation(jakarta.persistence.Column.class);
        if (column == null) {
            return field.getName();
        }
        if (column.name().isEmpty()) {
            return field.getName();
        }
        return column.name();
    }

    public String name() {
        return this.name;
    }
}
