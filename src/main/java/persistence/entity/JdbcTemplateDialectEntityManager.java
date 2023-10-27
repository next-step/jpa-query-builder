package persistence.entity;

import jdbc.JdbcTemplate;
import jdbc.SimpleEntityRowMapper;
import persistence.sql.dbms.Dialect;
import persistence.sql.dml.DeleteDMLQueryBuilder;
import persistence.sql.dml.InsertDMLQueryBuilder;
import persistence.sql.dml.SelectDMLQueryBuilder;
import persistence.sql.dml.clause.WhereClause;
import persistence.sql.dml.clause.operator.Operator;
import persistence.sql.entitymetadata.model.EntityColumn;
import persistence.sql.entitymetadata.model.EntityTable;

public class JdbcTemplateDialectEntityManager implements EntityManager {
    private JdbcTemplate jdbcTemplate;
    private Dialect dialect;

    public JdbcTemplateDialectEntityManager(JdbcTemplate jdbcTemplate, Dialect dialect) {
        this.jdbcTemplate = jdbcTemplate;
        this.dialect = dialect;
    }

    @Override
    public <T> T find(Class<T> clazz, Long id) {
        EntityTable<T> entityTable = new EntityTable<>(clazz);
        EntityColumn<T, ?> idColumn = entityTable.getIdColumn();

        SelectDMLQueryBuilder<T> selectDMLQueryBuilder = new SelectDMLQueryBuilder<>(dialect, clazz)
                .where(WhereClause.of(idColumn.getDbColumnName(), id, Operator.EQUALS));
        selectDMLQueryBuilder.build();

        return jdbcTemplate.queryForObject(selectDMLQueryBuilder.build(), new SimpleEntityRowMapper<>(entityTable, dialect));
    }

    @Override
    public void persist(Object entity) {
        InsertDMLQueryBuilder<?> insertDMLQueryBuilder = new InsertDMLQueryBuilder<>(dialect, entity);

        jdbcTemplate.execute(insertDMLQueryBuilder.build());
    }

    @Override
    public void remove(Object entity) {
        Class<?> entityClass = entity.getClass();
        EntityTable<?> entityTable = new EntityTable<>(entityClass);
        EntityColumn<Object, Object> idColumn = (EntityColumn<Object, Object>) entityTable.getIdColumn();

        DeleteDMLQueryBuilder<?> deleteDMLQueryBuilder = new DeleteDMLQueryBuilder<>(dialect, entityClass)
                .where(WhereClause.of(idColumn.getDbColumnName(), idColumn.getValue(entity), Operator.EQUALS));

        jdbcTemplate.execute(deleteDMLQueryBuilder.build());
    }
}
