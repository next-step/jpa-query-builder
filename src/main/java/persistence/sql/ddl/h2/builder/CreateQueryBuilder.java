package persistence.sql.ddl.h2.builder;

import jakarta.persistence.Id;
import jakarta.persistence.Transient;
import persistence.sql.ddl.H2Columns;
import persistence.sql.ddl.QueryBuilder;
import persistence.sql.ddl.h2.meta.TableName;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;

public class CreateQueryBuilder implements QueryBuilder {
    private final Class<?> clazz;

    public CreateQueryBuilder(Class<?> clazz) {
        this.clazz = clazz;
    }

    @Override
    public String generateSQL() {
        return String.format("""
                create table %s
                (
                %s
                %s
                );
                """, getTableName(), getColumns(), getPK());
    }

    private String getTableName() {
        return new TableName(clazz).getTableName();
    }

    private String getPK() {
        Field[] declaredFields = clazz.getDeclaredFields();
        Field pkField = Arrays.stream(declaredFields)
                .filter(field -> field.isAnnotationPresent(Id.class))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("PK must exists"));
        return String.format("    primary key (%s)", pkField.getName());
    }

    private String getColumns() {
        List<Field> fields = Arrays.stream(clazz.getDeclaredFields())
                .filter(field -> !field.isAnnotationPresent(Transient.class))
                .toList();
        return new H2Columns(fields).generateSQL();
    }
}
