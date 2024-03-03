package persistence.meta.vo;

public class EntityField {
    private final String originalFieldName;
    private final ColumnAnnotationData columnAnnotationData;
    private final Class<?> fieldType;

    public EntityField(String originalFieldName, ColumnAnnotationData columnAnnotationData, Class<?> fieldType) {
        this.originalFieldName = originalFieldName;
        this.columnAnnotationData = columnAnnotationData;
        this.fieldType = fieldType;
    }

    public String getOriginalFieldName() {
        return originalFieldName;
    }

    public String getTableFieldName() {
        return columnAnnotationData == null ? originalFieldName : columnAnnotationData.getTableFieldName();
    }

    public boolean isNullable() {
        return columnAnnotationData == null ? true : columnAnnotationData.isNullable();
    }

    public Class<?> getFieldType() {
        return fieldType;
    }
}
