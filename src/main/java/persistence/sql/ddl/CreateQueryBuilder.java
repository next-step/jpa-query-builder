package persistence.sql.ddl;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static java.lang.String.format;

public class CreateQueryBuilder{
    private final static String CREATE_TABLE_COMMAND = "CREATE TABLE %s";

    public CreateQueryBuilder() {
    }

    public String bulidQuery(Class<?> clazz) {
        checkIsEntity(clazz);

        return format(CREATE_TABLE_COMMAND, findTableName(clazz)) +
                "(" +
                buildColumnList(convertClassToColumnList(clazz)) +
                ");";
    }

    private void checkIsEntity(Class<?> clazz) {
        if (!clazz.isAnnotationPresent(Entity.class)) {
            throw new IllegalArgumentException("Entity 클래스가 아닙니다.");
        }
    }

    private String findTableName(Class<?> clazz) {
        if(clazz.isAnnotationPresent(Table.class)) {
            return clazz.getDeclaredAnnotation(Table.class).name();
        }

        return clazz.getSimpleName();
    }

    private String buildColumnList(List<Column> columns) {
        return columns.stream()
                .filter(x -> !x.isTransient())
                .map(new ColumnBuilder()::buildColumnToCreate)
                .collect(Collectors.joining(", ")).toString();
    }

    private List<Column> convertClassToColumnList(Class<?> clazz) {
        return Arrays.stream(clazz.getDeclaredFields())
                .map(Column::new)
                .collect(Collectors.toList());
    }
}
