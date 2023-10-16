package persistence.sql.ddl;

import jakarta.persistence.Column;
import java.lang.reflect.Field;
import java.sql.JDBCType;

public class EntityColumn {
    private final String name;
    private final JDBCType jdbcType;
    private final EntityColumnOption option;
    private static final JavaToJdbcFiledMapper mapper = new JavaToJdbcFiledMapper();
    public EntityColumn(Field field) {
        if (field == null) {
            throw new IllegalArgumentException("필드가 비어 있으면 안됩니다.");
        }
        this.name = initName(field);
        this.jdbcType = mapper.convert(field.getType());
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
