package persistence.sql.dml;

import persistence.sql.dml.keygenerator.KeyGenerator;

import java.lang.reflect.Field;

import static persistence.sql.dml.parser.ValueParser.valueParse;

public class QueryBuilder {
    private Object object;
    private EntityTableMeta entityTableMeta;
    private EntityColumns entityColumns;

    public QueryBuilder(Object object) {
        this.object = object;
        this.entityTableMeta = EntityTableMeta.of(object.getClass());
        this.entityColumns = EntityColumns.of(object);
    }

    public String createInsertQuery(final KeyGenerator keyGenerator) {
        return String.format("insert into %s (%s) values (%s)", this.entityTableMeta.name(), this.entityColumns.names(),
                entityColumns.values(keyGenerator));
    }

    public String createFindAllQuery() {
        return String.format("select %s from %s", this.entityColumns.names(), this.entityTableMeta.name());
    }

    public String createFindByIdQuery() {
        final Field primaryField = this.entityColumns.primaryField();

        return String.format("%s where %s = %s", createFindAllQuery(), primaryField.getName(),
                valueParse(primaryField, object));
    }

    public String createDeleteQuery() {
        final Field primaryField = this.entityColumns.primaryField();

        return String.format("delete from %s where %s = %s", this.entityTableMeta.name(), primaryField.getName(),
                valueParse(primaryField, object));
    }

}
