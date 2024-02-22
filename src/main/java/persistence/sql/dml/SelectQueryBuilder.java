package persistence.sql.dml;

import persistence.sql.column.Column;
import persistence.sql.column.ColumnGenerator;
import persistence.sql.column.GeneralColumnFactory;
import persistence.sql.column.TableColumn;
import persistence.sql.dialect.Database;

import java.util.List;
import java.util.stream.Collectors;

public class SelectQueryBuilder {
    private static final String SELECT_QUERY_FORMAT = "select %s from %s";
    private static final String WHERE_CLAUSE_FORMAT = " where %s = %d";
    private static final String COMMA = ", ";

    private final TableColumn tableColumn;
    private final List<Column> columns;
    private String query;

    private SelectQueryBuilder(TableColumn tableColumn, List<Column> columns, String query) {
        this.tableColumn = tableColumn;
        this.columns = columns;
        this.query = query;
    }

    public static SelectQueryBuilder generate(Class<?> clazz, Database database) {
        ColumnGenerator columnGenerator = new ColumnGenerator(new GeneralColumnFactory());

        TableColumn tableColumn = TableColumn.from(clazz);
        List<Column> columns = columnGenerator.of(clazz.getDeclaredFields(), database.createDialect());

        return new SelectQueryBuilder(tableColumn, columns, null);
    }

    public SelectQueryBuilder build() {
        String columnDefinitions = columns.stream()
                .map(Column::getColumnName)
                .collect(Collectors.joining(COMMA));
        this.query = String.format(SELECT_QUERY_FORMAT, columnDefinitions, tableColumn.getName());

        return this;
    }

    public String findById(Long id) {
        return this.query + whereClause(this.columns, id);
    }

    public String findAll() {
        return this.query;
    }

    private String whereClause(List<Column> columns, Long id) {
        Column pkColumn = columns.stream()
                .filter(Column::isPk)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("No primary key found"));
        return String.format(WHERE_CLAUSE_FORMAT, pkColumn.getColumnName(), id);
    }

}
