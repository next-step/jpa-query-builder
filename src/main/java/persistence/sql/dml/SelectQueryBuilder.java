package persistence.sql.dml;

import persistence.exception.PersistenceException;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class SelectQueryBuilder {
    private final List<String> data;
    private final Map<String, String> whereData;
    private String tableName;

    private SelectQueryBuilder() {
        this.data = new ArrayList<>();
        this.whereData = new LinkedHashMap<>();
    }

    public static SelectQueryBuilder builder() {
        return new SelectQueryBuilder();
    }

    public SelectQueryBuilder table(final String tableName) {
        this.tableName = tableName;
        return this;
    }

    public SelectQueryBuilder column(final String column) {
        this.data.add(column);
        return this;
    }

    public SelectQueryBuilder column(final List<String> columns) {
        this.data.addAll(columns);
        return this;
    }

    public SelectQueryBuilder where(final String column, final String data) {
        this.whereData.put(column, data);
        return this;
    }

    public SelectQueryBuilder where(final List<String> columns, final List<String> data) {
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
            throw new PersistenceException("테이블 이름 없이 select query 를 만들 수 없습니다.");
        }

        if (data.isEmpty()) {
            throw new PersistenceException("Data 정보 없이 select query 를 만들 수 없습니다.");
        }

        final StringBuilder builder = new StringBuilder();
        builder.append("select ")
                .append(String.join(", ", data))
                .append(" from ")
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
