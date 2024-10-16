package persistence.sql.clause;

import persistence.sql.data.ClauseType;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;

public interface Clause {
    boolean supported(ClauseType clauseType);

    String column();
    String value();
    String clause();
    static String toColumnValue(Object value) {
        if (value == null) {
            return null;
        }

        if (value instanceof String) {
            return "'" + value + "'";
        }
        return value.toString();
    }

    static Object extractValue(Field field, Object value) {
        try {
            field.setAccessible(true);
            return field.get(value);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    static List<Clause> filterByClauseType(Clause[] clauses, ClauseType clauseType) {
        return Arrays.stream(clauses)
                .filter(clause -> clause.supported(clauseType))
                .toList();
    }
}
