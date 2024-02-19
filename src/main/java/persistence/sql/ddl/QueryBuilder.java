package persistence.sql.ddl;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Locale;

public class QueryBuilder {

    public String createDdl(Class<?> clazz) {
        if (!clazz.isAnnotationPresent(Entity.class)) {
            throw new IllegalArgumentException("[INFO] No @Entity annotation");
        }

        if (clazz.getDeclaredConstructors().length == 0) {
            throw new IllegalArgumentException("[INFO] No constructors");
        }

        Field idField = Arrays.stream(clazz.getDeclaredFields())
                .filter(field -> field.isAnnotationPresent(Id.class))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("[INFO] No @Id annotation"));

        PkColumn pkColumn = new PkColumn(idField);

        StringBuilder sb = new StringBuilder();
        String tableName = clazz.getSimpleName().toLowerCase(Locale.ROOT);
        sb.append("create table ").append(tableName).append(" (");

        sb.append(pkColumn.getDefinition()).append(pkColumn.getGenerationTypeDefinition());

        Arrays.stream(clazz.getDeclaredFields())
                .filter(field -> !field.isAnnotationPresent(Id.class))
                .forEach(field -> {
                    JpaColumn jpaColumn = new JpaColumn(field);
                    sb.append(jpaColumn.getDefinition());
                });

        sb.append(pkColumn.getPkDefinition());
        sb.append(")");

        return sb.toString();
    }
}
