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

    public GeneralColumn(NameType name, String value, ColumnType columnType, NullableType nullable) {
        this.name = name;
        this.value = value;
        this.columnType = columnType;
        this.nullable = nullable;
    }

    @Override
    public String getDefinition() {
        return String.format(DEFAULT_COLUMN_FORMAT, name.getValue(), columnType.getColumnDefinition() + nullable.getDefinition());
    }

    @Override
    public PkColumn convertPk(Field field, Dialect dialect) {
        return PkColumn.of(this, field, dialect);
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
