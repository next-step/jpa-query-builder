package persistence.sql.clause;

import java.lang.reflect.Field;

public record SetValueClause(String column, String value) implements ValueClause {

    public static SetValueClause newInstance(Field field, Object entity, String columnName) {

        Object extractedValue = Clause.extractValue(field, entity);
        String columnValue = Clause.toColumnValue(extractedValue);

        return new SetValueClause(columnName, columnValue);
    }


    @Override
    public String clause() {
        return "%s = %s".formatted(column(), value());
    }
}
