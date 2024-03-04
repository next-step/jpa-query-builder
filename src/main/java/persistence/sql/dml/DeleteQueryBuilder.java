package persistence.sql.dml;

import persistence.sql.QueryBuilder;
import persistence.sql.metadata.Entity;
import persistence.sql.metadata.PrimaryKey;

public class DeleteQueryBuilder extends QueryBuilder {

    public static final String DELETE_TEMPLATE = "DELETE FROM %s";
    private final Entity entity;
    private final Object id;
    private final PrimaryKey primaryKey;

    private DeleteQueryBuilder(Entity entity, Object id, PrimaryKey primaryKey) {
        this.entity = entity;
        this.id = id;
        this.primaryKey = primaryKey;
    }

    public static DeleteQueryBuilder of(Class<?> clazz) {
        return new DeleteQueryBuilder(Entity.of(clazz), null, null);
    }

    public static DeleteQueryBuilder of(Class<?> clazz, Object id) {
        return new DeleteQueryBuilder(Entity.of(clazz), id, PrimaryKey.of(generatePrimaryKey(clazz)));
    }

    @Override
    public String build() {
        String whereClause = whereClause(primaryKey, id);
        return String.format(DELETE_TEMPLATE, whereClause.isBlank() ?
                entity.getName() : String.join(" ", entity.getName(), whereClause));
    }
}
