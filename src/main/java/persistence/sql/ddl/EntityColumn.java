package persistence.sql.ddl;

import jakarta.persistence.Column;
import java.lang.reflect.Field;
import java.sql.JDBCType;
import persistence.exception.FiledEmptyException;

public class EntityColumn {
    private final String name;
    private final JDBCType jdbcType;
    private final EntityColumnOption option;

    public EntityColumn(Field field) {
        if (field == null) {
            throw new FiledEmptyException();
        }
        this.name = initName(field);
        this.jdbcType = JavaToJdbcFiledMapper.convert(field.getType());
        this.option = new EntityColumnOption(field);
    }

    private String initName(Field field) {
        final Column column = field.getAnnotation(Column.class);
        if (column == null || column.name().isBlank()) {
            return field.getName();
        }
        return column.name();
    }

    public String getName() {
        return name;
    }

    public JDBCType getJdbcType() {
        return jdbcType;
    }

    public boolean isPk() {
        return option.isPk();
    }

    public EntityColumnOption getOption() {
        return option;
    }

}
