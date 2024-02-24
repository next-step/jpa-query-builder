package persistence.sql.dml;

import persistence.sql.dialect.Dialect;

public class QueryBuilder {
    private final EntityTableMeta entityTableMeta;
    private final EntityColumns entityColumns;
    private final Dialect dialect;

    public QueryBuilder(Class<?> clazz, final Dialect dialect) {
        this.entityTableMeta = EntityTableMeta.of(clazz);
        this.entityColumns = EntityColumns.of(clazz);
        this.dialect = dialect;
    }

    public String createInsertQuery(Object object) {
        return String.format("insert into %s (%s) values (%s)", this.entityTableMeta.name(), this.entityColumns.names(),
                entityColumns.insertValues(object, dialect));
    }

    public String createFindAllQuery() {
        return String.format("select %s from %s", this.entityColumns.names(), this.entityTableMeta.name());
    }

    public String createFindByIdQuery(Long id) {
        return String.format("%s where %s = %dL", createFindAllQuery(),
                this.entityColumns.primaryFieldName(), id);
    }

    public String createDeleteQuery(Object object) {
        return String.format("delete from %s where %s = %s", this.entityTableMeta.name(),
                this.entityColumns.primaryFieldName(),
                this.entityColumns.primaryFieldValue(object));
    }
}
