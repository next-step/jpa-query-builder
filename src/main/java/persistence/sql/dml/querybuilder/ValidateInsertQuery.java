package persistence.sql.dml.querybuilder;

public class ValidateInsertQuery {

    public ValidateInsertQuery(QueryBuilder builder) {
        validateInsertQuery(builder);
    }

    private void validateInsertQuery(QueryBuilder builder) {
        if (
            builder.getColumns().isEmpty() ||
            builder.getValues().isEmpty() ||
            builder.getColumns().size() != builder.getValues().size()
        ) {
            throw new IllegalStateException(
                "Column and value count must match for INSERT.");
        }
    }

}
