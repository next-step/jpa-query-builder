package persistence.sql.mapping;

import java.io.Serializable;

public class Value implements Serializable {

    private final Class<?> originalType;

    private final int sqlType;

    private Object value;

    public Value(final Class<?> originalType, final int sqlType) {
        this(originalType, sqlType, null);
    }

    public Value(final Class<?> originalType, final int sqlType, final Object value) {
        this.originalType = originalType;
        this.sqlType = sqlType;
        this.value = value;
    }

    public Class<?> getOriginalType() {
        return this.originalType;
    }

    public int getSqlType() {
        return this.sqlType;
    }

    public Object getValue() {
        return this.value;
    }

    public void setValue(final Object value) {
        this.value = value;
    }

    public Value clone() {
        return new Value(this.originalType, this.sqlType, this.value);
    }

}
