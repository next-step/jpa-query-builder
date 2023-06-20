package persistence.sql.ddl.column;

import jakarta.persistence.Column;
import jakarta.persistence.Id;
import jakarta.persistence.Transient;
import persistence.sql.base.ColumnName;
import persistence.sql.ddl.column.option.ColumnOptionStrategy;
import persistence.sql.ddl.column.option.IdOptionStrategy;
import persistence.sql.ddl.column.option.NoneOptionStrategy;
import persistence.sql.ddl.column.option.OptionStrategy;
import persistence.sql.ddl.column.type.Dialect;
import persistence.sql.ddl.column.type.H2Dialect;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class DdlColumn {
    private static final String LENGTH_FORMAT = "%s(%d)";
    private static final Integer DEFAULT_LENGTH = 255;

    private final Field field;
    private final Column column;
    private final ColumnName columnName;
    private final Dialect dialect;
    private final List<OptionStrategy> optionStrategies;

    public DdlColumn(
            Field field,
            Column column,
            ColumnName columnName,
            Dialect dialect,
            List<OptionStrategy> optionStrategies) {
        validate(field);
        this.field = field;
        this.column = column;
        this.columnName = columnName;
        this.dialect = dialect;
        this.optionStrategies = optionStrategies;
    }

    private void validate(Field field) {
        if (field.isAnnotationPresent(Transient.class)) {
            throw new IllegalArgumentException("@Transient 은 컬럼을 생성할 수 없습니다");
        }
    }

    public static DdlColumn of(Field field) {
        return new DdlColumn(
                field,
                field.getAnnotation(Column.class),
                new ColumnName(field),
                new H2Dialect(),
                List.of(new ColumnOptionStrategy(), new IdOptionStrategy())
        );
    }

    public static List<DdlColumn> ofList(Class<?> clazz) {
        Field[] declaredFields = clazz.getDeclaredFields();
        List<Field> columnFields = new ArrayList<>();
        columnFields.add(getIdField(declaredFields));
        columnFields.addAll(getBasicFields(declaredFields));

        return columnFields.stream()
                .map(DdlColumn::of)
                .collect(Collectors.toList());
    }

    private static Field getIdField(Field[] declaredFields) {
        return Arrays.stream(declaredFields)
                .filter(field -> field.isAnnotationPresent(Id.class))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("@Id 어노테이션이 선언된 필드가 존재하지 않습니다."));
    }

    private static List<Field> getBasicFields(Field[] declaredFields) {
        return Arrays.stream(declaredFields)
                .filter(field -> !field.isAnnotationPresent(Id.class))
                .filter(filed -> !filed.isAnnotationPresent(Transient.class))
                .collect(Collectors.toList());
    }

    public String name() {
        return columnName.name();
    }

    public String type() {
        Class<?> fieldType = field.getType();
        String columnType = dialect.columnType(fieldType);

        if (fieldType.equals(String.class)) {
            return addLength(columnType);
        }

        return columnType;
    }

    private String addLength(String columnType) {
        if (column == null) {
            return String.format(LENGTH_FORMAT, columnType, DEFAULT_LENGTH);
        }
        return String.format(LENGTH_FORMAT, columnType, column.length());
    }

    public String options() {
        OptionStrategy optionStrategy = optionStrategies.stream()
                .filter(strategy -> strategy.supports(field))
                .findFirst()
                .orElse(NoneOptionStrategy.INSTANCE);

        return optionStrategy.options(field);
    }
}
