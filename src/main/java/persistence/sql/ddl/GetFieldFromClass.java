package persistence.sql.ddl;

import jakarta.persistence.Id;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.stream.Collectors;
import persistence.sql.ddl.vo.DatabaseField;
import persistence.sql.ddl.vo.DatabaseFields;
import persistence.sql.ddl.vo.type.TypeConverter;

public class GetFieldFromClass {
    public DatabaseFields execute(Class<?> cls) {
        Field[] declaredFields = cls.getDeclaredFields();
        return DatabaseFields.of(Arrays.stream(declaredFields).map(
            it -> {
                String name = it.getName();
                boolean isPrimary = it.isAnnotationPresent(Id.class);
                return new DatabaseField(name, TypeConverter.convert(it), isPrimary);
            }
        ).collect(Collectors.toList()));
    }
}
