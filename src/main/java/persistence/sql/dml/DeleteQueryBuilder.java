package persistence.sql.dml;

import persistence.exception.PersistenceException;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class DeleteQueryBuilder {
    private final Map<String, String> whereData;
    private String tableName;

    private DeleteQueryBuilder() {
        this.whereData = new LinkedHashMap<>();
    }

    public static DeleteQueryBuilder builder() {
        return new DeleteQueryBuilder();
    }

    public DeleteQueryBuilder table(final String tableName) {
        this.tableName = tableName;
        return this;
    }

    public DeleteQueryBuilder where(final String column, final String data) {
        this.whereData.put(column, data);
        return this;
    }

    public DeleteQueryBuilder where(final List<String> columns, final List<String> data) {
        if (columns.size() != data.size()) {
            throw new PersistenceException("columns size 와 data size 가 같아야 합니다.");
        }
        for (int i = 0; i < columns.size(); i++) {
            where(columns.get(i), data.get(i));
        }
        return this;
    }

    public String build() {
        if (tableName == null || tableName.isEmpty()) {
            throw new PersistenceException("테이블 이름 없이 delete query 를 만들 수 없습니다.");
        }

        final StringBuilder builder = new StringBuilder();
        builder.append("delete from ")
                .append(tableName)
                .append(buildWhereClause());
        return builder.toString();
    }

    private String buildWhereClause() {
        if (whereData.isEmpty()) {
            return "";
        }
        final StringBuilder builder = new StringBuilder();
        builder.append(" where ");
        final List<String> columns = new ArrayList<>(whereData.keySet());
        for (int i = 0; i < columns.size(); i++) {
            if (i > 0 && i != columns.size() - 1) {
                builder.append(" and ");
            }
            builder.append(columns.get(i))
                    .append("=")
                    .append(whereData.get(columns.get(i)));
        }
        return builder.toString();
    }
}
