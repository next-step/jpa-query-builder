package persistence.entity;

import java.sql.Connection;
import jdbc.JdbcTemplate;
import persistence.sql.dialect.ColumnType;
import persistence.sql.dml.clause.WherePredicate;
import persistence.sql.dml.clause.operator.EqualOperator;
import persistence.sql.dml.statement.DeleteStatementBuilder;
import persistence.sql.dml.statement.InsertStatementBuilder;
import persistence.sql.dml.statement.SelectStatementBuilder;
import persistence.sql.schema.EntityClassMappingMeta;
import persistence.sql.schema.EntityObjectMappingMeta;

public class EntityManagerImpl implements EntityManager {

    private final ColumnType columnType;
    private final JdbcTemplate jdbcTemplate;

    public EntityManagerImpl(Connection connection, ColumnType columnType) {
        this.jdbcTemplate = new JdbcTemplate(connection);
        this.columnType = columnType;
    }

    @Override
    public <T> T find(Class<T> clazz, Long Id) {
        final EntityClassMappingMeta classMappingMeta = EntityClassMappingMeta.of(clazz, columnType);

        final String selectSql = SelectStatementBuilder.builder()
            .select(clazz, columnType)
            .where(WherePredicate.of(classMappingMeta.getIdFieldColumnName(), Id, new EqualOperator()))
            .build();

        return jdbcTemplate.queryForObject(selectSql, new EntityRowMapper<>(clazz, columnType));
    }

    @Override
    public void persist(Object entity) {
        final InsertStatementBuilder insertStatementBuilder = new InsertStatementBuilder(columnType);
        final String insertSql = insertStatementBuilder.insert(entity);

        jdbcTemplate.execute(insertSql);
    }

    @Override
    public void remove(Object entity) {
        final EntityClassMappingMeta classMappingMeta = EntityClassMappingMeta.of(entity.getClass(), columnType);
        final EntityObjectMappingMeta objectMappingMeta = EntityObjectMappingMeta.of(entity, classMappingMeta);

        final String deleteSql = DeleteStatementBuilder.builder()
            .delete(entity.getClass(), columnType)
            .where(WherePredicate.of(objectMappingMeta.getIdColumnName(), objectMappingMeta.getIdValue(), new EqualOperator()))
            .build();

        jdbcTemplate.execute(deleteSql);
    }
}
