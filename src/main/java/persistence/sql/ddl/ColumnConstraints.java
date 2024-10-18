package persistence.sql.ddl;

import jakarta.persistence.Column;
import jakarta.persistence.Id;
import java.lang.reflect.Field;

public class ColumnConstraints {

    private final Field field;

    public ColumnConstraints(Field field) {
        this.field = field;
    }

    public String getColumnConstraints() {
        StringBuilder columnConstraints = new StringBuilder();
        if (field.isAnnotationPresent(Id.class)) {
            columnConstraints
                .append(" ")
                .append(new IdColumnConstraint(field).getConstraint());
        }

        if (field.isAnnotationPresent(Column.class)) {
            columnConstraints
                .append(" ")
                .append(new ColumnConstraint(field).getConstraint());
        }

        return columnConstraints.toString();
    }

}
