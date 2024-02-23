package persistence.sql.dml;

import jakarta.persistence.Entity;
import persistence.sql.QueryBuilder;
import persistence.sql.ddl.domain.Column;
import persistence.sql.ddl.domain.Columns;
import persistence.sql.ddl.domain.Table;
import persistence.sql.dml.domain.Value;
import persistence.sql.dml.domain.Values;

import java.util.Arrays;
import java.util.stream.Collectors;

public class InsertQueryBuilder implements QueryBuilder {

    private static final String INSERT_QUERY = "INSERT INTO %s (%s) VALUES (%s);";

    private final Columns columns;
    private final Table table;
    private final Values values;

    public InsertQueryBuilder(Object object) {
        if (!object.getClass().isAnnotationPresent(Entity.class)) {
            throw new IllegalArgumentException("Does not have an @Entity annotation.");
        }

        Class<?> clazz = object.getClass();
        this.columns = new Columns(Arrays.stream(clazz.getDeclaredFields())
                .map(field -> new Column(field, TYPE_MAPPER, CONSTRAINT_MAPPER)).collect(Collectors.toList()));
        this.table = new Table(clazz);
        this.values = new Values(Arrays.stream(clazz.getDeclaredFields())
                .map(field -> new Value(new Column(field, TYPE_MAPPER, CONSTRAINT_MAPPER), field, object))
                .collect(Collectors.toList()));
    }

    @Override
    public String build() {
        return String.format(
                INSERT_QUERY,
                table.getName(),
                columns.getInsertColumns(),
                values.getInsertValues()
        );
    }
}
