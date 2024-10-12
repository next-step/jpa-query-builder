package persistence.sql.ddl;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;

public class PrimaryKey {

    private final FieldInfo fieldInfo;
    private GenerationType generationType;

    public PrimaryKey(FieldInfo fieldInfo, GeneratedValue generatedValue) {
        this.fieldInfo = fieldInfo;
        this.generationType = GenerationType.AUTO;
        if (generatedValue != null) {
            this.generationType = generatedValue.strategy();
        }
    }

    public String name() {
        return fieldInfo.name();
    }

    public String type() {
        return fieldInfo.type();
    }

    public GenerationType generationType() {
        return generationType;
    }
}
