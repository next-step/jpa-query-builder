package persistence.sql.ddl;

import java.lang.reflect.Field;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import persistence.sql.ddl.constraints.strategy.ConstraintsStrategy;
import persistence.sql.ddl.constraints.strategy.DefaultConstraintsStrategy;
import persistence.sql.ddl.type.DataTypeBuilder;
import persistence.sql.ddl.type.impl.DefaultDataTypeBuilder;

public abstract class QueryBuilder {

    private final ConstraintsStrategy constraintsStrategy = new DefaultConstraintsStrategy();

    private final DataTypeBuilder dataTypeBuilder = new DefaultDataTypeBuilder();

    public String buildDDL(final Class<?> clazz) {
        return String.format(
            "CREATE TABLE %s (%s)",
            getTableNameFrom(clazz),
            getTableColumnDefinitionFrom(clazz)
        );
    }

    public String buildDropQuery(Class<?> clazz) {
        return String.format(
            "DROP TABLE %s",
            getTableNameFrom(clazz)
        );
    }

    public abstract String getTableNameFrom(final Class<?> clazz);

    public abstract String getTableColumnDefinitionFrom(final Class<?> clazz);

    public String getColumnDefinitionFrom(Field field) {
        return Stream.of(
                getColumnNameFrom(field),
                getColumnDataTypeDefinitionFrom(field),
                getColumnConstraintsFrom(field),
                getPrimaryKeyConstraintFrom(field)
            )
            .filter(s -> !s.isBlank())
            .collect(Collectors.joining(" "));
    }

    public abstract String getColumnNameFrom(Field field);

    public String getColumnDataTypeDefinitionFrom(Field field) {
        return dataTypeBuilder.getDataTypeDefinitionFrom(field);
    }

    public String getColumnConstraintsFrom(Field field) {
        return constraintsStrategy.getConstraintsFrom(field);
    }

    public abstract String getPrimaryKeyConstraintFrom(Field field);
}
