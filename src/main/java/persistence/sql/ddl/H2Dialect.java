package persistence.sql.ddl;

import java.util.Map;
import java.util.Optional;

class H2Dialect implements DatabaseDialect {
    private static final Map<ColumnType, String> TYPE_DEFINITIONS = Map.of(
            ColumnType.BIGINT, "BIGINT",
            ColumnType.INTEGER, "INTEGER",
            ColumnType.VARCHAR, "VARCHAR(255)"
    );

    @Override
    public String getColumnTypeDefinition(final ColumnType type) {
        return Optional.ofNullable(TYPE_DEFINITIONS.get(type))
                .orElseThrow(() -> new IllegalArgumentException("Unsupported column type: " + type));
    }

    @Override
    public String identityClause(final boolean isIdentity) {
        return isIdentity ? " AUTO_INCREMENT" : "";
    }

    @Override
    public String nullableClause(final boolean isNullable) {
        return isNullable ? "" : " NOT NULL";
    }

    @Override
    public String primaryKeyClause(final boolean isPrimaryKey) {
        return isPrimaryKey ? " PRIMARY KEY" : "";
    }
}
