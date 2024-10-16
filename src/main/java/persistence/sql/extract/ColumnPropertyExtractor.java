package persistence.sql.extract;

import static persistence.sql.extract.AnnotationPropertyExtractor.isNotBlank;
import static persistence.sql.extract.AnnotationPropertyExtractor.isPresent;

import jakarta.persistence.Column;
import java.lang.reflect.Field;

public class ColumnPropertyExtractor implements AnnotationPropertyExtractor {

    public static String getName(Field field) {
        if (isColumnNamePresent(field)) {
            return getColumnName(field);
        }
        return getFieldName(field);
    }

    private static boolean isColumnNamePresent(Field field) {
        return isPresent(field.getClass(), Column.class) && isNotBlank(getColumnName(field));
    }

    private static String getColumnName(Field field) {
        Column column = field.getAnnotation(Column.class);
        return column.name();
    }

    private static String getFieldName(Field field) {
        return field.getName();
    }

}
