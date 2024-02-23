package persistence.sql.model;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import persistence.sql.constant.ColumnType;
import persistence.sql.model.contstraint.NullableConstraint;
import persistence.sql.model.contstraint.PrimaryKeyConstraint;
import persistence.sql.model.contstraint.SqlConstraint;

import java.lang.reflect.Field;

public class Column {

    private final String name;
    private final ColumnType type;
    private final SqlConstraint nullable;
    private final SqlConstraint primary;
    private final GenerationType generationType;

    private Column(String name, ColumnType type, SqlConstraint nullable, SqlConstraint primary, GenerationType generationType) {
        this.name = name;
        this.type = type;
        this.nullable = nullable;
        this.primary = primary;
        this.generationType = generationType;
    }

    public static Column create(Field field, ColumnType columnType) {
        String columnName = getColumnName(field);
        boolean nullable = getNullableStatus(field);
        boolean primary = field.isAnnotationPresent(Id.class);
        GenerationType generationType = getGenerationType(field);

        return new Column(columnName, columnType, new NullableConstraint(nullable), new PrimaryKeyConstraint(primary), generationType);
    }

    private static String getColumnName(Field field) {
        if (field.isAnnotationPresent(jakarta.persistence.Column.class) && !field.getDeclaredAnnotation(jakarta.persistence.Column.class).name().isEmpty()) {
            return field.getDeclaredAnnotation(jakarta.persistence.Column.class).name();
        }
        return field.getName();
    }

    private static GenerationType getGenerationType(Field field) {
        return field.isAnnotationPresent(GeneratedValue.class)
            ? field.getDeclaredAnnotation(GeneratedValue.class).strategy()
            : null;
    }

    private static Boolean getNullableStatus(Field field) {
        return field.isAnnotationPresent(jakarta.persistence.Column.class) && field.getDeclaredAnnotation(jakarta.persistence.Column.class).nullable();
    }

    public String getName() {
        return name;
    }

    public ColumnType getType() {
        return type;
    }

    public SqlConstraint getNullableConstraint() {
        return nullable;
    }

    public SqlConstraint getPrimaryKeyConstraint() {
        return primary;
    }

    public GenerationType getGenerationType() {
        return generationType;
    }

}
