package persistence.sql.ddl;

import jakarta.persistence.Transient;
import persistence.sql.column.Columnn;
import persistence.sql.column.JpaColumn;
import persistence.sql.column.TableColumn;
import persistence.sql.dialect.Database;

import java.util.Arrays;

public class CreateDdl implements QueryBuilder {

    private static final String CREATE_TABLE_DDL = "create table ";
    private static final String COMMA = ", ";
    private static final String OPEN_BRACKET = " (";
    private static final String CLOSE_BRACKET = ")";

    @Override
    public String generate(Class<?> clazz, Database database) {

        StringBuilder sb = new StringBuilder();
        TableColumn tableColumn = TableColumn.from(clazz);
        sb.append(CREATE_TABLE_DDL).append(tableColumn.getName()).append(OPEN_BRACKET);

        Arrays.stream(clazz.getDeclaredFields())
                .filter(field -> !field.isAnnotationPresent(Transient.class))
                .forEach(field -> {
                    Columnn columnn = JpaColumn.from(field, database.createDialect());
                    sb.append(columnn.getDefinition());
                    sb.append(COMMA);
                });
        sb.delete(sb.length() - 2, sb.length());
        sb.append(CLOSE_BRACKET);

        return sb.toString();
    }
}
