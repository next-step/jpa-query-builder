package persistence.sql.dml;

import persistence.sql.Dialect;
import persistence.sql.QueryBuilder;
import persistence.sql.SQLEscaper;
import persistence.sql.TableSQLMapper;

import java.lang.reflect.Field;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.stream.Collectors;

public class RowFindByIdQueryBuilder extends QueryBuilder {
    final private LinkedHashSet<Object> primaryKeys = new LinkedHashSet<>();

    public RowFindByIdQueryBuilder(Dialect dialect) {
        super(dialect);
    }

    public String generateSQLQuery(Class<?> clazz) {
        return "SELECT " +
            String.join(", ", TableSQLMapper.getAllEscapedColumnNamesOfTable(clazz)) +
            " FROM " +
            TableSQLMapper.getTableName(clazz) +
            " WHERE " +
            whereClause(clazz) +
            ";";
    }

    @Override
    public RowFindByIdQueryBuilder findBy(Object... primaryKeyValues) {
        Collections.addAll(this.primaryKeys, primaryKeyValues);
        return this;
    }

    private String whereClause(Class<?> clazz) {
        Field primaryKeyField = TableSQLMapper.getPrimaryKeyColumnField(clazz);
        String primaryKeyColumName = SQLEscaper.escapeNameByBacktick(TableSQLMapper.getColumnName(primaryKeyField));
        switch (primaryKeys.size()) {
            case 0:
            case 1:
                return primaryKeyColumName + " = " + primaryKeys.stream().findFirst().orElse(0L);
            default:
                return primaryKeyColumName + " in (" + primaryKeys.stream().map(Object::toString).collect(Collectors.joining(", ")) + ")";
        }
    }
}
