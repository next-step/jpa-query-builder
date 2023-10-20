package persistence.sql.ddl.usecase;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import persistence.sql.ddl.exception.CannotCreateTableException;
import persistence.sql.ddl.vo.TableName;

public class GetTableNameFromClassUseCase {
    public TableName execute(Class<?> cls) {
        if (!cls.isAnnotationPresent(Entity.class)) {
            throw new CannotCreateTableException("Class is not annotated with @Entity");
        }
        String tableName = cls.getSimpleName();
        if (cls.isAnnotationPresent(Table.class)) {
            Table annotation = cls.getAnnotation(Table.class);
            if (annotation.name() != null && !annotation.name().isEmpty()) {
                tableName = annotation.name();
            }
        }
        return TableName.of(tableName);
    }
}
