package persistence.sql.table;


import jakarta.persistence.Transient;
import persistence.metadata.ColumnName;
import persistence.metadata.TableName;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.List;

import static common.AnnotationValidation.isNotPresent;

public record TableMetadataExtractor(TableName tableName, List<ColumnName> columnNames) {
    public TableMetadataExtractor(Class<?> clazz) {
        this( new TableName(clazz), Arrays.stream(clazz.getDeclaredFields()).filter(field -> isNotPresent(field, Transient.class)).map(ColumnName::new).toList());
    }

}
