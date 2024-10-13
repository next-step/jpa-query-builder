package persistence.sql.ddl;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import persistence.sql.ddl.type.TypeReference;

import java.lang.reflect.Field;

public class TableExporter {

    public String getSqlCreateQueryString(Class<?> clazz) {
        if (!clazz.isAnnotationPresent(Entity.class)) {
            throw new RuntimeException("@Entity not exist. class = " + clazz.getName());
        }

        StringBuilder queryBuilder = new StringBuilder();
        String tableName = clazz.getSimpleName().toLowerCase();

        queryBuilder.append( tableCreateString() )
                .append( " " )
                .append( tableName )
                .append( " (" );

        Field[] fields = clazz.getDeclaredFields();
        boolean isFirst = true;
        for (Field field : fields) {
            if (isFirst) {
                isFirst = false;
            }
            else {
                queryBuilder.append(", ");
            }

            String sqlType = TypeReference.getSqlType(field.getType());
            queryBuilder.append(snakeCase(field.getName()))
                    .append( " " )
                    .append( sqlType );
            if (sqlType.equals("varchar")) {
                queryBuilder.append("(255)");
            }

            if (field.isAnnotationPresent(Id.class)) {
                queryBuilder.append( " " )
                        .append( "not null" );
            }
        }

        if (hasPrimaryKey(fields)) {
            Field primaryKeyField = getPrimaryField(fields);
            queryBuilder.append( ", " )
                    .append("primary key (")
                    .append(primaryKeyField.getName())
                    .append(")");
        }

        queryBuilder.append(")");
        return queryBuilder.toString();
    }

    private String tableCreateString() {
        return "create table";
    }

    private String snakeCase(String columnName) {
        return columnName.replaceAll("([a-z])([A-Z])", "$1_$2").toLowerCase();
    }

    private boolean hasPrimaryKey(Field[] fields) {
        for (Field field : fields) {
            if (field.isAnnotationPresent(Id.class)) {
                return true;
            }
        }
        return false;
    }

    private Field getPrimaryField(Field[] fields) {
        for (Field field : fields) {
            if (field.isAnnotationPresent(Id.class)) {
                return field;
            }
        }
        throw new RuntimeException("primary key field not exist.");
    }
}
