package persistence.meta.vo;

import java.util.Optional;

public class EntityField {
    private final String originalFieldName;
    private final Optional<ColumnAnnotationData> columnAnnotationData;
    private final Class<?> fieldType;

    public EntityField(String originalFieldName, Optional<ColumnAnnotationData> columnAnnotationData, Class<?> fieldType) {
        this.originalFieldName = originalFieldName;
        this.columnAnnotationData = columnAnnotationData;
        this.fieldType = fieldType;
    }

    public String getTableFieldName() {
        return columnAnnotationData.isEmpty() ? originalFieldName : columnAnnotationData.get().getTableFieldName();
    }

    public boolean isNullable() {
        return columnAnnotationData.isEmpty() ? true : columnAnnotationData.get().isNullable();
    }

    public Class<?> getFieldType() {
        return fieldType;
    }
}
