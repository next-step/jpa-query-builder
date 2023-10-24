package persistence.sql.ddl;

import persistence.core.EntityColumn;
import persistence.core.EntityMetadataModel;
import persistence.sql.JdbcTypeJavaClassMapping;

import java.util.function.Consumer;

public class TableDdlQueryBuilder {

    private static final String BLANK = " ";

    private static final String COMMA = ",";

    private static final String OPEN_PARENTHESIS = "(";

    private static final String CLOSE_PARENTHESIS = ")";

    private static final String PRIMARY_KEY = "primary key";

    private final JdbcTypeJavaClassMapping javaClassMapping;

    public TableDdlQueryBuilder(JdbcTypeJavaClassMapping javaClassMapping) {
        this.javaClassMapping = javaClassMapping;
    }

    public String createDdlQuery(EntityMetadataModel entityMetadataModel) {
        EntityColumn primaryKeyColumn = entityMetadataModel.getPrimaryKeyColumn();
        StringBuilder builder = new StringBuilder();

        builder.append("create table ")
                .append(entityMetadataModel.getTableName())
                .append(OPEN_PARENTHESIS)
                .append(primaryKeyColumn.getName())
                .append(BLANK)
                .append(javaClassMapping.getType(primaryKeyColumn.getType()))
                .append(BLANK);

        addGenerateValueQueryIfPresent(primaryKeyColumn, builder);
        addColumnQueries(entityMetadataModel, builder);
        addPrimaryQuery(primaryKeyColumn, builder);

        builder.append(CLOSE_PARENTHESIS);
        return builder.toString();
    }

    public String createDropTableQuery(EntityMetadataModel entityMetadataModel) {
        return "drop table if exists " + entityMetadataModel.getTableName();
    }

    private void addGenerateValueQueryIfPresent(EntityColumn primaryKeyColumn, StringBuilder builder) {
        if (primaryKeyColumn.isAutoIncrement()) {
            builder.append("auto_increment");
        }
    }

    private void addColumnQueries(EntityMetadataModel entityMetadataModel, StringBuilder builder) {
        entityMetadataModel.getColumns()
                .forEach(createColumnQueries(builder));
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
                builder.append(javaClassMapping.getType(column.getType()))
                        .append(OPEN_PARENTHESIS)
                        .append(column.getLength())
                        .append(CLOSE_PARENTHESIS)
                        .append(BLANK)
                        .append(getUniqueConstraintQuery(column.isUnique()))
                        .append(getNullableConstraintQuery(column.isNullable()));
                return;
            }

            builder.append(javaClassMapping.getType(column.getType()))
                    .append(BLANK)
                    .append(getUniqueConstraintQuery(column.isUnique()))
                    .append(getNullableConstraintQuery(column.isNullable()));
        };
    }

    private String getNullableConstraintQuery(boolean isNullable) {
        return isNullable ? "null" : "not null";
    }

    private String getUniqueConstraintQuery(boolean isUnique) {
        return isUnique ? "unique" : "";
    }
}
