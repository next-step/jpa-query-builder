package persistence.sql.ddl;

import persistence.model.EntityColumn;
import persistence.model.EntityFactory;
import persistence.model.EntityTable;
import persistence.sql.dialect.Dialect;

import java.util.List;
import java.util.stream.Collectors;

public class DdlQueryBuilder {
    private final Dialect dialect;

    public DdlQueryBuilder(Dialect dialect) {
        this.dialect = dialect;
    }

    public String getDropTableQuery(Class<?> entityClass) {
        EntityTable table = EntityFactory.createTable(entityClass);

        return dialect.getDropTablePhrase() + " " + dialect.getIdentifierQuoted(table.getName());
    }

    public String getCreateTableQuery(Class<?> entityClass) {
        EntityTable table = EntityFactory.createTable(entityClass);

        return dialect.getCreateTablePhrase() +
                " " +
                dialect.getIdentifierQuoted(table.getName()) +
                " (" +
                getColumnsDdl(table) +
                ", " +
                getPrimaryDdl(table) +
                ");";
    }

    private String getPrimaryDdl(EntityTable table) {
        List<String> columnNames = table.getPrimaryColumns()
                .stream().map(EntityColumn::getName).collect(Collectors.toList());
        return dialect.buildPrimaryKeyPhrase(columnNames);
    }

    private String getColumnsDdl(EntityTable table) {
        return table.getColumns()
                .stream().map(this::getColumnDdl)
                .collect(Collectors.joining(", "));
    }

    private String getColumnDdl(EntityColumn column) {
        String nameQuoted = dialect.getIdentifierQuoted(column.getName());
        String dataType = dialect.getDataTypeFullName(column.getType(), column.getLength());
        String nullPhrase = dialect.getNullPhrase(column.isNullable());

        if (column.isAutoGeneratedIdentity()) {
            return getAutoGeneratedIdentityColumnDdl(nameQuoted, dataType, nullPhrase);
        }
        return nameQuoted + " " + dataType + " " + nullPhrase;
    }

    private String getAutoGeneratedIdentityColumnDdl(String name, String dataType, String nullPhrase) {
        StringBuilder builder = new StringBuilder();

        builder.append(name).append(" ").append(dataType);

        if (dialect.shouldSpecifyNotNullOnIdentity()) {
            builder.append(" ").append(nullPhrase);
        }
        builder.append(" ").append(dialect.getAutoGeneratedIdentityPhrase());

        return builder.toString();
    }
}
