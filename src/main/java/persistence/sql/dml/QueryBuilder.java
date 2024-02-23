package persistence.sql.dml;

import persistence.sql.dml.keygenerator.KeyGenerator;

public class QueryBuilder {
    private EntityTableMeta entityTableMeta;
    private EntityColumns entityColumns;

    public QueryBuilder(Object object, KeyGenerator keyGenerator) {
        this.entityTableMeta = EntityTableMeta.of(object.getClass());
        this.entityColumns = EntityColumns.of(object, keyGenerator);
    }

    public String createInsertQuery() {
        return String.format("insert into %s (%s) values (%s)", this.entityTableMeta.name(), this.entityColumns.names(),
                entityColumns.insertValues());
    }

    public String createFindAllQuery() {
        return String.format("select %s from %s", this.entityColumns.names(), this.entityTableMeta.name());
    }

    public String createFindByIdQuery() {
        return String.format("%s where %s = %s", createFindAllQuery(),
                this.entityColumns.primaryFieldName(),
                this.entityColumns.primaryFieldValue());
    }

    public String createDeleteQuery() {
        return String.format("delete from %s where %s = %s", this.entityTableMeta.name(),
                this.entityColumns.primaryFieldName(),
                this.entityColumns.primaryFieldValue());
    }
}
