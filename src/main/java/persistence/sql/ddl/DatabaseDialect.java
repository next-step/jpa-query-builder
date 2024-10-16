package persistence.sql.ddl;

interface DatabaseDialect {
    String getColumnTypeDefinition(ColumnType type);
    String identityClause(boolean isIdentity);
    String nullableClause(boolean isNullable);
    String primaryKeyClause(boolean isPrimaryKey);
}
