package persistence.sql.loader;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import persistence.sql.ClassComponentType;
import persistence.sql.dto.javaclass.ClassName;

public class ClassNameLoader implements ClassComponentLoader<ClassName> {

    @Override
    public ClassName invoke(Class<?> clazz) {
        if (clazz.isAnnotationPresent(Entity.class)) {
            return new ClassName(clazz.getSimpleName(), clazz.getAnnotation(Table.class));
        }
        throw new IllegalStateException("엔티티로 지정된 클래스를 찾을 수 없습니다.");
    }

    @Override
    public ClassComponentType type() {
        return ClassComponentType.CLASS_NAME;
    }
}
