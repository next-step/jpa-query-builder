package persistence.sql.dml;

import persistence.sql.dml.keygenerator.KeyGenerator;

import java.lang.reflect.Field;

public class QueryBuilder {
    private EntityTableMeta entityTableMeta;
    private EntityColumns entityColumns;

    public QueryBuilder(Object object) {
        this.entityTableMeta = EntityTableMeta.of(object.getClass());
        this.entityColumns = EntityColumns.of(object);
    }

    public String createInsertQuery(final KeyGenerator keyGenerator) {
        return String.format("insert into %s (%s) values (%s)", this.entityTableMeta.name(), this.entityColumns.names(),
                entityColumns.insertValues(keyGenerator));
    }

    public String createFindAllQuery() {
        return String.format("select %s from %s", this.entityColumns.names(), this.entityTableMeta.name());
    }

    public String createFindByIdQuery() {
        return String.format("%s where %s = %s", createFindAllQuery(),
                this.entityColumns.primaryField().getName(),
                entityColumns.values());
    }

    public String createDeleteQuery() {
        final Field primaryField = this.entityColumns.primaryField();

        return String.format("delete from %s where %s = %s", this.entityTableMeta.name(),
                primaryField.getName(),
                entityColumns.values());
    }
}
