package persistence.sql.dialect;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import persistence.sql.column.ColumnType;
import persistence.sql.column.MysqlColumnType;
import persistence.sql.column.IdGeneratedStrategy;
import persistence.sql.column.MysqlIdGeneratedStrategy;

import java.lang.reflect.Field;

public class MysqlDialect implements Dialect {

    @Override
    public ColumnType getColumn(Class<?> javaType) {
        return MysqlColumnType.convertToSqlColumnType(javaType);
    }

    @Override
    public IdGeneratedStrategy getIdGeneratedStrategy(GenerationType strategy) {
        return MysqlIdGeneratedStrategy.from(strategy);
    }

    @Override
    public boolean isNotAutoIncrement(Field field) {
        if (field.isAnnotationPresent(GeneratedValue.class)) {
            IdGeneratedStrategy idGeneratedStrategy = getIdGeneratedStrategy(field.getAnnotation(GeneratedValue.class).strategy());
            return !idGeneratedStrategy.isAutoIncrement();
        }
        return true;
    }
}
