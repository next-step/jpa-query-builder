package persistence.sql.dml;

import persistence.exception.PersistenceException;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class InsertQueryBuilder {
    private final Map<String, String> data;
    private String tableName;

    private InsertQueryBuilder() {
        this.data = new LinkedHashMap<>();
    }

    public static InsertQueryBuilder builder() {
        return new InsertQueryBuilder();
    }

    public InsertQueryBuilder table(final String tableName) {
        this.tableName = tableName;
        return this;
    }

    public InsertQueryBuilder addData(final String column, final String value) {
        this.data.put(column, value);
        return this;
    }

    public InsertQueryBuilder addData(final List<String> columns, final List<String> values) {
        if (columns.size() != values.size()) {
            throw new PersistenceException("columns size 와 values size 가 같아야 합니다.");
        }
        for (int i = 0; i < columns.size(); i++) {
            addData(columns.get(i), values.get(i));
        }
        return this;
    }

    public String build() {
        if (tableName == null || tableName.isEmpty()) {
            throw new PersistenceException("테이블 이름 없이 insert query 를 만들 수 없습니다.");
        }

        if (data.isEmpty()) {
            throw new PersistenceException("Data 정보 없이 insert query 를 만들 수 없습니다.");
        }

        final StringBuilder builder = new StringBuilder();
        builder.append("insert into ")
                .append(tableName)
                .append(" (")
                .append(String.join(", ", data.keySet()))
                .append(")")
                .append(" values ")
                .append("(")
                .append(String.join(", ", data.values()))
                .append(")");
        return builder.toString();
    }

}
