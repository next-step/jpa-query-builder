package persistence.sql.dml;

import persistence.exception.PersistenceException;

import java.util.List;

public class DeleteQueryBuilder {
    private final WhereClauseBuilder whereClauseBuilder;
    private String tableName;

    private DeleteQueryBuilder() {
        this.whereClauseBuilder = WhereClauseBuilder.builder();
    }

    public static DeleteQueryBuilder builder() {
        return new DeleteQueryBuilder();
    }

    public DeleteQueryBuilder table(final String tableName) {
        this.tableName = tableName;
        return this;
    }

    public DeleteQueryBuilder where(final String column, final String data) {
        this.whereClauseBuilder.and(column, data);
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
                .append(whereClauseBuilder.build());
        return builder.toString();
    }
}
