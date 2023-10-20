package persistence.sql.ddl;

import jakarta.persistence.Column;
import jakarta.persistence.Id;
import java.lang.reflect.Field;
import java.util.Objects;
import java.util.Optional;

public class MyField {

    private final String name;
    private final String type;
    private final boolean isPk;


    public MyField(final Field field) {
        this.name = Optional.ofNullable(field.getAnnotation(Column.class))
            .map(Column::name)
            .filter(String::isEmpty)
            .map(emptyColumnName -> field.getName())
            .orElse(field.getName());
        this.type = getFieldType(field);
        this.isPk = field.isAnnotationPresent(Id.class);
    }

    private String getFieldType(final Field field) {
        final Class<?> fieldType = field.getType();

        return switch (fieldType.getSimpleName()) {
            case "Long" -> "BIGINT";
            case "String" -> "VARCHAR";
            case "Integer" -> "INT";
            default -> throw new IllegalStateException("Unexpected value: " + fieldType.getSimpleName());
        };
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public boolean isPk() {
        return isPk;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        MyField myField = (MyField) o;
        return isPk == myField.isPk && Objects.equals(name, myField.name) && Objects.equals(type, myField.type);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, type, isPk);
    }
}
