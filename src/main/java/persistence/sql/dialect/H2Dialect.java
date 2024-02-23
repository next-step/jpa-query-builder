package persistence.sql.dialect;

import persistence.sql.dialect.constraint.strategy.ColumnConstraintStrategy;
import persistence.sql.dialect.constraint.strategy.h2db.H2GeneratedValueConstraintStrategy;
import persistence.sql.dialect.constraint.strategy.h2db.H2NotNullConstraintStrategy;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static java.util.Optional.ofNullable;

public class H2Dialect implements Dialect {

    private static final H2Dialect INSTANCE = new H2Dialect();

    private final List<ColumnConstraintStrategy> strategies = List.of(
            new H2NotNullConstraintStrategy(),
            new H2GeneratedValueConstraintStrategy()
    );

    private static final Map<Class<?>, String> COLUMN_TYPES = new HashMap<>();

    static {
        COLUMN_TYPES.put(Long.class, "BIGINT");
        COLUMN_TYPES.put(String.class, "VARCHAR");
        COLUMN_TYPES.put(Integer.class, "INTEGER");
    }

    public static H2Dialect getInstance() {
        return INSTANCE;
    }

    @Override
    public String generateColumnSql(Field field) {
        String constraints = strategies.stream()
                .map(strategy -> strategy.generateConstraints(field))
                .filter(constraint -> !constraint.trim().isEmpty())
                .collect(Collectors.joining(" "));

        if (!constraints.isEmpty()) {
            return String.join(" ", generateColumnType(field.getType()), constraints);
        }

        return generateColumnType(field.getType());
    }

    private String generateColumnType(Class<?> type) {
        return ofNullable(COLUMN_TYPES.get(type))
                .orElseThrow(() -> new IllegalArgumentException("This type is not supported."));
    }
}
