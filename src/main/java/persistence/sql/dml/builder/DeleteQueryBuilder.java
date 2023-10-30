package persistence.sql.dml.builder;

import java.lang.reflect.Field;
import persistence.meta.MetaEntity;

public class DeleteQueryBuilder<T> implements QueryBuilder<T>{
  private static final String DELETE_SQL_QUERY = "DELETE FROM %s WHERE %s = %s;";

  public String createDeleteQuery(T entity, Field field) {
    MetaEntity<T> metaEntity = MetaEntity.of(entity);
    Object value = getFieldValue(field, entity);
    String dbFieldName = metaEntity.getFieldMapping().get(field.getName());
    return String.format(DELETE_SQL_QUERY, metaEntity.getTableName(), dbFieldName, value);
  }
}
