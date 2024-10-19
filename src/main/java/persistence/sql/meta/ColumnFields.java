package persistence.sql.meta;

import org.h2.util.StringUtils;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class ColumnFields {

    private final List<ColumnField> columnFields;

    public ColumnFields(Class<?> clazz) {
        this.columnFields = extract(clazz);
    }

    private List<ColumnField> extract(Class<?> clazz) {
        return Arrays.stream(clazz.getDeclaredFields())
                .map(ColumnField::new)
                .filter(ColumnField::isNotTransient)
                .collect(Collectors.toList());
    }

    public List<ColumnField> getColumnFields() {
        return columnFields;
    }

    public List<String> getDeclaredFieldNames() {
        return columnFields
                .stream().map(ColumnField::getName).collect(Collectors.toList());
    }

    public List<ColumnField> getPrimary() {
        if(columnFields.stream().noneMatch(ColumnField::isPrimaryKey)) {
            throw new IllegalArgumentException("Entity에 Id로 정의된 column이 존재하지 않습니다.");
        }

        return columnFields.stream().filter(ColumnField::isPrimaryKey).collect(Collectors.toList());
    }

    public String getColumnClause() {
        return getDeclaredFieldNames().stream()
                .collect(Collectors.joining(", "));
    }

    public String valueClause(Object object) {
        List<Field> fields = columnFields.stream().map(ColumnField::getField).collect(Collectors.toList());
        fields.forEach(field -> field.setAccessible(true));
        return fields.stream().map(field-> {
            try {
                return String.valueOf(field.get(object));
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        }).collect(Collectors.joining(", "));
    }
}
