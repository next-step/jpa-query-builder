package persistence.sql.ddl.mapping;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import persistence.sql.ddl.exception.NotExistException;

import java.lang.reflect.Field;
import java.util.Arrays;

public class TablePrimaryKey {

    private final TableColumn column;
    private final GenerationType generationType;

    public static TablePrimaryKey from(Field[] fields) {
        Field keyField = Arrays.stream(fields)
                .filter(field -> field.isAnnotationPresent(Id.class))
                .findFirst()
                .orElseThrow(() -> new NotExistException("primary key."));

        return new TablePrimaryKey(new TableColumn(keyField), generationType(keyField));
    }

    private TablePrimaryKey(TableColumn column, GenerationType generationType) {
        this.column = column;
        this.generationType = generationType;
    }

    private static GenerationType generationType(Field field) {
        if (field.isAnnotationPresent(GeneratedValue.class)) {
            GeneratedValue annotation = field.getAnnotation(GeneratedValue.class);
            return annotation.strategy();
        }
        return GenerationType.IDENTITY;
    }

    public TableColumn column() {
        return column;
    }

    public String columnName() {
        return column.name();
    }

    public GenerationType generationType() {
        return generationType;
    }

}
