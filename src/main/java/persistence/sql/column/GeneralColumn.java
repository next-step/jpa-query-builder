package persistence.sql.column;

import persistence.sql.Type.NameType;
import persistence.sql.Type.NullableType;
import persistence.sql.dialect.Dialect;

import java.lang.reflect.Field;

public class GeneralColumn implements Column {

    private static final String DEFAULT_COLUMN_FORMAT = "%s %s";

    private final NameType name;
    private final String value;
    private final ColumnType columnType;
    private final NullableType nullable;

    public GeneralColumn(NameType name, ColumnType columnType, NullableType nullable) {
        this(name, null, columnType, nullable);
    }

    private GeneralColumn(NameType name, String value, ColumnType columnType, NullableType nullable) {
        this.name = name;
        this.value = value;
        this.columnType = columnType;
        this.nullable = nullable;
    }

    public static GeneralColumn create(Field field, Dialect dialect) {
        ColumnType columnType = dialect.getColumn(field.getType());
        NameType name = new NameType(field.getName());
        NullableType nullable = new NullableType();
        if (field.isAnnotationPresent(jakarta.persistence.Column.class)) {
            boolean isNullable = field.getAnnotation(jakarta.persistence.Column.class).nullable();
            nullable.update(isNullable);
            String columnName = field.getAnnotation(jakarta.persistence.Column.class).name();
            name.setName(columnName);
        }
        return new GeneralColumn(name, columnType, nullable);
    }

    @Override
    public String getDefinition() {
        return String.format(DEFAULT_COLUMN_FORMAT, name.getValue(), columnType.getColumnDefinition() + nullable.getDefinition());
    }

    @Override
    public boolean isPk() {
        return false;
    }

    @Override
    public String getColumnName() {
        return name.getValue();
    }

}
