package persistence.sql.dml;

import util.StringUtils;
import persistence.sql.QueryException;
import persistence.sql.dialect.Dialect;
import persistence.sql.mapping.Column;
import persistence.sql.mapping.Table;
import persistence.sql.mapping.Value;

import java.util.List;
import java.util.stream.Collectors;

import static persistence.sql.QueryBuilderConst.ENTER;
import static persistence.sql.QueryBuilderConst.SPACE;

public class DefaultDmlQueryBuilder implements DmlQueryBuilder {

    private final Dialect dialect;

    private final List<InsertQueryValueBinder> insertQueryValueBinders = initInsertQueryValueBinders();

    public DefaultDmlQueryBuilder(Dialect dialect) {
        this.dialect = dialect;
    }

    private List<InsertQueryValueBinder> initInsertQueryValueBinders() {
        return List.of(
                new InsertQueryStringValueBinder(),
                new InsertQueryNumberValueBinder()
        );
    }

    @Override
    public String buildInsertQuery(final Table table) {

        final StringBuilder statement = new StringBuilder()
                .append("insert")
                .append(ENTER)
                .append("into")
                .append(ENTER)
                .append(SPACE)
                .append(table.getName())
                .append(ENTER)
                .append(SPACE)
                .append("(");

        final List<Column> columns = table.getColumns();
        final String columnsClause = columnsClause(columns);

        statement.append(columnsClause)
                .append(")")
                .append(ENTER)
                .append("values")
                .append(ENTER)
                .append(SPACE)
                .append("(");

        final String valuesClause = columns.stream()
                .map(this::valueClause)
                .filter(StringUtils::isNotBlank)
                .collect(Collectors.joining("," + ENTER + SPACE));

        return statement
                .append(valuesClause)
                .append(")")
                .toString();
    }

    private String columnsClause(final List<Column> columns) {

        return columns.stream()
                .map(this::toInsertQueryColumnName)
                .filter(StringUtils::isNotBlank)
                .collect(Collectors.joining("," + ENTER + SPACE));
    }

    private String toInsertQueryColumnName(final Column column) {
        if (isIdentityColumnWithInsertKeyword(column)) {
            return "";
        }

        return column.getName();
    }

    private String valueClause(final Column column) {
        if (isIdentityColumnWithInsertKeyword(column)) {
            return "";
        }

        final Value value = column.getValue();

        return insertQueryValueBinders.stream()
                .filter(binder -> binder.support(value))
                .findFirst()
                .orElseThrow(() -> new QueryException("not found InsertQueryValueBinder for " + value.getOriginalType() + " type"))
                .bind(value.getValue());
    }

    private boolean isIdentityColumnWithInsertKeyword(final Column column) {
        return column.isIdentifierKey() && !dialect.getIdentityColumnSupport().hasIdentityInsertKeyword();
    }
}
