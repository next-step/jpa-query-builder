package persistence.sql.dml;

import persistence.exception.PersistenceException;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class WhereClauseBuilder {
    private final Map<String, String> whereData;

    private WhereClauseBuilder() {
        this.whereData = new LinkedHashMap<>();
    }

    public static WhereClauseBuilder builder() {
        return new WhereClauseBuilder();
    }

    public WhereClauseBuilder and(final String column, final String data) {
        this.whereData.put(column, data);
        return this;
    }

    public WhereClauseBuilder and(final List<String> columns, final List<String> data) {
        if (columns.size() != data.size()) {
            throw new PersistenceException("columns size 와 data size 가 같아야 합니다.");
        }
        for (int i = 0; i < columns.size(); i++) {
            and(columns.get(i), data.get(i));
        }
        return this;
    }


    public String build() {
        if (whereData.isEmpty()) {
            return "";
        }
        final StringBuilder builder = new StringBuilder();
        builder.append(" where ");
        final List<String> columns = new ArrayList<>(whereData.keySet());
        for (int i = 0; i < columns.size(); i++) {
            if (i > 0) {
                builder.append(" and ");
            }
            builder.append(columns.get(i))
                    .append("=")
                    .append(whereData.get(columns.get(i)));
        }
        return builder.toString();
    }

}
