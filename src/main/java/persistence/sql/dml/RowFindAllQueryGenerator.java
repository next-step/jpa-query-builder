package persistence.sql.dml;

import persistence.sql.Dialect;
import persistence.sql.QueryBuilder;
import persistence.sql.TableFieldUtil;
import persistence.sql.TableQueryUtil;

import java.util.Arrays;
import java.util.stream.Collectors;

public class RowFindAllQueryGenerator extends QueryBuilder {

    public RowFindAllQueryGenerator(Dialect dialect) {
        super(dialect);
    }

    @Override
    public String generateSQLQuery(Object object) {
        String columns = Arrays
            .stream(TableFieldUtil.getColumnNames(TableFieldUtil.getAvailableFields(object.getClass())))
            .map(x -> TableQueryUtil.getTableName(object.getClass()) + "." + x)
            .collect(Collectors.joining(", "));
        return "SELECT " +
            columns +
            " FROM " +
            TableQueryUtil.getTableName(object.getClass()) +
            ";";
    }
}
