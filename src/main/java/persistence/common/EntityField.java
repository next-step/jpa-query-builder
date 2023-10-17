package persistence.common;

import persistence.annotations.Column;
import persistence.annotations.GeneratedValue;
import persistence.annotations.GenerationType;
import persistence.annotations.Id;

import java.lang.reflect.Field;
import java.util.Optional;

public class EntityField {

    private String name;
    private String type;
    private boolean isPk;
    private GenerationType generationType;
    private boolean nullable;

    public EntityField(Field field) {
        if (field.getType().equals(Long.class)) {
            this.type = "BIGINT";
        } else if (field.getType().equals(Integer.class)) {
            this.type = "INT";
        } else if (field.getType().equals(String.class)) {
            this.type = "VARCHAR(50)";
        }

        this.name = Optional.ofNullable(field.getAnnotation(Column.class))
                .map(Column::name)
                .orElse("");
        if (this.name.equals("")) {
            this.name = field.getName();
        }
        this.nullable = Optional.ofNullable(field.getAnnotation(Column.class))
                .map(Column::nullable)
                .orElse(true);
        this.isPk = field.isAnnotationPresent(Id.class);
        this.generationType = Optional.ofNullable(field.getAnnotation(GeneratedValue.class))
                .map(GeneratedValue::strategy)
                .orElse(null);
    }

    public String getCreateFieldQuery() {
        StringBuilder sb = new StringBuilder();
        sb.append(name);
        sb.append(" ");
        sb.append(type);
        sb.append(" ");
        if (!nullable) {
            sb.append("NOT NULL");
        }
        if (generationType != null) {
            sb.append(generationType.getCreateQuery());
        }

        sb.append(",\n");
        return sb.toString();
    }

    public boolean isPk() {
        return isPk;
    }

    public String getName() {
        return name;
    }
}
