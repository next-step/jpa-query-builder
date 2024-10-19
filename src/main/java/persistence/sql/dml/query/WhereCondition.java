package persistence.sql.dml.query;

public record WhereCondition(String columnName,
                             String operator,
                             Object value) {

}
