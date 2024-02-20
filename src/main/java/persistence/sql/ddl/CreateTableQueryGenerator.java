package persistence.sql.ddl;

import jakarta.persistence.*;
import persistence.sql.ddl.TypeMapper.H2TypeMapper;
import persistence.sql.ddl.TypeMapper.TypeMapper;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class CreateTableQueryGenerator {
    private final TypeMapper typeMapper;
    CreateTableQueryGenerator(TypeMapper typeMapper) {
        this.typeMapper = typeMapper;
    }
    public String generateCreateQuery(final Class<?> entityClazz) {
        if (!entityClazz.isAnnotationPresent(Entity.class)) {
            throw new AnnotationMissingException("Entity 어노테이션이 없습니다.");
        }
        final String tableName = getTableName(entityClazz);

        List<Field> fields = Arrays.stream(entityClazz.getDeclaredFields())
                .filter(field -> !field.getName().equals("this$0"))
                .filter(field -> !field.isAnnotationPresent(Transient.class))
                .collect(Collectors.toList());

        String columnDefinitions = fields.stream().map(this::getColumnDefinition)
                .collect(Collectors.joining(", "));

        Field primaryKeyField = fields.stream()
                .filter(field -> field.isAnnotationPresent(Id.class))
                .findFirst()
                .orElseThrow(IdAnnotationMissingException::new);

        String primaryKeyDefinition = "PRIMARY KEY (" + primaryKeyField.getName() + ")";

        return String.format("CREATE TABLE %s (%s, %s)", tableName, columnDefinitions, primaryKeyDefinition);
    }

    private String getColumnDefinition(Field field) {
        StringBuilder sb = new StringBuilder();
        String columnName = field.getName();
        String columnType = typeMapper.map(field.getType());

        Column column = field.getAnnotation(Column.class);
        if(column != null) {
            if(!column.name().isEmpty()){
                columnName = column.name();
            }

            if(!column.nullable()){
                columnType += " NOT NULL";
            }
        }

        sb.append(columnName);
        sb.append(" ");
        sb.append(columnType);

        if (field.isAnnotationPresent(GeneratedValue.class)) {
           sb.append(" auto_increment");
        }

        return sb.toString();
    }

    private String getTableName(Class<?> entityClazz) {
        Table table = entityClazz.getAnnotation(Table.class);
        if (table == null) {
            return entityClazz.getSimpleName().toLowerCase();
        }
        return table.name();
    }
}
