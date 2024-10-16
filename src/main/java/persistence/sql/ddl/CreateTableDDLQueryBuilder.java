package persistence.sql.ddl;

import jakarta.persistence.*;

import java.util.Arrays;
import java.util.stream.Collectors;


public class CreateTableDDLQueryBuilder extends DDLQueryBuilder implements QueryBuilderAdapter {
    @Override
    public String executeQuery(Class<?> entityClass, DDLType ddlType) {
        if(ddlType == DDLType.CREATE) {
            return createTable(entityClass);
        }
        throw new IllegalArgumentException("Unsupported DDL Type");
    }

    private static final String CREATE_TABLE = "CREATE TABLE ";


    public String createTable( Class<?> entityClass) {
        if (!entityClass.isAnnotationPresent(Entity.class)) {
            throw new IllegalArgumentException("This Class is not an Entity ");
        }

        String tableName = getTableName(entityClass);
        String columns = Arrays.stream(entityClass.getDeclaredFields())
                .filter(field -> !field.isAnnotationPresent(Transient.class)).map(field -> {
                    StringBuilder sb = new StringBuilder();
                    String columnName = getColumnName(field);
                    Class<?> type = field.getType();

                    sb.append(columnName).append(" ");
                    sb.append(H2DBDataType.castType(type));

                    appendColumnAttributes( field, sb);

                    return sb.toString();
                }).collect(Collectors.joining(", "));

        return CREATE_TABLE + tableName + " (" + columns + ");";
    }

}
