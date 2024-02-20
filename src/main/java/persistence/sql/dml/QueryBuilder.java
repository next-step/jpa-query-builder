package persistence.sql.dml;

import jakarta.persistence.*;
import persistence.sql.dml.keygenerator.KeyGenerator;

import java.lang.reflect.Field;
import java.util.Arrays;

import static persistence.sql.dml.parser.ValueParser.valueParse;

public class QueryBuilder {

    public String createInsertQuery(Object object, final KeyGenerator keyGenerator) {
        EntityTableMeta entityTableMeta = EntityTableMeta.of(object.getClass());
        EntityColumns entityColumns = EntityColumns.of(object);

        return String.format("insert into %s (%s) values (%s)", entityTableMeta.name(), entityColumns.names(),
                entityColumns.values(keyGenerator));
    }

    public String createFindAllQuery(final Object object) {
        EntityTableMeta entityTableMeta = EntityTableMeta.of(object.getClass());
        EntityColumns entityColumns = EntityColumns.of(object);

        return String.format("select %s from %s", entityColumns.names(), entityTableMeta.name());
    }

    public String createFindByIdQuery(final Object object) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(createFindAllQuery(object));
        stringBuilder.append(" where ");

        final Field primaryField = getPrimaryField(object);
        stringBuilder.append(String.format("%s = %s", primaryField.getName(), valueParse(primaryField, object)));
        return stringBuilder.toString();
    }

    public String createDeleteQuery(final Object object) {
        EntityTableMeta entityTableMeta = EntityTableMeta.of(object.getClass());
        final Field primaryField = getPrimaryField(object);
        return String.format("delete from %s where %s = %s", entityTableMeta.name(), primaryField.getName(),
                valueParse(primaryField, object));
    }

    private Field getPrimaryField(final Object object) {
        return Arrays.stream(object.getClass().getDeclaredFields())
                .filter(f -> f.isAnnotationPresent(Id.class))
                .findFirst()
                .orElseThrow(IllegalStateException::new);
    }
}
