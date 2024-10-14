package persistence.sql.dml;

import persistence.model.EntityColumn;
import persistence.model.EntityFactory;
import persistence.model.EntityTable;
import persistence.model.meta.Value;
import persistence.sql.dialect.Dialect;

import java.util.List;

public class DmlQueryBuilder {
    private final Dialect dialect;

    public DmlQueryBuilder(Dialect dialect) {
        this.dialect = dialect;
    }

    public String getInsertQuery(Object entityObject) {
        String INSERT_FORMAT = "INSERT INTO %s (%s) VALUES (%s)";

        EntityTable table = EntityFactory.createPopulatedSchema(entityObject);

        List<EntityColumn> columns = table.getInsertableColumns(table);
        List<String> columnNames = columns.stream().map(EntityColumn::getName).toList();
        List<Value> values = columns.stream().map(EntityColumn::getValue).toList();

        String columnNamesJoined = dialect.getIdentifiersQuoted(columnNames);
        String valueNames = dialect.getValuesQuoted(values);

        String tableName = table.getName();

        return String.format(INSERT_FORMAT, dialect.getIdentifierQuoted(tableName), columnNamesJoined, valueNames);
    }
}
