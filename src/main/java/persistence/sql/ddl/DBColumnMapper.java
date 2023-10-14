package persistence.sql.ddl;

public interface DBColumnMapper {
    String getColumnName(Class<?> clazz);
}
