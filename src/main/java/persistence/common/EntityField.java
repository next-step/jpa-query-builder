package persistence.common;

import persistence.sql.Id;

import java.lang.reflect.Field;

public class EntityField {

    private String name;
    private String type;
    private boolean isPk;

    public EntityField(Field field) {
        if (field.getType().equals(Long.class)) {
            this.type = "BIGINT";
        } else if (field.getType().equals(Integer.class)) {
            this.type = "INT";
        } else if (field.getType().equals(String.class)) {
            this.type = "VARCHAR(50)";
        }

        this.name = field.getName();
        this.isPk = field.isAnnotationPresent(Id.class);
    }

    public String getCreateFieldQuery() {
        return name + " " + type + " ,\n";
    }

    public boolean isPk() {
        return isPk;
    }

    public String getName() {
        return name;
    }
}
