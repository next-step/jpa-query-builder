package persistence.sql.mapping;

import jakarta.persistence.GenerationType;
import persistence.sql.dialect.Dialect;

public class Column {

    private String name;

    private int type;

    private Value value;

    private int length = 255;

    private boolean nullable = true;

    private boolean unique = false;

    private boolean pk = false;

    private GenerationType pkStrategy = null;

    private Column() {}

    public Column(final String columnName, final int sqlType, final Value value, final int length, final boolean nullable, final boolean unique) {
        this.name = columnName;
        this.type = sqlType;
        this.value = value;
        this.length = length;
        this.nullable = nullable;
        this.unique = unique;
    }

    public Column clone() {
        final Column copy = new Column();
        copy.name = this.name;
        copy.type = this.type;
        copy.value = this.getValue();
        copy.length = this.length;
        copy.nullable = this.nullable;
        copy.unique = this.unique;
        copy.pk = this.pk;
        copy.pkStrategy = this.pkStrategy;

        return copy;
    }

    public String getName() {
        return name;
    }

    public String getSqlType(Dialect dialect) {
        return dialect.convertColumnType(this.type, this.getLength());
    }

    public Value getValue() {
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

    public boolean isNotPk() {
        return !pk;
    }

    public boolean isIdentifierKey() {
        return this.pk && this.pkStrategy == GenerationType.IDENTITY;
    }

    public void setValue(final Value value) {
        this.value = value;
    }

    public void setPk(final boolean pk) {
        this.pk = pk;
    }

    public void setStrategy(final GenerationType strategy) {
        this.pkStrategy = strategy;
    }

}
