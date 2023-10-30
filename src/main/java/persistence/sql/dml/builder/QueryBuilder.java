package persistence.sql.dml.builder;

import java.lang.reflect.Field;

public interface QueryBuilder<T> {
  default String getFieldValue(Field field, T entity) {
    try {
      Object value = field.get(entity);
      if (value.getClass().equals(String.class)) {
        return "'" + value.toString() + "'";
      }
      return value.toString();
    } catch (IllegalAccessException e) {
      throw new RuntimeException(e);
    }
  }
}
