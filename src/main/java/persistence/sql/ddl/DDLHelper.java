package persistence.sql.ddl;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import persistence.sql.model.ColumnFactory;

import java.util.Arrays;
import java.util.Optional;
import java.util.stream.Collectors;


public class DDLHelper {
    public String getCreateQuery(Class<?> clazz) {
        checkEntity(clazz);
        var tableName = getTableName(clazz);

        String columnInfo = Arrays.stream(clazz.getDeclaredFields())
                .map(ColumnFactory::createColumn)
                .filter(Optional::isPresent)
                .map(column -> column.get().getDDLColumnQuery())
                .collect(Collectors.joining(", "));

        return "create table " + tableName + "(" + columnInfo + ");";
    }

    public String getDropQuery(Class<?> clazz) {
        checkEntity(clazz);
        var tableName = getTableName(clazz);

        return "drop table " + tableName + ";";
    }

    private void checkEntity(Class<?> clazz) {
        var annotations = clazz.getAnnotations();
        Arrays.stream(annotations)
                .filter(annotation -> annotation.annotationType().equals(Entity.class))
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);
    }

    private String getTableName(Class<?> clazz) {
        var annotations = clazz.getAnnotations();
        return Arrays.stream(annotations)
                .filter(annotation -> annotation.annotationType().equals(Table.class))
                .map(table -> ((Table) table).name())
                .findFirst()
                .orElse(clazz.getSimpleName());
    }
}
