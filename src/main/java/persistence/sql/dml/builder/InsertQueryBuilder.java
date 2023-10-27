package persistence.sql.dml.builder;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import persistence.meta.MetaEntity;

public class InsertQueryBuilder<T> {
  private static final String INSERT_SQL_QUERY = "INSERT INTO %s (%s) values (%s);";
  private static final String DELIMITER = ",";

  public String createInsertQuery(T entity) {

    MetaEntity<T> metaEntity = MetaEntity.of(entity);

    String valueClause = getValueClause(metaEntity, entity);
    String columnClause = getColumnClause(metaEntity);
    return String.format(INSERT_SQL_QUERY, metaEntity.getTableName(), columnClause, valueClause);
  }

  private String getValueClause(MetaEntity metaEntity, T entity) {
    List<String> columns = metaEntity.getEntityFields();
    return String.join(DELIMITER, extractValuesFromEntity(columns, entity));
  }

  private String getColumnClause(MetaEntity metaEntity) {
    List<String> columns = metaEntity.getEntityColumns();

    return String.join(DELIMITER, columns);
  }

  private List<String> extractValuesFromEntity(List<String> columns, T entity) {
    List<String> values = new ArrayList<>();

    for (String column : columns) {
      Field field = getField(column, entity);
      field.setAccessible(true);
      values.add(getFieldValue(field, entity));
    }
    return values;
  }

  private Field getField(String column, T entity) {
    try {
      return entity.getClass().getDeclaredField(column);
    } catch (NoSuchFieldException e) {
      throw new RuntimeException(e);
    }
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
