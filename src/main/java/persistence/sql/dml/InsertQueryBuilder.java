package persistence.sql.dml;

import org.h2.table.Column;
import persistence.sql.ddl.EntityMetaData;
import persistence.sql.ddl.Person;
import persistence.sql.ddl.utils.ColumnType;
import persistence.sql.dml.value.Value;

import java.util.List;
import java.util.stream.Collectors;

public class InsertQueryBuilder {

    private static final String INSERT_QUERY_FORMAT = "insert into %s (%s) values(%s)";

    public String create(final EntityMetaData entityMetaData, Object object) {
        return String.format(INSERT_QUERY_FORMAT
                , entityMetaData.getTableName()
                , columnsClause(entityMetaData.getFieldColumns())
                , valueClause(entityMetaData, object));
    }

    private String columnsClause(final List<ColumnType> columns) {
        return columns.stream().map(ColumnType::getName)
                .collect(Collectors.joining(", "));
    }

    private String valueClause(final EntityMetaData entityMetaData, Object object) {
        return "28, '지영', jy@lim.com";
    }


}
