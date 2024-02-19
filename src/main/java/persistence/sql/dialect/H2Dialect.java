package persistence.sql.dialect;

import jakarta.persistence.GenerationType;
import persistence.sql.query.Column;

import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

public class H2Dialect implements Dialect {

    private final String notNull = "NOT NULL";

    private final String autoIncrement = "AUTO_INCREMENT";

    private final String primaryKey = "PRIMARY KEY";

    private final String unique = "UNIQUE";

    @Override
    public String convertColumnType(final int type, final int length) {
        return switch (type) {
            case Types.VARCHAR -> "VARCHAR(" + length + ")";
            case Types.INTEGER -> "INTEGER";
            case Types.BIGINT -> "BIGINT";
            default -> throw new DialectException("NotFount ColumnType for " + type + " in H2Dialect");
        };
    }

    @Override
    public String toDialectKeywords(final Column column) {
        List<String> keywords = new ArrayList<>();
        if (!column.isNullable()) {
            keywords.add(notNull);
        }
        if (column.isPk()) {
            if (column.getPkStrategy() == GenerationType.IDENTITY) {
                keywords.add(autoIncrement);
            }
            keywords.add(primaryKey);
        } else if (column.isUnique()) {
            keywords.add(unique);
        }

        return String.join(" ", keywords);
    }

}
