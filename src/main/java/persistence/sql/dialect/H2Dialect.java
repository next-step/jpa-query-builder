package persistence.sql.dialect;

import persistence.sql.dialect.constraint.strategy.ColumnConstraintStrategy;
import persistence.sql.dialect.constraint.strategy.constraint.GeneratedValueConstraint;
import persistence.sql.dialect.constraint.strategy.constraint.NotNullConstraint;
import persistence.sql.metadata.ColumnMetadata;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static java.util.Optional.ofNullable;

public class H2Dialect implements Dialect {

    public static final String TYPE_AND_CONSTRAINTS_DELIMITER = " ";
    public static final String COLUMN_DELIMITER = " ";

    private final List<ColumnConstraintStrategy> strategies = List.of(
            new NotNullConstraint(),
            new GeneratedValueConstraint()
    );

    private static final Map<Class<?>, String> COLUMN_TYPES = new HashMap<>();

    static {
        COLUMN_TYPES.put(Long.class, "BIGINT");
        COLUMN_TYPES.put(String.class, "VARCHAR");
        COLUMN_TYPES.put(Integer.class, "INTEGER");
    }

    @Override
    public String build(ColumnMetadata column) {
        String constraints = strategies.stream()
                .map(strategy -> strategy.generateConstraints(column.getAnnotations()))
                .filter(constraint -> !constraint.trim().isEmpty())
                .collect(Collectors.joining(COLUMN_DELIMITER));

        return constraints.isBlank() ?
                generateColumnType(column.getType()) : String.join(TYPE_AND_CONSTRAINTS_DELIMITER, generateColumnType(column.getType()), constraints);
    }

    private String generateColumnType(Class<?> clazz) {
        return ofNullable(COLUMN_TYPES.get(clazz))
                .orElseThrow(() -> new IllegalArgumentException("This type is not supported."));
    }
}
