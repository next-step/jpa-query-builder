package persistence.sql.ddl.field;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class QueryFields {
    private final QueryField idField;
    private final List<QueryField> columnFields;

    public QueryFields(Field[] fields) {
        this.idField = Arrays.stream(fields)
                .filter(IdField::isMappableField)
                .findFirst()
                .map(IdField::new)
                .orElseThrow(() -> new IllegalArgumentException("@Id가 존재하지 않습니다"));
        this.columnFields = Arrays.stream(fields)
                .filter(ColumnField::isMappableField)
                .map(ColumnField::new)
                .collect(Collectors.toUnmodifiableList());
    }

    public String toSQL() {
        final String idSql = idField.toSQL();
        final String columnSQLs = columnFields.stream()
                .map(QueryField::toSQL)
                .distinct()
                .collect(Collectors.joining("," + System.lineSeparator()));
        return idSql + "," + System.lineSeparator() + columnSQLs;
    }
}
