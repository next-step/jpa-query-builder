package orm;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import orm.settings.JpaSettings;

import java.lang.reflect.Field;

public class TablePrimaryField extends TableField {

    private final Id id;
    private final GeneratedValue generatedValue;

    public <T> TablePrimaryField(Field field, T entity, JpaSettings jpaSettings) {
        super(field, entity, jpaSettings);
        this.id = field.getAnnotation(Id.class);
        this.generatedValue = field.getAnnotation(GeneratedValue.class);
    }

    @Override
    public boolean isId() {
        return true;
    }

    public Id getId() {
        return id;
    }

    public GeneratedValue getGeneratedValue() {
        return generatedValue;
    }
}
