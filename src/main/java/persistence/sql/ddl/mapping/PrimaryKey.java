package persistence.sql.ddl.mapping;

import jakarta.persistence.GeneratedValue;
import persistence.sql.ddl.type.IdGenerationTypeReference;

public class PrimaryKey {

    private String name;
    private String generationType;

    public PrimaryKey(Table table, Class<?> clazz) {
        this.name = "";
        this.generationType = "";

        for (Column column : table.columns()) {
            setIdentityValue(clazz, column);
        }
    }

    private void setIdentityValue(Class<?> clazz, Column column) {
        if (column.isIdentity()) {
            this.name = column.getName();
            setGeneratedValue(clazz);
        }
    }

    private void setGeneratedValue(Class<?> clazz) {
        if (clazz.isAnnotationPresent(GeneratedValue.class)) {
            GeneratedValue annotation = clazz.getAnnotation(GeneratedValue.class);
            this.generationType = IdGenerationTypeReference.getSqlType(annotation.strategy());
        }
    }

    public String getName() {
        return name;
    }

    public String getGenerationType() {
        return generationType;
    }

}
