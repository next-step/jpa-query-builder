package persistence.sql.mapping;

import java.io.Serializable;

public class Value implements Serializable {

    private final Class<?> originalType;

    private final int sqlType;

    private Object value;

    private String valueClause;

    public Value(final Class<?> originalType, final int sqlType) {
        this(originalType, sqlType, null, null);
    }

    public Value(final Class<?> originalType, final int sqlType, final Object value, final String valueClause) {
        this.originalType = originalType;
        this.sqlType = sqlType;
        this.value = value;
        this.valueClause = valueClause;
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

    public String getValueClause() {
        return this.valueClause;
    }

    public void setValue(final Object value) {
        this.value = value;
    }

    public void setValueClause(final String valueClause) {
        this.valueClause = valueClause;
    }

    public Value clone() {
        return new Value(this.originalType, this.sqlType, this.value, this.valueClause);
    }
}
