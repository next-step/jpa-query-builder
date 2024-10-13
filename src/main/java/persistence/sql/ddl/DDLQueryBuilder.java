package persistence.sql.ddl;

import jakarta.persistence.*;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.stream.Collectors;


public class DDLQueryBuilder {

    private static final String CREATE_TABLE = "CREATE TABLE ";
    private static final String DROP_TABLE = "DROP TABLE ";

    public static String createTable(DDLQueryBuilder ddlQueryBuilder, Class<?> entityClass) {

        if (!entityClass.isAnnotationPresent(Entity.class)) {
            throw new IllegalArgumentException("This Class is not an Entity ");
        }

        String tableName = ddlQueryBuilder.getTableName(entityClass);

        String columns = Arrays.stream(entityClass.getDeclaredFields())
                .filter(field -> !field.isAnnotationPresent(Transient.class)).map(field -> {
                    StringBuilder sb = new StringBuilder();
                    String columnName = ddlQueryBuilder.getColumnName(field);
                    Class<?> type = field.getType();

                    sb.append(columnName).append(" ");
                    sb.append(H2DBDataType.castType(type));

                    extra(ddlQueryBuilder, field, sb);

                    return sb.toString();
                }).collect(Collectors.joining(", "));


        return CREATE_TABLE + tableName + " (" + columns + ");";
    }

    public static String dropTable(DDLQueryBuilder ddlQueryBuilder, Class<?> entityClass) {
        if (!entityClass.isAnnotationPresent(Entity.class)) {
            throw new IllegalArgumentException("This Class is not an Entity ");
        }

        String tableName = ddlQueryBuilder.getTableName(entityClass);
        return DROP_TABLE + tableName + ";";
    }

    private static void extra(DDLQueryBuilder ddlQueryBuilder, Field field, StringBuilder sb) {
        if (ddlQueryBuilder.isPrimaryKey(field)) {
            sb.append(" PRIMARY KEY");
        }

        if (ddlQueryBuilder.isAutoIncrement(field)) {
            sb.append(" AUTO_INCREMENT");
        }

        if (ddlQueryBuilder.isNotNullable(field)) {
            sb.append(" NOT NULL");
        }
    }

    private String getColumnName(Field field) {
        // Column 어노테이션이 존재하고 name 속성이 있으면 사용, 없으면 필드 이름 사용
        if (field.isAnnotationPresent(Column.class)) {
            Column column = field.getAnnotation(Column.class);
            if (!column.name().isEmpty()) {
                return column.name();
            }
        }
        return field.getName();
    }

    private String getTableName(Class<?> field) {
        if (field.isAnnotationPresent(Table.class)) {
            Table table = field.getAnnotation(Table.class);
            return !table.name().isEmpty() ? table.name() : field.getSimpleName();
        }
        return field.getSimpleName();
    }

    private boolean isAutoIncrement(Field field) {
        return field.isAnnotationPresent(GeneratedValue.class) &&
                field.getAnnotation(GeneratedValue.class).strategy() == GenerationType.IDENTITY;
    }

    private boolean isPrimaryKey(Field field) {
        return field.isAnnotationPresent(Id.class);
    }

    private boolean isNotNullable(Field field) {
        Column column = field.getAnnotation(Column.class);
        return column != null && !column.nullable();
    }

}
