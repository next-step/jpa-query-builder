package persistence.entity;

import jdbc.JdbcRowMapper;
import jdbc.JdbcTemplate;
import persistence.meta.MetaDataColumn;
import persistence.meta.MetaEntity;
import persistence.sql.dml.builder.DeleteQueryBuilder;
import persistence.sql.dml.builder.InsertQueryBuilder;
import persistence.sql.dml.builder.SelectQueryBuilder;

public class JdbcEntityManager implements EntityManager {
  private final JdbcTemplate jdbcTemplate;

  public JdbcEntityManager(JdbcTemplate jdbcTemplate) {

    this.jdbcTemplate = jdbcTemplate;
  }

  @Override
  public <T> T find(Class<T> clazz, Long id) {
    MetaEntity<T> metaEntity = MetaEntity.of(clazz);

    MetaDataColumn primaryKeyColumn = metaEntity.getPrimaryKeyColumn();
    String targetColumn = primaryKeyColumn.getSimpleName();

    SelectQueryBuilder selectQueryBuilder = new SelectQueryBuilder();
    String query = selectQueryBuilder.createSelectByFieldQuery(metaEntity.getColumnClauseWithId(), metaEntity.getTableName(), targetColumn, id);

    return jdbcTemplate.queryForObject(query, new JdbcRowMapper<>(metaEntity));
  }

  @Override
  public void persist(Object entity) {
    MetaEntity<?> metaEntity = MetaEntity.of(entity.getClass());

    InsertQueryBuilder insertQueryBuilder = new InsertQueryBuilder();
    String query = insertQueryBuilder.createInsertQuery(metaEntity.getTableName(), metaEntity.getColumnClause(), metaEntity.getValueClause(entity));

    jdbcTemplate.execute(query);
  }

  @Override
  public void remove(Object entity) {
    MetaEntity<?> metaEntity = MetaEntity.of(entity.getClass());

    MetaDataColumn primaryKeyColumn = metaEntity.getPrimaryKeyColumn();
    String targetColumn = primaryKeyColumn.getSimpleName();
    Object fieldValue = primaryKeyColumn.getFieldValue(entity);

    DeleteQueryBuilder deleteQueryBuilder = new DeleteQueryBuilder();
    String query = deleteQueryBuilder.createDeleteQuery(metaEntity.getTableName(), targetColumn, fieldValue);

    jdbcTemplate.execute(query);
  }

}
