package persistence.sql.ddl.view;

import persistence.sql.ddl.DatabaseTypeConverter;
import persistence.sql.domain.DatabaseColumn;
import persistence.sql.domain.DatabasePrimaryColumn;

import java.util.List;

import static persistence.sql.CommonConstant.COLUMN_SEPARATOR;
import static persistence.sql.CommonConstant.SPACE;

public abstract class AbstractQueryResolver implements QueryResolver {

    private final DatabaseTypeConverter databaseTypeConverter;

    public AbstractQueryResolver(DatabaseTypeConverter databaseTypeConverter) {
        this.databaseTypeConverter = databaseTypeConverter;
    }

    @Override
    public String toQuery(List<DatabaseColumn> columns) {
        return columns.stream().map((column) -> {
                    StringBuilder sb = new StringBuilder();
                    sb.append(column.getName())
                            .append(SPACE)
                            .append(databaseTypeConverter.convert(column));

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