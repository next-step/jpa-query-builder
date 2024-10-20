package persistence.sql.ddl.query;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Transient;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;
import org.jetbrains.annotations.NotNull;
import persistence.sql.metadata.Identifier;
import persistence.sql.metadata.TableName;

public record CreateQuery(TableName tableName,
                          List<ColumnMeta> columns,
                          Identifier identifier) {

    public CreateQuery(Class<?> clazz) {
        this(
                new TableName(clazz),
                Arrays.stream(clazz.getDeclaredFields())
                        .filter(notIdField())
                        .filter(notTransientField())
                        .map(ColumnMeta::new)
                        .toList(),
                Identifier.from(clazz.getDeclaredFields())
        );
    }

    @NotNull
    private static Predicate<Field> notIdField() {
        return field -> !field.isAnnotationPresent(Id.class)
                && !field.isAnnotationPresent(GeneratedValue.class);
    }

    @NotNull
    private static Predicate<Field> notTransientField() {
        return field -> !field.isAnnotationPresent(Transient.class);
    }

}
