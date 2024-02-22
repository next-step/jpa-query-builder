package persistence.sql.dml;

import persistence.sql.column.Column;
import persistence.sql.column.ColumnGenerator;
import persistence.sql.column.GeneralColumnFactory;
import persistence.sql.column.TableColumn;
import persistence.sql.dialect.Database;
import persistence.sql.dialect.Dialect;

import java.util.List;

public class DeleteQueryBuilder {

    private static final String DELETE_QUERY_FORMAT = "delete from %s";
    private static final String WHERE_CLAUSE_FORMAT = " where %s = %d";

    private TableColumn tableColumn;
    private List<Column> columns;
    private String query;

    private DeleteQueryBuilder(TableColumn tableColumn, List<Column> columns) {
        this.tableColumn = tableColumn;
        this.columns = columns;
    }

    public static DeleteQueryBuilder generate(Class<?> clazz, Database database) {
        ColumnGenerator columnGenerator = new ColumnGenerator(new GeneralColumnFactory());
        TableColumn tableColumn = TableColumn.from(clazz);
        Dialect dialect = database.createDialect();
        List<Column> columns = columnGenerator.of(clazz.getDeclaredFields(), dialect);
        return new DeleteQueryBuilder(tableColumn, columns);
    }

    public DeleteQueryBuilder build() {
        this.query = String.format(DELETE_QUERY_FORMAT, tableColumn.getName());
        return this;
    }

    public String deleteById(Long id) {
        Column pkColumn = columns.stream()
                .filter(Column::isPk)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("No primary key found"));
        return query + WHERE_CLAUSE_FORMAT.formatted(pkColumn.getColumnName(), id);
    }
}
