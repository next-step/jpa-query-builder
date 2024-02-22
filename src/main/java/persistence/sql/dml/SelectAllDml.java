package persistence.sql.dml;

import persistence.sql.column.Column;
import persistence.sql.column.ColumnGenerator;
import persistence.sql.column.GeneralColumnFactory;
import persistence.sql.column.TableColumn;
import persistence.sql.dialect.Database;

import java.util.stream.Collectors;

public class SelectAllDml {

    private static final String SELECT_ALL_QUERY_FORMAT = "select %s from %s";
    private static final String COMMA = ", ";

    public String generate(Class<?> clazz, Database database) {
        ColumnGenerator columnGenerator = new ColumnGenerator(new GeneralColumnFactory());

        TableColumn tableColumn = TableColumn.from(clazz);
        String columns = columnGenerator.of(clazz.getDeclaredFields(), database.createDialect()).stream()
                .map(Column::getColumnName)
                .collect(Collectors.joining(COMMA));
        return String.format(SELECT_ALL_QUERY_FORMAT, columns, tableColumn.getName());
    }
}
