package persistence.sql.ddl;

import static persistence.sql.ddl.JavaToJdbcFiledMapper.convert;

import jakarta.persistence.Column;
import java.lang.reflect.Field;
import java.sql.JDBCType;
import persistence.dialect.Dialect;
import persistence.vender.dialect.H2Dialect;
import persistence.exception.FiledEmptyException;

public class EntityColumn {
    private final String name;
    private final ColumnType columType;
    private final EntityColumnOption option;
    private final Dialect direct;

    public EntityColumn(Field field) {
        this(field, new H2Dialect());

    }
    public EntityColumn(Field field, Dialect direct) {
        if (field == null) {
            throw new FiledEmptyException();
        }
        this.name = initName(field);
        this.columType = initColumType(field);
        this.option = new EntityColumnOption(field);
        this.direct = direct;
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
        return generateVarchar(field);
    }

    private ColumnType generateVarchar(Field field) {
        final Column column = field.getAnnotation(Column.class);
        if (column != null && column.length() > 0) {
            return ColumnType.createVarchar(column.length());
        }
        return ColumnType.createVarchar();
    }

    public String createColumQuery() {
        return new StringBuilder(name)
                .append(" ")
                .append(columType.getColumType(direct))
                .append(getNullAble(option))
                .append(getGeneratedType(option))
                .toString();
    }

    private String getNullAble(EntityColumnOption option){
        if (!option.isNullable()) {
            return " " + direct.notNull();
        }
        return "";
    }
    private String getGeneratedType(EntityColumnOption option) {
        if (option.hasGenerationType()) {
            return " " + direct.getGeneratedType(option.getGenerationType());
        }
        return "";
    }

    public String getName() {
        return name;
    }

    public boolean isPk() {
        return option.isPk();
    }
}
