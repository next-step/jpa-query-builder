package sources;

import exception.AnnotationException;
import jakarta.persistence.*;

import java.lang.reflect.Field;

public class AnnotationBinder {

    public AnnotationBinder() {
    }

    public String entityBinder(Class<?> entityClass) {
        if (!entityClass.isAnnotationPresent(Entity.class) ) {
            throw new AnnotationException( "Type '" + entityClass.getName()
                    + "@Entity 가 아닙니다." );
        }
        //요구사항 3. @Table 어노테이션이 있을 경우에는 해당 네임을 테이블 이름으로 지정해준다.
        if (entityClass.isAnnotationPresent(Table.class) ) {
            Table table = entityClass.getDeclaredAnnotation(Table.class);
            return table.name();
        }

        return entityClass.getSimpleName();
    }

    public String entityIdBinder(Field field) {
        if(!field.isAnnotationPresent(Id.class)) {
            throw new AnnotationException( "Type '" + field.getName()
                    + "@id 가 아닙니다." );
        }
        // 요구사항2. generatedValue 어노테이션이 있을 경우에 처리한다.
        if(field.isAnnotationPresent(GeneratedValue.class)) {
            GeneratedValue declaredAnnotation = field.getDeclaredAnnotation(GeneratedValue.class);
            String generator = registerGenerators(declaredAnnotation.strategy());
            return field.getName() + generator;
            // not null auto_increment
        }
        return field.getName();
    }

    private String registerGenerators(GenerationType type) {
        switch (type) {
            case IDENTITY, AUTO:
                return " INT AUTO_INCREMENT PRIMARY KEY";
            case UUID:
                return "";
        }
        return null;
    }
}
