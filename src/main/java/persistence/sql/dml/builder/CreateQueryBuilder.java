package persistence.sql.dml.builder;

import persistence.dialect.Dialect;
import persistence.metadata.ColumnMeta;
import persistence.metadata.ColumnType;
import persistence.metadata.Identifier;
import persistence.metadata.TableName;

import java.util.List;
import java.util.stream.Collectors;

public class CreateQueryBuilder {
    private static final String CREATE_TABLE = "create table";

    private final Dialect dialect;
    private final StringBuilder queryString;

    private CreateQueryBuilder(Dialect dialect) {
        this.dialect = dialect;
        this.queryString = new StringBuilder();
    }
    public static CreateQueryBuilder builder(Dialect dialect) {
        return new CreateQueryBuilder(dialect);
    }

    public String build() {
        return queryString.toString();
    }

    public CreateQueryBuilder create(TableName table, Identifier identifier, List<ColumnMeta> columns) {
        queryString.append( CREATE_TABLE )
                .append( " " )
                .append( table.name() )
                .append( " (" );

        queryString.append( definePrimaryKeyColumn(identifier, dialect) ).append(", ");
        queryString.append(
                getColumnMetaValue(columns)
        );
        queryString.append( definePrimaryKeyConstraint(identifier) );

        queryString.append(")");
        return this;
    }

    private String getColumnMetaValue(List<ColumnMeta> columns) {
        return columns.stream()
                .map(column -> define(column, dialect))
                .collect(Collectors.joining(", "));
    }

    public static String define(ColumnMeta column, Dialect dialect) {
        StringBuilder builder = new StringBuilder();

        builder.append( column.name() )
                .append( " " )
                .append( dialect.getColumnType(column.type()));
        if (ColumnType.isVarcharType(column.type())) {
            builder.append("(")
                    .append(column.length())
                    .append(")");
        }
        if (column.notNull()) {
            builder.append( " " )
                    .append( "not null" );
        }
        return builder.toString();
    }

    public static String definePrimaryKeyColumn(Identifier identifier, Dialect dialect) {
        return define(identifier.column(), dialect) +
                " " +
                dialect.getIdentifierGenerationType(identifier.generationType());
    }

    public static String definePrimaryKeyConstraint(Identifier identifier) {
        return ", " +
                "primary key (" +
                identifier.column().name() +
                ")";
    }
}
