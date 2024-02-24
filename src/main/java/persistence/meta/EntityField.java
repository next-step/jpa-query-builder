package persistence.meta;

public class EntityField {
    private final String originalFieldName;
    private final String tableFieldName;
    private final int fieldType;

    public EntityField(String originalFieldName, String tableFieldName, int fieldType) {
        this.originalFieldName = originalFieldName;
        this.tableFieldName = tableFieldName;
        this.fieldType = fieldType;
    }
}
