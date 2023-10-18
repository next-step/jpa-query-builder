package sources;

import exception.AnnotationException;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;

import java.lang.reflect.Field;

public class AnnotationBinder {

    public AnnotationBinder() {
    }

    public String entityBinder(Class<?> entityClass) {
        if (!entityClass.isAnnotationPresent(Entity.class) ) {
            throw new AnnotationException( "Type '" + entityClass.getName()
                    + "@Entity 가 아닙니다." );
        }

        return entityClass.getSimpleName();
    }

    public String entityIdBinder(Field field) {
        if(!field.isAnnotationPresent(Id.class)) {
            throw new AnnotationException( "Type '" + field.getName()
                    + "@id 가 아닙니다." );
        }
        return field.getName();
    }
}
