package persistence.sql.ddl.model;

import jakarta.persistence.Id;

import java.lang.reflect.Field;
import java.util.Arrays;

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

    public Column(String name, Type type, Condition condition) {
        this.name = name;
        this.type = type;
        this.condition = condition;
    }

    public static Column from(Field field) {
        var isPKColumn = Arrays.stream(field.getAnnotations())
                .anyMatch(annotation -> annotation.annotationType().equals(Id.class));

        if (isPKColumn) return PKColumn.from(field);
        var columnAnnotation = Arrays.stream(field.getAnnotations())
                .filter(annotation -> annotation.annotationType().equals(jakarta.persistence.Column.class))
                .map(annotation -> (jakarta.persistence.Column) annotation)
                .findFirst();

        if (columnAnnotation.isEmpty()) return null;
        var column = columnAnnotation.get();

        var name = column.name().isBlank() ? field.getName() : column.name();
        var type = Type.from(field.getType());
        var condition = Condition.from(column);

        return new Column(name, type, condition);
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
}
