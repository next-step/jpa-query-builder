package persistence.sql.ddl.query;

import static persistence.validator.AnnotationValidator.isNotPresent;

import jakarta.persistence.Column;
import java.lang.reflect.Field;
import persistence.sql.ddl.type.ColumnType;
import persistence.sql.metadata.ColumnName;

public record CreateQueryColumn(int type,
                                String name,
                                int length,
                                boolean nullable) {

    private static final int DEFAULT_LENGTH = 255;

    public CreateQueryColumn(Field field) {
        this(
          ColumnType.getSqlType(field.getType()),
          new ColumnName(field).value(),
          getLength(field),
          getNullable(field)
        );
    }

    private static int getLength(Field field) {
        if (ColumnType.isNotVarcharType(field.getType())) {
            return 0;
        }

        if (isNotPresent(field, Column.class)) {
            return DEFAULT_LENGTH;
        }

        Column annotation = field.getAnnotation(Column.class);
        return annotation.length();
    }

    private static boolean getNullable(Field field) {
        if (isNotPresent(field, Column.class)) {
            return true;
        }
        Column annotation = field.getAnnotation(Column.class);
        return annotation.nullable();
    }

    public boolean notNull() {
        return !nullable;
    }

}
