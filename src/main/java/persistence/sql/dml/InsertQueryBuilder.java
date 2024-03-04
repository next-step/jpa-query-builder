package persistence.sql.dml;

import persistence.sql.QueryBuilder;
import persistence.sql.core.Columns;
import persistence.sql.core.Entity;
import persistence.sql.core.PrimaryKey;

import java.util.stream.Collectors;

public class InsertQueryBuilder extends QueryBuilder {

    public static final String INSERT_TEMPLATE = "INSERT INTO %s (%s) VALUES (%s)";
    public static final String DELIMITER = ", ";
    private final Entity entity;
    private final Columns columns;
    private final PrimaryKey primaryKey;

    private InsertQueryBuilder(Entity entity, Columns columns, PrimaryKey primaryKey) {
        this.entity = entity;
        this.columns = columns;
        this.primaryKey = primaryKey;
    }

    public static InsertQueryBuilder of(Object entity) {
        Class<?> clazz = entity.getClass();
        return new InsertQueryBuilder(Entity.of(clazz), Columns.of(createColumns(clazz, entity)), PrimaryKey.of(generatePrimaryKey(clazz)));
    }

    private String valueClause() {
        return columns.getColumns().stream()
                .map(column -> this.primaryKey.getName().equals(column.getName()) ? "default" : generateColumnValue(column.getValue()))
                .collect(Collectors.joining(DELIMITER));
    }

    private String generateColumnValue(Object object) {
        if (object instanceof String) {
            return String.format("'%s'", object);
        } else {
            return String.valueOf(object);
        }
    }

    @Override
    public String build() {
        return String.format(INSERT_TEMPLATE, entity.getName(), columnsClause(columns), valueClause());
    }
}
