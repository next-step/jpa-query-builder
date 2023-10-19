package persistence.sql.ddl;

import jakarta.persistence.Entity;
import persistence.sql.ddl.exception.CannotCreateTableException;
import persistence.sql.ddl.vo.TableName;

public class GetTableNameFromClass {
    public TableName execute(Class<?> cls) {
        if (!cls.isAnnotationPresent(Entity.class)) {
            throw new CannotCreateTableException("Class is not annotated with @Entity");
        }
        return TableName.of(cls.getSimpleName());
    }
}
