package persistence.sql.dialect;

import java.util.Map;
import persistence.sql.dml.ColumnClause;
import persistence.sql.dml.ValueClause;
import persistence.sql.dml.delete.DeleteQuery;
import persistence.sql.dml.insert.InsertQuery;
import persistence.sql.dml.select.SelectQuery;
import persistence.sql.dml.where.ConditionType;
import persistence.sql.dml.where.KeyCondition;
import persistence.sql.dml.where.WhereQuery;
import persistence.sql.vo.type.VarChar;

public class H2Dialect implements Dialect{
    private static final String SINGLE_QUOTE = "'";
    @Override
    public String insertBuilder(InsertQuery insertQuery) {
        StringBuilder sb = new StringBuilder();
        sb.append("insert into ");
        sb.append(insertQuery.getTableName());
        fillColumnAndValue(insertQuery.getColumToValueMap(), sb);
        return sb.toString();
    }

    @Override
    public String selectBuilder(SelectQuery selectQuery) {
        StringBuilder sb = new StringBuilder();
        sb.append("select * from ");
        sb.append(selectQuery.getTableName().toString());
        sb.append(";");
        return sb.toString();
    }

    @Override
    public String selectConditionBuilder(SelectQuery selectQuery, WhereQuery whereQuery) {
        StringBuilder sb = new StringBuilder();
        sb.append("select * from ");
        sb.append(selectQuery.getTableName().toString());
        whereQueryToSql(whereQuery, sb);
        sb.append(";");
        return sb.toString();
    }

    @Override
    public String deleteBuilder(DeleteQuery deleteQuery, WhereQuery whereQuery) {
        StringBuilder sb = new StringBuilder();
        sb.append("delete from ");
        sb.append(deleteQuery.getTableName().toString());
        whereQueryToSql(whereQuery, sb);
        sb.append(";");
        return sb.toString();
    }

    private void fillColumnAndValue(Map<ColumnClause, ValueClause> map, StringBuilder sb) {
        sb.append(" (");
        int idx = 0;
        final int maxIdx = map.size() - 1;
        for(ColumnClause columnClause : map.keySet()) {
            fillColumn(columnClause, sb, idx == maxIdx);
            idx++;
        }
        sb.append(") values (");
        idx = 0;
        for(ValueClause valueClause : map.values()) {
            fillValue(valueClause, sb, idx == maxIdx);
            idx++;
        }
        sb.append(");");
    }

    private void fillColumn(ColumnClause columnClause, StringBuilder sb, boolean isLast) {
        sb.append(columnClause.getColumnName());
        if (!isLast) {
            sb.append(", ");
        }
    }
    private void fillValue(ValueClause valueClause, StringBuilder sb, boolean isLast) {
        if(valueClause.getObject() != null && valueClause.getDatabaseType() instanceof VarChar) {
            sb.append(changeToSql(valueClause));
        } else {
            sb.append(valueClause.getObject());
        }
        if (!isLast) {
            sb.append(", ");
        }
    }

    private void whereQueryToSql(WhereQuery whereQuery, StringBuilder sb) {
        sb.append(" where ");
        int n = whereQuery.getKeyConditions().size();
        for(int i = 0;i < n;++i) {
            fillWhere(whereQuery.getKeyConditions().get(i), sb, i == 0);
        }
    }

    private void fillWhere(KeyCondition keyCondition, StringBuilder sb, boolean isStart) {
        if(!isStart){
            sb.append(" and ");
        }
        sb.append(keyCondition.getKey());
        if(keyCondition.getConditionType() == ConditionType.IS) {
            sb.append(" = ");
        }
        sb.append(changeToSql(keyCondition.getValue()));
    }

    private String changeToSql(ValueClause valueClause) {
        if(valueClause.getObject() == null) {
            return "null";
        }
        if(valueClause.getDatabaseType() instanceof VarChar) {
            return SINGLE_QUOTE + valueClause.getObject().toString() + SINGLE_QUOTE;
        }
        return valueClause.getObject().toString();
    }
}
