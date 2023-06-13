package persistence.sql.ddl;

import jakarta.persistence.Id;

import java.lang.reflect.Field;

public class ColumnBuilder {
    private static final String BLANK = "";
    private final Field field;

    public ColumnBuilder(Field field) {this.field = field;}

    public String build() {
        StringBuilder builder = new StringBuilder();
        builder.append(getColumnName());
        builder.append(" ");
        builder.append(getSqlType());
        builder.append(getPKSql());
        return builder.toString();
    }

    private String getColumnName() {
        return field.getName();
    }

    private String getSqlType() {
        return TypeMapper.toSqlType(field.getType());
    }

    private String getPKSql() {
        final String PK_SQL = " PRIMARY KEY";
        return field.isAnnotationPresent(Id.class)
                ? PK_SQL
                : BLANK;
    }
}
