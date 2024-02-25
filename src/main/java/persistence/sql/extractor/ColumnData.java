package persistence.sql.extractor;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import persistence.sql.ddl.KeyType;
import persistence.sql.extractor.exception.GenerationTypeMissingException;

public class ColumnData {
    private final String name;
    private final DataType type;
    private Object value;
    private KeyType keyType;
    private GenerationType generationType;
    private boolean isNullable;

    public ColumnData(String name, DataType type, boolean isNullable) {
        this.name = name;
        this.type = type;
        this.isNullable = isNullable;
    }

    public boolean hasGenerationType() {
        return generationType != null;
    }

    public boolean isPrimaryKey() {
        return keyType == KeyType.PRIMARY;
    }

    public boolean isNullable() {
        return isNullable;
    }
    public boolean isNotNullable() { return !isNullable; }

    public void setNullable(boolean nullable) {
        isNullable = nullable;
    }

    public String getName() {
        return name;
    }

    public DataType getType() {
        return type;
    }

    public Object getValue() {
        return value;
    }

    public KeyType getKeyType() {
        return keyType;
    }

    public void setValue(Object value) {
        this.value = value;
    }

    public void setKeyType(KeyType keyType) {
        this.keyType = keyType;
    }

    public GenerationType getGenerationType() {
        if (generationType == null) {
            throw new GenerationTypeMissingException();
        }
        return generationType;
    }

    public void setGenerationType(GenerationType generationType) {
        this.generationType = generationType;
    }
}
