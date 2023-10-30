package persistence.sql.dml.builder;

import java.lang.reflect.Field;
import java.util.List;
import persistence.meta.MetaEntity;

public class SelectQueryBuilder<T> implements QueryBuilder{
  private static final String SELECT_SQL_QUERY = "SELECT %s FROM %s;";
  private static final String SELECT_WHERE_SQL_QUERY = "SELECT %s FROM %s WHERE %s=%s;";
  private static final String DELIMITER = ",";

  public String createSelectQuery(T entity) {

    MetaEntity<T> metaEntity = MetaEntity.of(entity);

    String columnClause = getColumnClause(metaEntity);
    return String.format(SELECT_SQL_QUERY, columnClause, metaEntity.getTableName());
  }

  public String createSelectByFieldQuery(T entity, Field field) {

    MetaEntity<T> metaEntity = MetaEntity.of(entity);
    Object value = getFieldValue(field, entity);
    String dbFieldName = metaEntity.getFieldMapping().get(field.getName());
    String columnClause = getColumnClause(metaEntity);
    return String.format(SELECT_WHERE_SQL_QUERY, columnClause, metaEntity.getTableName(), dbFieldName, value);
  }

  private String getColumnClause(MetaEntity metaEntity) {
    List<String> columns = metaEntity.getEntityColumnsWithId();

    return String.join(DELIMITER, columns);
  }

}
