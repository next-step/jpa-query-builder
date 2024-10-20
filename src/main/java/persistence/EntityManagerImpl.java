package persistence;

import builder.dml.builder.*;
import builder.dml.DMLBuilderData;
import jdbc.EntityMapper;
import jdbc.JdbcTemplate;

import java.util.List;

public class EntityManagerImpl implements EntityManager {

    private final static String DATA_NOT_EXIST_MESSAGE = "데이터가 존재하지 않습니다. : ";

    private final JdbcTemplate jdbcTemplate;

    public EntityManagerImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public <T> T find(Class<T> clazz, Long id) {
        SelectByIdQueryBuilder queryBuilder = new SelectByIdQueryBuilder();
        return jdbcTemplate.queryForObject(queryBuilder.buildQuery(DMLBuilderData.createDMLBuilderData(clazz, id)), resultSet -> EntityMapper.mapRow(resultSet, clazz));
    }

    @Override
    public <T> List<T> findAll(Class<T> clazz) {
        SelectAllQueryBuilder queryBuilder = new SelectAllQueryBuilder();
        return jdbcTemplate.query(queryBuilder.buildQuery(DMLBuilderData.createDMLBuilderData(clazz)), resultSet -> EntityMapper.mapRow(resultSet, clazz));
    }

    @Override
    public void persist(Object entityInstance) {
        InsertQueryBuilder queryBuilder = new InsertQueryBuilder();
        jdbcTemplate.execute(queryBuilder.buildQuery(DMLBuilderData.createDMLBuilderData(entityInstance)));
    }

    @Override
    public void update(Object entityInstance) {
        confirmEntityDataExist(entityInstance);
        UpdateQueryBuilder queryBuilder = new UpdateQueryBuilder();
        jdbcTemplate.execute(queryBuilder.buildQuery(DMLBuilderData.createDMLBuilderData(entityInstance)));
    }

    @Override
    public void remove(Object entityInstance) {
        DeleteQueryBuilder queryBuilder = new DeleteQueryBuilder();
        jdbcTemplate.execute(queryBuilder.buildQuery(DMLBuilderData.createDMLBuilderData(entityInstance)));
    }

    //조회되는 데이터가 존재하는지 확인한다.
    private void confirmEntityDataExist(Object entityInstance) {
        SelectByIdQueryBuilder queryBuilder = new SelectByIdQueryBuilder();
        try {
            jdbcTemplate.queryForObject(
                    queryBuilder.buildQuery(DMLBuilderData.createDMLBuilderData(entityInstance)),
                    resultSet -> EntityMapper.mapRow(resultSet, entityInstance.getClass())
            );
        } catch (RuntimeException e) {
            throw new RuntimeException(DATA_NOT_EXIST_MESSAGE + entityInstance.getClass().getSimpleName());
        }
    }
}
