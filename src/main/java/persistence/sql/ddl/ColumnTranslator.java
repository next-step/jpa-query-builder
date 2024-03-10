package persistence.sql.ddl;

import static persistence.sql.ddl.common.StringConstants.COLUMN_DEFINITION_DELIMITER;

import jakarta.persistence.Column;
import jakarta.persistence.Id;
import jakarta.persistence.Transient;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Comparator;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import persistence.sql.ddl.constraints.strategy.ConstraintsStrategy;
import persistence.sql.ddl.constraints.strategy.DefaultConstraintsStrategy;
import persistence.sql.ddl.type.DataTypeMapping;
import persistence.sql.ddl.type.impl.DefaultDataTypeMapping;

public class ColumnTranslator {

    private final ConstraintsStrategy constraintsStrategy;

    private final DataTypeMapping dataTypeMapping;

    public ColumnTranslator() {
        this(
            new DefaultConstraintsStrategy(),
            new DefaultDataTypeMapping()
        );
    }

    public ColumnTranslator(
        ConstraintsStrategy constraintsStrategy,
        DataTypeMapping dataTypeMapping
    ) {
        this.constraintsStrategy = constraintsStrategy;
        this.dataTypeMapping = dataTypeMapping;
    }

    public String getColumnDefinitionsFrom(Class<?> entityClass) {
        return getColumnFieldStream(entityClass)
            .map(this::getColumnDefinitionFrom)
            .collect(Collectors.joining(COLUMN_DEFINITION_DELIMITER));
    }

    public String getColumnDefinitionFrom(Field field) {
        return Stream.of(
                getColumnNameFrom(field),
                dataTypeMapping.getDataTypeDefinitionFrom(field),
                constraintsStrategy.getConstraintsFrom(field)
            )
            .filter(s -> !s.isBlank())
            .collect(Collectors.joining(" "));
    }

    public String getColumnNameFrom(Field field) {
        if (!field.isAnnotationPresent(Column.class)) {
            return field.getName();
        }

        Column column = field.getAnnotation(Column.class);

        if (column.name().isEmpty()) {
            return field.getName();
        }

        return column.name();
    }

    public String getPrimaryKeyColumnName(Class<?> entityClass) {
        return Arrays.stream(entityClass.getDeclaredFields())
            .filter(field -> field.isAnnotationPresent(Id.class))
            .findFirst()
            .map(this::getColumnNameFrom)
            .orElseThrow(() -> new IllegalStateException("Primary key not found"));
    }

    public String getColumnNamesClauseWithoutPrimaryKey(Class<?> entityClass) {
        return getColumnFieldStream(entityClass)
            .filter(field -> !field.isAnnotationPresent(Id.class))
            .map(this::getColumnNameFrom)
            .collect(Collectors.joining(COLUMN_DEFINITION_DELIMITER));
    }

    public String getColumnNamesClause(Class<?> entityClass) {
        return getColumnFieldStream(entityClass)
            .map(this::getColumnNameFrom)
            .collect(Collectors.joining(COLUMN_DEFINITION_DELIMITER));
    }

    public Stream<Field> getColumnFieldStream(Class<?> entityClass) {
        return Arrays.stream(entityClass.getDeclaredFields())
            .filter(field -> !field.isAnnotationPresent(Transient.class))
            .sorted(Comparator.comparing(field -> field.isAnnotationPresent(Id.class) ? 0 : 1));
    }
}
