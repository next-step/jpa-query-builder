package persistence.sql.mapping;

import jakarta.persistence.GenerationType;
import persistence.sql.dialect.Dialect;

import java.lang.reflect.Field;

public class Column {

    private String name;

    private int type;

    private Object value;

    private int length = 255;

    private boolean nullable = true;

    private boolean unique = false;

    private boolean pk = false;

    private GenerationType pkStrategy = null;

    private Column() {}

    public Column(final Field field, final ColumnTypeMapper columnTypeMapper) {
        final jakarta.persistence.Column columnAnnotation = field.getAnnotation(jakarta.persistence.Column.class);
        final String columnName = toColumnName(field, columnAnnotation);
        final int sqlType = columnTypeMapper.toSqlType(field.getType());

        this.name = columnName;
        this.type = sqlType;

        if (columnAnnotation == null) {
            return;
        }

        this.length = columnAnnotation.length();
        this.nullable = columnAnnotation.nullable();
        this.unique = columnAnnotation.unique();
    }

    public Column clone() {
        final Column copy = new Column();
        copy.name = this.name;
        copy.type = this.type;
        copy.value = this.value;
        copy.length = this.length;
        copy.nullable = this.nullable;
        copy.unique = this.unique;
        copy.pk = this.pk;
        copy.pkStrategy = this.pkStrategy;

        return copy;
    }

    protected String toColumnName(final Field field, final jakarta.persistence.Column columnAnnotation) {
        if (columnAnnotation == null || columnAnnotation.name().isBlank()) {
            return field.getName();
        }

        return columnAnnotation.name();
    }

    public String getName() {
        return name;
    }

    public String getSqlType(Dialect dialect) {
        return dialect.convertColumnType(this.type, this.getLength());
    }

    public Object getValue() {
        return value;
    }

    public int getLength() {
        return length;
    }

    public boolean isNullable() {
        return nullable;
    }

    public boolean isNotNull() {
        return !isNullable();
    }

    public boolean isUnique() {
        return unique;
    }

    public boolean isPk() {
        return pk;
    }

    public boolean isIdentifierKey() {
        return this.pk && this.pkStrategy == GenerationType.IDENTITY;
    }

    public void setPk(final boolean pk) {
        this.pk = pk;
    }

    public void setStrategy(final GenerationType strategy) {
        this.pkStrategy = strategy;
    }

}
