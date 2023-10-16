package persistence.sql.ddl.h2;

import persistence.core.EntityColumn;
import persistence.core.EntityMetadataModel;
import persistence.sql.ddl.TableDdlQueryBuilder;

import java.util.function.Consumer;

public class H2TableDdlQueryBuilder implements TableDdlQueryBuilder {

    private static final String BLANK = " ";

    private static final String COMMA = ",";

    private static final String OPEN_PARENTHESIS = "(";

    private static final String CLOSE_PARENTHESIS = ")";

    private static final String PRIMARY_KEY = "primary key";

    private final H2Dialect h2Dialect;

    public H2TableDdlQueryBuilder(H2Dialect h2Dialect) {
        this.h2Dialect = h2Dialect;
    }

    @Override
    public String createDdlQuery(EntityMetadataModel entityMetadataModel) {
        EntityColumn primaryKeyColumn = entityMetadataModel.getPrimaryKeyColumn();

        StringBuilder builder = new StringBuilder();

        builder.append("create table ")
                .append(entityMetadataModel.getTableName())
                .append(OPEN_PARENTHESIS)
                .append(primaryKeyColumn.getName())
                .append(BLANK)
                .append(h2Dialect.getDialectType(primaryKeyColumn.getType()))
                .append(BLANK);

        if (primaryKeyColumn.isAutoIncrement()) {
            builder.append("auto_increment");
        }

        entityMetadataModel.getColumns()
                .forEach(createColumnQueries(builder));

        addPrimaryQuery(primaryKeyColumn, builder);

        builder.append(CLOSE_PARENTHESIS);

        return builder.toString();
    }

    @Override
    public String createDropTableQuery(EntityMetadataModel entityMetadataModel) {
        return "drop table if exists " + entityMetadataModel.getTableName();
    }

    private void addPrimaryQuery(EntityColumn primaryKeyColumn, StringBuilder builder) {
        builder.append(COMMA)
                .append(BLANK)
                .append(PRIMARY_KEY)
                .append(BLANK)
                .append(OPEN_PARENTHESIS)
                .append(primaryKeyColumn.getName())
                .append(CLOSE_PARENTHESIS);
    }

    private Consumer<EntityColumn> createColumnQueries(StringBuilder builder) {
        return column -> {
            if (column.hasTransient()) {
                return;
            }

            builder.append(COMMA)
                    .append(BLANK)
                    .append(column.getName())
                    .append(BLANK);

            if (column.isStringType()) {
                builder.append(h2Dialect.getDialectType(column.getType()))
                        .append(OPEN_PARENTHESIS)
                        .append(column.getLength())
                        .append(CLOSE_PARENTHESIS)
                        .append(BLANK);
            }

            builder.append(getUniqueConstraintQuery(column.isUnique()))
                    .append(getNullableConstraintQuery(column.isNullable()));
        };
    }
}
