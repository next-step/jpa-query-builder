package persistence.meta;

import static persistence.meta.JavaToJdbcFiledMapper.convert;

import jakarta.persistence.Column;
import jakarta.persistence.GenerationType;
import java.lang.reflect.Field;
import java.sql.JDBCType;
import persistence.exception.FieldEmptyException;
import persistence.exception.NotFoundException;
import persistence.exception.NumberRangeException;

public class EntityColumn {
    private static final Integer DEFAULT_VARCHAR_LENGTH = 255;
    private static final Integer VARCHAR_MIN_LENGTH = 1;
    private final String name;
    private final ColumnType columType;
    private final EntityColumnOption option;
    private final String fieldName;
    private Integer length;

    public EntityColumn(Field field) {
        if (field == null) {
            throw new FieldEmptyException();
        }
        this.name = initName(field);
        this.fieldName = field.getName();
        this.columType = initColumType(field);
        this.option = new EntityColumnOption(field);
    }

    private String initName(Field field) {
        final Column column = field.getAnnotation(Column.class);
        if (column == null || column.name().isBlank()) {
            return field.getName();
        }
        return column.name();
    }
    private ColumnType initColumType(Field field) {
        JDBCType jdbcType = convert(field.getType());
        if (jdbcType != JDBCType.VARCHAR) {
            return ColumnType.createColumn(jdbcType);
        }
        return createVarcharType(field);
    }

    private ColumnType createVarcharType(Field field) {
        final Column column = field.getAnnotation(Column.class);
        if (column != null && column.length() < VARCHAR_MIN_LENGTH) {
            throw new NumberRangeException("길이는 1보다 작을 수 없습니다.");
        }
        if (column == null) {
            length = DEFAULT_VARCHAR_LENGTH;
            return ColumnType.createVarchar();
        }
        length = column.length();
        return ColumnType.createVarchar();
    }

    public Object getFieldValue(Object object) {
        try {
            final Class<?> clazz = object.getClass();
            final Field field = clazz.getDeclaredField(fieldName);
            field.setAccessible(true);
            return field.get(object);
        } catch (NoSuchFieldException | IllegalAccessException e ) {
            throw new NotFoundException("해당 필드를 찾을 수 없습니다.");
        }
    }

    public String getName() {
        return name;
    }

    public boolean isPk() {
        return option.isPk();
    }

    public ColumnType getColumType() {
        return columType;
    }

    public int getLength() {
        return length;
    }

    public boolean isVarchar() {
        return columType.isVarchar();
    }

    public boolean isNotNull() {
        return !option.isNullable();
    }

    public GenerationType getGenerationType() {
        return option.getGenerationType();
    }

    public boolean hasGeneratedValue() {
        return option.hasGenerationValue();
    }

    public String getFieldName() {
        return fieldName;
    }
}
