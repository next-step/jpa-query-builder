package persistence.sql.ddl;

import persistence.sql.dialect.Dialect;
import persistence.sql.model.*;

import java.util.List;
import java.util.stream.Collectors;

public class CreateQueryBuilder {

    private final static String CREATE_QUERY_FORMAT = "CREATE TABLE %s (%s);";

    private final Table table;
    private final Dialect dialect;

    public CreateQueryBuilder(Table table, Dialect dialect) {
        this.table = table;
        this.dialect = dialect;
    }

    public String build() {
        String tableName = table.getName();
        String columns = buildColumnsQuery();
        return String.format(CREATE_QUERY_FORMAT, tableName, columns);
    }

    private String buildColumnsQuery() {
        StringBuilder columnsBuilder = new StringBuilder();

        String pkColumn = buildPKColumnQuery();
        columnsBuilder.append(pkColumn);

        Columns columns = table.getColumns();
        String columnsClause = columns.stream()
                .map(this::buildColumnQuery)
                .collect(Collectors.joining(",", ",", ""));

        columnsBuilder.append(columnsClause);
        return columnsBuilder.toString();
    }

    private String buildPKColumnQuery() {
        StringBuilder pkColumnBuilder = new StringBuilder();

        PKColumn pkColumn = table.getPKColumn();

        String name = pkColumn.getName();
        pkColumnBuilder.append(name);

        SqlType sqlType = pkColumn.getType();
        String type = dialect.getType(sqlType);
        pkColumnBuilder.append(' ')
                .append(type);

        pkColumn.getGenerationType()
                .ifPresent((generationType) -> {
                    String generationStrategy = dialect.getGenerationStrategy(generationType);
                    pkColumnBuilder.append(' ')
                            .append(generationStrategy);
                });

        List<SqlConstraint> sqlConstraints = pkColumn.getConstraints();
        sqlConstraints.forEach(sqlConstraint -> {
            String constraint = dialect.getConstraint(sqlConstraint);
            pkColumnBuilder
                    .append(' ')
                    .append(constraint);
        });


        return pkColumnBuilder.toString();
    }

    private String buildColumnQuery(Column column) {
        StringBuilder columnBuilder = new StringBuilder();

        SqlType sqlType = column.getType();
        List<SqlConstraint> sqlConstraints = column.getConstraints();

        String name = column.getName();
        String type = dialect.getType(sqlType);

        columnBuilder.append(name)
                .append(' ')
                .append(type);

        sqlConstraints.forEach(sqlConstraint -> {
            String constraint = dialect.getConstraint(sqlConstraint);
            columnBuilder
                    .append(' ')
                    .append(constraint);
        });

        return columnBuilder.toString();
    }
}
