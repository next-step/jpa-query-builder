package persistence.sql.dml.statement;

import persistence.sql.dialect.ColumnType;
import persistence.sql.dml.clause.WherePredicate;
import persistence.sql.dml.clause.builder.WhereClauseBuilder;
import persistence.sql.schema.EntityClassMappingMeta;
import persistence.sql.schema.EntityObjectMappingMeta;

public class DeleteStatementBuilder {

    private final StringBuilder deleteStatementBuilder;
    private WhereClauseBuilder whereClauseBuilder;
    private static final String DELETE_FORMAT = "DELETE FROM %s";

    private static final String DELETE_WHERE_FORMAT = "%s %s";

    private DeleteStatementBuilder() {
        this.deleteStatementBuilder = new StringBuilder();
    }

    public static DeleteStatementBuilder builder() {
        return new DeleteStatementBuilder();
    }

    public DeleteStatementBuilder delete(Object object, ColumnType columnType) {
        final EntityObjectMappingMeta objectMappingMeta = EntityObjectMappingMeta.of(
            object,
            EntityClassMappingMeta.of(object.getClass(), columnType)
        );

        if (deleteStatementBuilder.length() > 0) {
            throw new RuntimeException("delete() method must be called only once");
        }

        deleteStatementBuilder.append(String.format(DELETE_FORMAT, objectMappingMeta.getTableName()));
        return this;
    }

    public DeleteStatementBuilder where(WherePredicate predicate) {
        this.whereClauseBuilder = WhereClauseBuilder.builder(predicate);
        return this;
    }

    public DeleteStatementBuilder and(WherePredicate predicate) {
        if (this.whereClauseBuilder == null) {
            throw new RuntimeException("where() method must be called");
        }

        this.whereClauseBuilder.and(predicate);
        return this;
    }

    public DeleteStatementBuilder or(WherePredicate predicate) {
        if (this.whereClauseBuilder == null) {
            throw new RuntimeException("where() method must be called");
        }

        this.whereClauseBuilder.or(predicate);
        return this;
    }

    public String build() {
        if (deleteStatementBuilder.length() == 0) {
            throw new RuntimeException("DeleteStatement must start with delete()");
        }

        if (this.whereClauseBuilder == null) {
            return deleteStatementBuilder.toString();
        }

        return String.format(DELETE_WHERE_FORMAT, deleteStatementBuilder, this.whereClauseBuilder.build());
    }
}
