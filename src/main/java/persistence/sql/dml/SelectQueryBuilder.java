package persistence.sql.dml;

import persistence.exception.PersistenceException;

import java.util.ArrayList;
import java.util.List;

public class SelectQueryBuilder {
    private final List<String> data;
    private final WhereClauseBuilder whereClauseBuilder;
    private String tableName;

    private SelectQueryBuilder() {
        this.data = new ArrayList<>();
        this.whereClauseBuilder = WhereClauseBuilder.builder();
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
        this.whereClauseBuilder.and(column, data);
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
                .append(whereClauseBuilder.build());
        return builder.toString();
    }
}
