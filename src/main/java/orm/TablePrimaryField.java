package orm;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

import java.lang.reflect.Field;

public class TablePrimaryField extends TableField {

    private final Id id;
    private final GeneratedValue generatedValue;

    public TablePrimaryField(Field field) {
        super(field);
        this.id = field.getAnnotation(Id.class);
        this.generatedValue = field.getAnnotation(GeneratedValue.class);
    }

    public Id getId() {
        return id;
    }

    public GeneratedValue getGeneratedValue() {
        return generatedValue;
    }
}
