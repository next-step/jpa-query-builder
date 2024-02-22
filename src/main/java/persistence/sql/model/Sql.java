package persistence.sql.model;

import persistence.sql.dialect.Dialect;

import java.util.List;

public enum Sql {

    CREATE,
    DROP,
    TABLE;

    public static class Builder {

        private final StringBuilder queryBuilder;
        private final Dialect dialect;

        public Builder(Dialect dialect) {
            this.dialect = dialect;
            this.queryBuilder = new StringBuilder();
        }

        public Builder create() {
            queryBuilder.append(CREATE.name());
            return this;
        }

        public Builder drop() {
            queryBuilder.append(DROP.name());
            return this;
        }

        public Builder leftParenthesis() {
            queryBuilder.append('(');
            return this;
        }

        public Builder rightParenthesis() {
            queryBuilder.append(')');
            return this;
        }

        public Builder and() {
            queryBuilder.append(' ');
            return this;
        }

        public Builder table(String name) {
            queryBuilder.append(TABLE.name())
                    .append(' ')
                    .append(name);
            return this;
        }

        public Builder columns(List<Column> columns) {
            columns.forEach(this::column);
            removeLastChar();
            return this;
        }

        private void column(Column column) {
            String name = column.name();
            queryBuilder.append(name)
                    .append(' ');

            SqlType sqlType = column.type();
            String dbType = dialect.get(sqlType);
            queryBuilder.append(dbType);

            List<SqlConstraint> sqlConstraints = column.constraints();
            sqlConstraints.forEach(constraint -> {
                String query = constraint.query();
                queryBuilder.append(' ')
                        .append(query);
            });

            queryBuilder.append(',');
        }

        private void removeLastChar() {
            int length = queryBuilder.length();
            queryBuilder.delete(length - 1, length);
        }

        public String build() {
            queryBuilder.append(';');
            return queryBuilder.toString();
        }
    }
}
