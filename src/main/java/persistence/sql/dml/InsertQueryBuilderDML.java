package persistence.sql.dml;

import persistence.sql.ddl.QueryBuilderDDL;
import persistence.sql.meta.ColumnInfos;
import persistence.sql.meta.TableInfo;

public class InsertQueryBuilderDML {
    private static InsertQueryBuilderDML queryBuilderDML = new InsertQueryBuilderDML();
    private InsertQueryBuilderDML() { }
    public static InsertQueryBuilderDML getInstance() {
        return queryBuilderDML;
    }

    public String getQuery(Class<?> clazz) {
        String insertFormat = "insert into %s (%s) values (%s);";
        return String.format(insertFormat, columnsClause(clazz), valueClause(clazz));
    }

    private String columnsClause(Class<?> clazz) {
        ColumnInfos columnInfos = new ColumnInfos(clazz);
        return "";
    }

    private String valueClause(Object object) {
        return "";
    }

}
