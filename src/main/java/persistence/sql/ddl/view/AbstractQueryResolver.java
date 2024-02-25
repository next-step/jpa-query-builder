package persistence.sql.ddl.view;

import persistence.sql.Dialect;
import persistence.sql.domain.DatabaseColumn;
import persistence.sql.domain.DatabasePrimaryColumn;

import java.util.List;

import static persistence.sql.CommonConstant.COLUMN_SEPARATOR;
import static persistence.sql.CommonConstant.SPACE;

public abstract class AbstractQueryResolver implements QueryResolver {

    private final Dialect dialect;

    public AbstractQueryResolver(Dialect dialect) {
        this.dialect = dialect;
    }

    @Override
    public String toQuery(List<DatabaseColumn> columns) {
        return columns.stream().map((column) -> {
                    StringBuilder sb = new StringBuilder();
                    String jdbcType = dialect.getJdbcTypeFromJavaClass(column);
                    sb.append(column.getJdbcColumnName())
                            .append(SPACE)
                            .append(jdbcType);

                    if (column instanceof DatabasePrimaryColumn) {
                        DatabasePrimaryColumn primaryColumn = (DatabasePrimaryColumn) column;
                        sb.append(addPrimaryConstraint(primaryColumn));
                        return sb.toString();
                    }

                    if (!column.isNullable()) {
                        sb.append(addNotNullConstraint());
                    }
                    return sb.toString();
                })
                .reduce((columnA, columnB) -> String.join(COLUMN_SEPARATOR, columnA, columnB))
                .orElseThrow(IllegalStateException::new);
    }

    protected abstract String addPrimaryConstraint(DatabasePrimaryColumn primaryColumn);

    protected abstract String addNotNullConstraint();
}
