package persistence.sql.dialect;

import persistence.sql.dml.ColumnClause;
import persistence.sql.dml.ValueClause;
import persistence.sql.dml.insert.InsertQuery;
import persistence.sql.vo.type.VarChar;

public class H2Dialect implements Dialect{

    @Override
    public String insertBuilder(InsertQuery insertQuery) {
        StringBuilder sb = new StringBuilder();
        sb.append("insert into ");
        sb.append(insertQuery.getTableName());
        sb.append(" (");
        int n = insertQuery.getColumnClauses().size();
        for(int i = 0; i < n; ++i) {
            fillColumn(insertQuery.getColumnClauses().get(i), sb, i == n - 1);
        }
        sb.append(") values (");
        for(int i = 0;i < n; ++i) {
            fillValue(insertQuery.getValueClauses().get(i), sb, i == n - 1);
        }
        sb.append(");");
        return sb.toString();
    }
    private void fillColumn(ColumnClause columnClause, StringBuilder sb, boolean isLast) {
        sb.append(columnClause.getColumnName());
        if (!isLast) {
            sb.append(", ");
        }
    }
    private void fillValue(ValueClause valueClause, StringBuilder sb, boolean isLast) {
        if(valueClause.getObject() != null && valueClause.getDatabaseType() instanceof VarChar) {
            sb.append("'");
            sb.append(valueClause.getObject().toString());
            sb.append("'");
        } else {
            sb.append(valueClause.getObject());
        }
        if (!isLast) {
            sb.append(", ");
        }
    }


}
