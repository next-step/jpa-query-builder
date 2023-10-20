package persistence.sql.ddl;

import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class MyEntity {

    private final String tableName;
    private final List<MyField> myFields;

    public MyEntity(Class<?> clazz) {
        this.tableName = Optional.ofNullable(clazz.getAnnotation(Table.class))
            .map(Table::name)
            .orElse(clazz.getSimpleName());

        this.myFields = Arrays.stream(clazz.getDeclaredFields())
            .filter(field -> !field.isAnnotationPresent(Transient.class))
            .map(MyField::new)
            .collect(Collectors.toList());
    }

    public String getTableName() {
        return tableName;
    }

    public List<MyField> getMyFields() {
        return myFields;
    }
}
