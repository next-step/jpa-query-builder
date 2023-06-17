package persistence.sql.dialect;

public interface TypeDialect {
    String getSqlType(Class<?> javaType);
}
