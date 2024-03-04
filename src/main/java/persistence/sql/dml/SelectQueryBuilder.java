package persistence.sql.dml;

import persistence.sql.QueryBuilder;
import persistence.sql.metadata.Columns;
import persistence.sql.metadata.Entity;
import persistence.sql.metadata.PrimaryKey;

public class SelectQueryBuilder extends QueryBuilder {

    public static final String SELECT_TEMPLATE = "SELECT %s FROM %s";
    private final Entity entity;
    private final Columns columns;
    private final Object id;
    private final PrimaryKey primaryKey;

    private SelectQueryBuilder(Entity entity, Columns columns, Object id, PrimaryKey primaryKey) {
        this.entity = entity;
        this.columns = columns;
        this.id = id;
        this.primaryKey = primaryKey;
    }

    public static SelectQueryBuilder of(Class<?> clazz) {
        return new SelectQueryBuilder(Entity.of(clazz), Columns.of(createColumns(clazz, null)), null, null);
    }

    public static SelectQueryBuilder of(Class<?> clazz, Object id) {
        return new SelectQueryBuilder(Entity.of(clazz), Columns.of(createColumns(clazz, null)), id, PrimaryKey.of(generatePrimaryKey(clazz)));
    }

    @Override
    public String build() {
        String whereClause = whereClause(primaryKey, id);
        return String.format(SELECT_TEMPLATE, columnsClause(columns), whereClause.isBlank() ?
                entity.getName() : String.join(" ", entity.getName(), whereClause));
    }
}
