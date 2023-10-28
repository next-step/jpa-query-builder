package persistence.sql.ddl;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import persistence.sql.Dialect;
import persistence.sql.JdbcTypeJavaClassMappings;
import persistence.sql.SQLEscaper;
import persistence.sql.TableSQLMapper;

import java.lang.reflect.Field;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class FieldStatementGenerator implements FieldSQLGenerator {

    private final Dialect dialect;

    public FieldStatementGenerator(Dialect dialect) {
        this.dialect = dialect;
    }

    public String generate(Field field) {
        return Stream.of(
                SQLEscaper.escapeNameByBacktick(TableSQLMapper.getColumnName(field)),
                getColumnType(field),
                getNullableCondition(field),
                getGenerationType(field)
            )
            .filter(Objects::nonNull)
            .collect(Collectors.joining(" "));
    }

    String getNullableCondition(Field field) {
        if (field.isAnnotationPresent(Id.class)) {
            return "NOT NULL";
        }

        if (!field.isAnnotationPresent(Column.class)) {
            return null;
        }

        Column column = field.getAnnotation(Column.class);
        if (column.nullable()) {
            return null;
        } else {
            return "NOT NULL";
        }
    }

    String getGenerationType(Field field) {
        if (!field.isAnnotationPresent(GeneratedValue.class)) {
            return null;
        }

        GeneratedValue annotation = field.getAnnotation(GeneratedValue.class);
        if (annotation.strategy() != GenerationType.IDENTITY) {
            return null;
        }

        return "AUTO_INCREMENT";
    }

    public String getColumnType(Field field) {
        return this.dialect.get(JdbcTypeJavaClassMappings.getJavaClassToJdbcCode(field.getType()));
    }
}
