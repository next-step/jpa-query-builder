package persistence.sql.view;

import java.lang.reflect.Field;
import java.util.List;
import java.util.stream.Collectors;

import static persistence.sql.view.StringConstant.DELIMITER;

public final class ColumnNames {
    private ColumnNames() {}

    public static String render(List<Field> fields) {
        return fields.stream()
                .map(ColumnName::render)
                .collect(Collectors.joining(DELIMITER));
    }
}
