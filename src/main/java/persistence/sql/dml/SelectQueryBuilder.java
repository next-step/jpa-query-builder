package persistence.sql.dml;

import persistence.exception.PersistenceException;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class SelectQueryBuilder {
    private final List<String> data;
    private String tableName;

    public SelectQueryBuilder() {
        this.data = new ArrayList<>();
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
                .append(tableName);
        return builder.toString();
    }

}
