package persistence.sql.util;

import java.lang.reflect.Field;
import java.util.List;
import java.util.stream.Collectors;

import static persistence.sql.util.StringConstant.DELIMITER;

public final class ColumnNames {
    private ColumnNames() {}

    public static String build(List<Field> fields) {
        return fields.stream()
                .map(ColumnName::build)
                .collect(Collectors.joining(DELIMITER));
    }
}
