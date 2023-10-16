package persistence.sql.common;

import jakarta.persistence.Id;
import jakarta.persistence.Transient;

import java.lang.reflect.Field;
import java.util.Arrays;

class Column {
    private final ColumnName name;
    private final ColumnType type;
    private final Constraint constraints;
    private final boolean isPrimaryKey;

    private Column(Field field) {
        this.name = ColumnName.of(field);
        this.type = ColumnType.of(field.getType());
        this.isPrimaryKey = confirmPrimaryKey(field);
        this.constraints = Constraint.of(field);
    }

    public static Column of(Field field) {
        if(isTransient(field)) {
            return null;
        }

        return new Column(field);
    }

    public static Column[] of(Field[] fields) {
        return Arrays.stream(fields)
                .filter(field -> !field.isAnnotationPresent(Transient.class))
                .map(Column::of)
                .toArray(Column[]::new);
    }

    /**
     * @Id로 설정된 필드를 Primary Key로 설정합니다.
     */
    private boolean confirmPrimaryKey(Field field) {
        return field.isAnnotationPresent(Id.class);
    }

    /**
     * @Transient가 설정되어 있는지 확인합니다.
     */
    private static boolean isTransient(Field field) {
        return field.isAnnotationPresent(Transient.class);
    }

    public String getName() {
        return name.value();
    }

    public String getType() {
        return type.getType();
    }

    public boolean isPrimaryKey() {
        return isPrimaryKey;
    }

    public Constraint getConstraints() {
        return constraints;
    }
}
