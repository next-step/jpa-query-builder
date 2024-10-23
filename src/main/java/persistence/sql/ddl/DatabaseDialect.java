package persistence.sql.ddl;

public interface DatabaseDialect {
    String getColumnTypeDefinition(ColumnType type);
    String identityClause(boolean isIdentity);
    String nullableClause(boolean isNullable);
    String primaryKeyClause(boolean isPrimaryKey);
}
