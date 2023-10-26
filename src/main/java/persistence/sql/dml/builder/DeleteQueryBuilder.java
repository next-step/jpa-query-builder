package persistence.sql.dml.builder;

import persistence.meta.MetaEntity;

import java.lang.reflect.Field;

public class DeleteQueryBuilder<T> {
  private static final String DELETE_SQL_QUERY = "DELETE FROM %s WHERE %s = %s;";

  public String createDeleteQuery(T entity, Field field) {
    MetaEntity<T> metaEntity = MetaEntity.of(entity);
    Object value = getFieldValue(field, entity);
    String dbFieldName = metaEntity.getFieldMapping().get(field.getName());
    return String.format(DELETE_SQL_QUERY, metaEntity.getTableName(), dbFieldName, value);
  }

  private String getFieldValue(Field field, T entity) {
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
