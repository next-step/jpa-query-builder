package persistence.sql.ddl;

import static persistence.sql.ddl.TypesMapper.getFieldType;

import jakarta.persistence.Column;
import jakarta.persistence.Id;
import java.lang.reflect.Field;
import java.util.Objects;
import java.util.Optional;

public class MyField {

    private final String name;
    private final Integer types;
    private final boolean isPk;

    public MyField(final Field field) {
        this.name = Optional.ofNullable(field.getAnnotation(Column.class))
            .map(Column::name)
            .filter(String::isEmpty)
            .map(emptyColumnName -> field.getName())
            .orElse(field.getName());
        this.types = getFieldType(field);
        this.isPk = field.isAnnotationPresent(Id.class);
    }

    public String getName() {
        return name;
    }

    public Integer getType() {
        return types;
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
        return isPk == myField.isPk && Objects.equals(name, myField.name) && Objects.equals(types, myField.types);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, types, isPk);
    }
}
