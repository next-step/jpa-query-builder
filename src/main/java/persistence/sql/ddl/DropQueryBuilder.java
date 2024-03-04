package persistence.sql.ddl;

import persistence.sql.QueryBuilder;
import persistence.sql.metadata.Entity;

public class DropQueryBuilder extends QueryBuilder {

    public static final String DROP_TABLE_TEMPLATE = "DROP TABLE %s";
    private final Entity entity;

    private DropQueryBuilder(Entity entity) {
        this.entity = entity;
    }

    public static DropQueryBuilder of(Class<?> clazz) {
        return new DropQueryBuilder(Entity.of(clazz));
    }

    @Override
    public String build() {
        return String.format(DROP_TABLE_TEMPLATE, entity.getName());
    }
}
