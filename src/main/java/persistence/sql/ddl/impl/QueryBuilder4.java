package persistence.sql.ddl.impl;

import jakarta.persistence.Table;

public class QueryBuilder4 extends QueryBuilder3 {

    @Override
    protected String getTableNameFrom(Class<?> clazz) {
        StringBuilder sb = new StringBuilder();

        String schema = getSchemaNameFrom(clazz);

        if (!schema.isEmpty()) {
            sb.append(schema).append(".");
        }

        sb.append(super.getTableNameFrom(clazz));

        return sb.toString();
    }

    protected String getSchemaNameFrom(Class<?> clazz) {
       if (clazz.isAnnotationPresent(Table.class)) {
           Table table = clazz.getAnnotation(Table.class);
           if (!table.schema().isEmpty()) {
               return table.schema();
           }
       }

       return "";
   }
}
