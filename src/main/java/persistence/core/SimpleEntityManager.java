package persistence.core;

import jdbc.JdbcTemplate;
import persistence.exception.NotMappingEntityClassException;
import persistence.sql.dml.DmlGenerator;
import persistence.sql.dml.where.EntityCertification;
import persistence.sql.dml.where.FetchWhereQuery;
import persistence.sql.dml.where.WhereQuery;
import persistence.sql.dml.where.WhereQueryBuilder;

import java.lang.reflect.Field;
import java.util.List;

public class SimpleEntityManager implements EntityManager {

    private final JdbcTemplate jdbcTemplate;
    private final DmlGenerator dmlGenerator;

    private final EntityMetadataModelHolder entityMetadataModelHolder;

    public SimpleEntityManager(
            JdbcTemplate jdbcTemplate,
            DmlGenerator dmlGenerator,
            EntityMetadataModelHolder entityMetadataModelHolder) {
        this.jdbcTemplate = jdbcTemplate;
        this.dmlGenerator = dmlGenerator;
        this.entityMetadataModelHolder = entityMetadataModelHolder;
    }

    @Override
    public <T> T find(Class<T> clazz, Long id) {
        String sql = dmlGenerator.findById(clazz, id);

        return jdbcTemplate.queryForObject(sql, new EntityRowMapper<>(clazz, entityMetadataModelHolder));
    }

    @Override
    public void persist(Object entity) {
        String sql = dmlGenerator.insert(entity);
        jdbcTemplate.execute(sql);
    }

    @Override
    public void remove(Object entity) {
        FetchWhereQuery fetchPkWhereQuery = createPkFetchWhereQueryBuilder(entity);
        String sql = dmlGenerator.delete(entity.getClass(), fetchPkWhereQuery);
        jdbcTemplate.execute(sql);
    }

    private FetchWhereQuery createPkFetchWhereQueryBuilder(Object entity) {
        EntityMetadataModel entityMetadataModel = entityMetadataModelHolder.getEntityMetadataModel(entity.getClass());

        EntityColumn primaryKeyColumn = entityMetadataModel.getPrimaryKeyColumn();

        try {
            Field declaredField = entity.getClass().getDeclaredField(primaryKeyColumn.getName());
            declaredField.setAccessible(true);

            EntityCertification<?> certification = EntityCertification.certification(entityMetadataModel.getType());
            WhereQuery idEqual = certification.equal("id", declaredField.get(entity));

            return WhereQueryBuilder.builder().and(List.of(idEqual));
        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new NotMappingEntityClassException("not found pk field on " + entity.getClass() ,e);
        }
    }
}
