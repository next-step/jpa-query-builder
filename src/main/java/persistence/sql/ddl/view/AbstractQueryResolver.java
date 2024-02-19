package persistence.sql.ddl.view;

import persistence.sql.ddl.DatabaseTypeConverter;
import persistence.sql.ddl.domain.DatabaseColumn;
import persistence.sql.ddl.domain.DatabasePrimaryColumn;

import java.util.List;

import static persistence.sql.ddl.CommonConstant.SPACE;

public abstract class AbstractQueryResolver implements QueryResolver {

    private static final String COLUMN_SEPARATOR = ", ";

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

                    if (column instanceof DatabasePrimaryColumn primaryColumn) {

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
