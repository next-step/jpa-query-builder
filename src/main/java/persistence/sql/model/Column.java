package persistence.sql.model;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Objects;
import java.util.Optional;

public class Column {
    private String name;
    private Type type;
    private Condition condition;

    public String getName() {
        return name;
    }

    public Type getType() {
        return type;
    }

    public Condition getCondition() {
        return condition;
    }

    Column(String name, Type type, Condition condition) {
        this.name = name;
        this.type = type;
        this.condition = condition;
    }

    Column(String name, Type type) {
        this.name = name;
        this.type = type;
        this.condition = Condition.DEFAULT_CONDITION;
    }

    public String getDDLColumnQuery() {
        var sb = new StringBuilder()
                .append(getName())
                .append(" ")
                .append(getType().name());
        if (!condition.isNullable()) sb.append(" ").append("NOT NULL");
        if (condition.isUnique()) sb.append(" ").append("UNIQUE");
        return sb.toString();
    }

    public String getQueryValue(Object value) {
        if (Objects.isNull(value)) return null;
        if (type == Type.VARCHAR) {
            return String.format("'%s'", value);
        }
        return String.valueOf(value);
    }

    public boolean isPK() {
        return false;
    }

    protected static Optional<jakarta.persistence.Column> getColumnAnnotation(Field field) {
        return Arrays.stream(field.getAnnotations())
                .filter(annotation -> annotation.annotationType().equals(jakarta.persistence.Column.class))
                .map(annotation -> (jakarta.persistence.Column) annotation)
                .findFirst();
    }
}
