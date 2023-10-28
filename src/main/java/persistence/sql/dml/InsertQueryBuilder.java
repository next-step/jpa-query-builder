package persistence.sql.dml;

import persistence.sql.ddl.EntityMetaData;
import persistence.sql.mapper.ColumnType;

import java.util.List;
import java.util.stream.Collectors;

public class InsertQueryBuilder {

    private static final String INSERT_QUERY_FORMAT = "insert into %s (%s) values(%s)";

    public String create(final EntityMetaData entityMetaData) {
        return String.format(INSERT_QUERY_FORMAT
                , entityMetaData.getTableName()
                , columnsClause(entityMetaData.getFieldColumns())
                , valueClause(entityMetaData.getFieldColumns()));
    }

    private String columnsClause(final List<ColumnType> columns) {
        return columns.stream()
                .map(ColumnType::getName)
                .collect(Collectors.joining(", "));
    }

    private String valueClause(final List<ColumnType> columns) {
        return columns.stream()
                .map(ColumnType::getValue)
                .collect(Collectors.joining(", "));
    }


}
