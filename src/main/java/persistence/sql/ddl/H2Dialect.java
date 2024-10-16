package persistence.sql.ddl;

class H2Dialect implements DatabaseDialect {
    private static final int VARCHAR_DEFAULT_LENGTH = 255;

    @Override
    public String getColumnTypeDefinition(ColumnType type) {
        return switch (type) {
            case BIGINT -> "BIGINT";
            case INTEGER -> "INTEGER";
            case VARCHAR -> "VARCHAR(" + VARCHAR_DEFAULT_LENGTH + ")";
            default -> throw new IllegalArgumentException("Unsupported column type: " + type);
        };
    }

    @Override
    public String identityClause(boolean isIdentity) {
        return isIdentity ? " AUTO_INCREMENT" : "";
    }

    @Override
    public String nullableClause(boolean isNullable) {
        return isNullable ? "" : " NOT NULL";
    }

    @Override
    public String primaryKeyClause(boolean isPrimaryKey) {
        return isPrimaryKey ? " PRIMARY KEY" : "";
    }
}
