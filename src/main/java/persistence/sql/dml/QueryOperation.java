package persistence.sql.dml;


import java.util.List;

public enum QueryOperation {

    SELECT {
        @Override
        public String buildQuery(SimpleQueryBuilder builder, StringBuilder query) {
            List<String> columns = builder.getColumns();
            String columnsPart = columns.isEmpty() ? "*" : String.join(", ", columns);

            query.append(SELECT_SQL)
                .append(columnsPart)
                .append(FROM_SQL)
                .append(builder.getTableName())
                .append(whereClause(builder));

            return query.toString();
        }
    },

    DELETE {
        @Override
        public String buildQuery(SimpleQueryBuilder builder, StringBuilder query) {
            query
                .append(DELETE_QUERY_PREFIX)
                .append(builder.getTableName());

            return query.toString();
        }
    },

    INSERT {
        @Override
        public String buildQuery(SimpleQueryBuilder builder, StringBuilder query) {
            validateInsertQuery(builder);
            String columnsPartForInsert = String.join(", ", builder.getColumns());
            String valuesPart = String.join(", ", builder.getValues());

            query
                .append(INSERT_QUERY_PREFIX).append(builder.getTableName())
                .append(" (").append(columnsPartForInsert).append(") ")
                .append(VALUES_SQL)
                .append("(").append(valuesPart).append(")");

            return query.toString();
        }
    };

    private static final String DELETE_QUERY_PREFIX = "DELETE FROM ";
    private static final String INSERT_QUERY_PREFIX = "INSERT INTO ";
    private static final String SELECT_SQL = "SELECT ";
    private static final String FROM_SQL = " FROM ";
    private static final String WHERE_SQL = " WHERE ";
    private static final String VALUES_SQL = " VALUES ";

    private static String whereClause(SimpleQueryBuilder builder) {
        StringBuilder query = new StringBuilder();
        if (!builder.getConditions().isEmpty()) {
            query.append(WHERE_SQL);
            String conditionsPart = String.join(" ", builder.getConditions());
            query.append(conditionsPart);
        }
        return query.toString();
    }

    private static void validateInsertQuery(SimpleQueryBuilder builder) {
        if (
            builder.getColumns().isEmpty() ||
                builder.getValues().isEmpty() ||
                builder.getColumns().size() != builder.getValues().size()
        ) {
            throw new IllegalStateException(
                "Column and value count must match for INSERT.");
        }
    }

    public abstract String buildQuery(SimpleQueryBuilder builder, StringBuilder query);
}
