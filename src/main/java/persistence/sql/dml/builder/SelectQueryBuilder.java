package persistence.sql.dml.builder;

import persistence.meta.MetaEntity;

import java.util.List;

public class SelectQueryBuilder<T> {
  private static final String INSERT_SQL_QUERY = "SELECT %s FROM %s;";
  private static final String DELIMITER = ",";
  public String createSelectQuery(T entity) {

    MetaEntity<T> metaEntity = MetaEntity.of(entity);

    String columnClause = getColumnClause(metaEntity);
    return String.format(INSERT_SQL_QUERY, columnClause, metaEntity.getTableName());
  }

  private String getColumnClause(MetaEntity metaEntity) {
    List<String> columns = metaEntity.getEntityFields();

    return String.join(DELIMITER, columns);
  }
}
