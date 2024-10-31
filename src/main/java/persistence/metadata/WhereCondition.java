package persistence.metadata;

public record WhereCondition(String columnName,
                             String operator,
                             Object value) {
}
