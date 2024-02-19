package persistence.sql.query;

import jakarta.persistence.GenerationType;

import java.lang.reflect.Field;
import java.sql.Types;
import java.util.Map;

public class Column {

    public static final Map<String, Integer> COLUMN_TYPES = Map.of(
            "STRING", Types.VARCHAR,
            "INTEGER", Types.INTEGER,
            "LONG", Types.BIGINT);

    private final String name;

    private final int type;

    private Object value;

    private boolean isPk = false;

    private GenerationType pkStrategy = null;

    private int length = 255;

    private boolean isNullable = true;

    private boolean isUnique = false;

    public Column(String name, int type) {
        this(name, type, null);
    }

    public Column(String name, int type, Object value) {
        this.name = name;
        this.type = type;
        this.value = value;
    }

    public Column(Column column) {
        this(column.name, column.type, column.value);
        this.value = column.value;
        this.isPk = column.isPk;
        this.pkStrategy = column.pkStrategy;
        this.length = column.length;
        this.isNullable = column.isNullable;
        this.isUnique = column.isUnique;
    }

    public String getName() {
        return name;
    }

    public int getType() {
        return type;
    }

    public Object getValue() {
        return value;
    }

    public boolean isPk() {
        return isPk;
    }

    public GenerationType getPkStrategy() {
        return pkStrategy;
    }

    public int getLength() {
        return length;
    }

    public boolean isNullable() {
        return isNullable;
    }

    public boolean isUnique() {
        return isUnique;
    }

    public static class Builder {

        private String name;

        private int type;

        private Object value;

        private boolean isPk = false;

        private GenerationType pkStrategy = null;

        private int length = 255;

        private boolean isNullable = true;

        private boolean isUnique = false;

        public Builder() {}

        public Builder(String name, int type) {
            this.setName(name)
                    .setType(type);
        }

        public Builder(Field field) {
            final jakarta.persistence.Column columnAnnotation = field.getAnnotation(jakarta.persistence.Column.class);
            final int sqlType = toSqlType(field);
            final String columnName = toColumnName(field, columnAnnotation);

            this.setName(columnName)
                    .setType(sqlType);

            if (columnAnnotation == null) return;

            this.setLength(columnAnnotation.length())
                    .setNullable(columnAnnotation.nullable())
                    .setUnique(columnAnnotation.unique());
        }

        private String toColumnName(final Field field, final jakarta.persistence.Column columnAnnotation) {
            if (columnAnnotation == null || columnAnnotation.name().isBlank()) return field.getName();

            return columnAnnotation.name();
        }

        private int toSqlType(final Field field) {
            return COLUMN_TYPES.getOrDefault(field.getType().getSimpleName().toUpperCase(), Types.VARCHAR);
        }

        public Builder setName(final String name) {
            this.name = name;
            return this;
        }

        public Builder setType(final int type) {
            this.type = type;
            return this;
        }

        public Builder setValue(final Object value) {
            this.value = value;
            return this;
        }

        public Builder setPk(final boolean pk) {
            isPk = pk;
            return this;
        }

        public Builder setPkStrategy(final GenerationType pkStrategy) {
            this.pkStrategy = pkStrategy;
            return this;
        }

        public Builder setLength(final int length) {
            this.length = length;
            return this;
        }

        public Builder setNullable(final boolean nullable) {
            isNullable = nullable;
            return this;
        }

        public Builder setUnique(final boolean unique) {
            isUnique = unique;
            return this;
        }

        public Column build() {
            final Column column = new Column(name, type, value);

            column.isPk = this.isPk;
            column.pkStrategy = this.pkStrategy;
            column.length = this.length;
            column.isNullable = this.isNullable;
            column.isUnique = this.isUnique;

            return column;
        }
    }

}
