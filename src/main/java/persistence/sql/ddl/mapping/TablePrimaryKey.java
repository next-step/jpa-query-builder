package persistence.sql.ddl.mapping;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import persistence.sql.ddl.type.TablePrimaryKeyGenerateType;

import java.lang.reflect.Field;
import java.util.Arrays;

public class TablePrimaryKey {

    private final TableColumn column;
    private final TablePrimaryKeyGenerateType generateType;

    public static TablePrimaryKey from(Field[] fields) {
        Field keyField = Arrays.stream(fields)
                .filter(field -> field.isAnnotationPresent(Id.class))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Primary key not exist."));

        return new TablePrimaryKey(new TableColumn(keyField), generateType(keyField));
    }

    private TablePrimaryKey(TableColumn column, TablePrimaryKeyGenerateType generateType) {
        this.column = column;
        this.generateType = generateType;
    }

    private static TablePrimaryKeyGenerateType generateType(Field keyField) {
        if (keyField.isAnnotationPresent(GeneratedValue.class)) {
            GeneratedValue annotation = keyField.getAnnotation(GeneratedValue.class);
            return TablePrimaryKeyGenerateType.lookup(annotation.strategy());
        }
        return TablePrimaryKeyGenerateType.IDENTITY;
    }

    public TableColumn column() {
        return column;
    }

    public String columnName() {
        return column.name();
    }

    public TablePrimaryKeyGenerateType generateType() {
        return generateType;
    }

}
