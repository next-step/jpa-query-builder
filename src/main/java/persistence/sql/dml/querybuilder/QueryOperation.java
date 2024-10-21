package persistence.sql.dml.querybuilder;


import java.util.List;

public enum QueryOperation {

    SELECT {
        @Override
        public String buildQuery(QueryBuilder builder) {
            StringBuilder query = new StringBuilder();
            List<String> columns = builder.getColumns();
            String columnsPart = columns.isEmpty() ? "*" : String.join(", ", columns);

            query.append(SELECT_SQL)
                .append(columnsPart)
                .append(FROM_SQL)
                .append(builder.getQueryBuilderTableName().getName())
                .append(new WhereClause(builder).getClause());

            return query.toString();
        }
    },

    DELETE {
        @Override
        public String buildQuery(QueryBuilder builder) {
            StringBuilder query = new StringBuilder();

            query
                .append(DELETE_QUERY_PREFIX)
                .append(builder.getQueryBuilderTableName().getName())
                .append(new WhereClause(builder).getClause());

            return query.toString();
        }
    },

    INSERT {
        @Override
        public String buildQuery(QueryBuilder builder) {
            new ValidateInsertQuery(builder);
            StringBuilder query = new StringBuilder();
            String columnsPartForInsert = String.join(", ", builder.getColumns());
            String valuesPart = String.join(", ", builder.getValues());

            query
                .append(INSERT_QUERY_PREFIX).append(builder.getQueryBuilderTableName().getName())
                .append(" (").append(columnsPartForInsert).append(")")
                .append(VALUES_SQL)
                .append("(").append(valuesPart).append(")");

            return query.toString();
        }
    },

    UPDATE {
        @Override
        public String buildQuery(QueryBuilder builder) {
            StringBuilder query = new StringBuilder();

            query
                .append(UPDATE_QUERY_PREFIX)
                .append(builder.getQueryBuilderTableName().getName())
                .append(SET_SQL)
                .append(new SetClause(builder).getClause())
                .append(new WhereClause(builder).getClause());

            return query.toString();
        }
    };

    private static final String DELETE_QUERY_PREFIX = "DELETE FROM ";
    private static final String INSERT_QUERY_PREFIX = "INSERT INTO ";
    private static final String UPDATE_QUERY_PREFIX = "UPDATE ";
    private static final String SELECT_SQL = "SELECT ";
    private static final String FROM_SQL = " FROM ";
    private static final String VALUES_SQL = " VALUES ";
    private static final String SET_SQL = " SET ";

    public abstract String buildQuery(QueryBuilder builder);
}
