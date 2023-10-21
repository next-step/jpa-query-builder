package persistence.sql.ddl;

import persistence.core.EntityColumn;
import persistence.core.EntityMetadata;
import persistence.dialect.Dialect;

public class DdlGenerator {

    private final Dialect dialect;

    public DdlGenerator(final Dialect dialect) {
        this.dialect = dialect;
    }

    public String generateCreateDdl(final EntityMetadata<?> entityMetadata) {
        final StringBuilder builder = new StringBuilder();

        final String tableName = entityMetadata.getTableName();
        builder.append("create table ")
                .append(tableName)
                .append(" ")
                .append(generateColumnsClause(entityMetadata));

        return builder.toString();
    }

    private String generateColumnsClause(final EntityMetadata<?> entityMetadata) {
        final StringBuilder builder = new StringBuilder();
        builder.append("(");

        entityMetadata.getColumns().forEach(column ->
                builder.append(generateColumnDefinition(column))
                        .append(",")
        );

        builder.append(generatePKConstraintClause(entityMetadata));

        builder.append(")");
        return builder.toString();
    }

    private String generateColumnDefinition(final EntityColumn column) {
        final StringBuilder builder = new StringBuilder();
        builder.append(column.getName())
                .append(" ")
                .append(generateColumnTypeClause(column))
                .append(generateNotNullClause(column))
                .append(generateAutoIncrementClause(column));
        return builder.toString();
    }

    private String generateColumnTypeClause(final EntityColumn column) {
        final StringBuilder builder = new StringBuilder();
        builder.append(dialect.getColumnTypeMapper().getColumnName(column.getType()));
        if (column.isStringValued()) {
            builder.append("(")
                    .append(column.getStringLength())
                    .append(")");
        }
        return builder.toString();
    }

    private String generateAutoIncrementClause(final EntityColumn column) {
        if (column.isAutoIncrement()) {
            return " auto_increment";
        }

        return "";
    }

    private String generateNotNullClause(final EntityColumn column) {
        if (column.isNotNull()) {
            return " not null";
        }

        return "";
    }

    private String generatePKConstraintClause(final EntityMetadata<?> entityMetadata) {
        final StringBuilder builder = new StringBuilder();
        builder.append("CONSTRAINT PK_")
                .append(entityMetadata.getTableName())
                .append(" PRIMARY KEY (")
                .append(entityMetadata.getIdColumnName())
                .append(")");
        return builder.toString();
    }

    public String generateDropDdl(final EntityMetadata<?> entityMetadata) {
        final StringBuilder builder = new StringBuilder();
        builder.append("drop table ")
                .append(entityMetadata.getTableName());

        return builder.toString();
    }
}
