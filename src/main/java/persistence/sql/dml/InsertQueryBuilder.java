package persistence.sql.dml;

import persistence.sql.dialect.Dialect;
import persistence.sql.metadata.ColumnMetadata;
import persistence.sql.metadata.ColumnsMetadata;
import persistence.sql.metadata.EntityMetadata;

import java.util.stream.Collectors;

public class InsertQueryBuilder {

    public static final String INSERT_TEMPLATE = "INSERT INTO %s (%s) VALUES (%s)";
    public static final String DELIMITER = ", ";
    private final Dialect dialect;
    private final EntityMetadata entity;

    private InsertQueryBuilder(Dialect dialect, EntityMetadata entity) {
        this.dialect = dialect;
        this.entity = entity;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private Dialect dialect;
        private EntityMetadata entity;

        private Builder() {
        }

        public Builder dialect(Dialect dialect) {
            this.dialect = dialect;
            return this;
        }

        public Builder entity(Object object) {
            this.entity = EntityMetadata.of(object.getClass(), object);
            return this;
        }

        public InsertQueryBuilder build() {
            return new InsertQueryBuilder(dialect, entity);
        }
    }

    private String columnsClause(ColumnsMetadata columns) {
        return columns.getColumns().stream()
                .map(ColumnMetadata::getName)
                .collect(Collectors.joining(DELIMITER));
    }

    private String valueClause() {
        return entity.getColumns().getColumns().stream()
                .map(column -> entity.getPrimaryKey().getName().equals(column.getName()) ? "default" : generateColumnValue(column.getValue()))
                .collect(Collectors.joining(DELIMITER));
    }

    private String generateColumnValue(Object object) {
        if (object instanceof String) {
            return String.format("'%s'", object);
        } else {
            return String.valueOf(object);
        }
    }

    public String generateQuery() {
        return String.format(INSERT_TEMPLATE, entity.getName(), columnsClause(entity.getColumns()), valueClause());
    }
}
