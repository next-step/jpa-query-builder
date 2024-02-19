package persistence.sql.ddl;

import jakarta.persistence.Transient;

import java.util.Arrays;

public class QueryBuilder {

    private static final String CREATE_TABLE_DDL = "create table ";
    private static final String OPEN_BRACKET = " (";
    private static final String CLOSE_BRACKET = ")";
    private static final String COMMA = ", ";

    public String createDdl(Class<?> clazz) {
        StringBuilder sb = new StringBuilder();
        TableColumn tableColumn = TableColumn.from(clazz);
        sb.append(CREATE_TABLE_DDL).append(tableColumn.getName()).append(OPEN_BRACKET);

        Arrays.stream(clazz.getDeclaredFields())
                .filter(field -> !field.isAnnotationPresent(Transient.class))
                .forEach(field -> {
                    JpaColumn jpaColumn = JpaColumn.from(field);
                    sb.append(jpaColumn.getDefinition());
                    sb.append(COMMA);
                });
        sb.delete(sb.length() - 2, sb.length());
        sb.append(CLOSE_BRACKET);

        return sb.toString();
    }
}
