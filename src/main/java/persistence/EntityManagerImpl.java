package persistence;

import builder.QueryBuilderDML;
import jdbc.EntityMapper;
import jdbc.JdbcTemplate;

import java.util.List;

public class EntityManagerImpl implements EntityManager {

    private final static String DATA_NOT_EXIST_MESSAGE = "데이터가 존재하지 않습니다. : ";

    private final JdbcTemplate jdbcTemplate;
    private final QueryBuilderDML queryBuilderDML;

    public EntityManagerImpl(JdbcTemplate jdbcTemplate, QueryBuilderDML queryBuilderDML) {
        this.jdbcTemplate = jdbcTemplate;
        this.queryBuilderDML = queryBuilderDML;
    }

    @Override
    public <T> T find(Class<T> clazz, Long id) {
        return jdbcTemplate.queryForObject(queryBuilderDML.buildFindByIdQuery(clazz, id), resultSet -> EntityMapper.mapRow(resultSet, clazz));
    }

    @Override
    public <T> List<T> findAll(Class<T> clazz) {
        return jdbcTemplate.query(queryBuilderDML.buildFindAllQuery(clazz), resultSet -> EntityMapper.mapRow(resultSet, clazz));
    }

    @Override
    public Object persist(Object entity) {
        jdbcTemplate.execute(queryBuilderDML.buildInsertQuery(entity));
        return entity;
    }

    @Override
    public Object update(Object entityInstance) {
        confirmEntityDataExist(entityInstance);
        jdbcTemplate.execute(queryBuilderDML.buildUpdateQuery(entityInstance));
        return entityInstance;
    }

    @Override
    public void remove(Object entityInstance) {
        jdbcTemplate.execute(queryBuilderDML.buildDeleteObjectQuery(entityInstance));
    }

    //조회되는 데이터가 존재하는지 확인한다.
    private void confirmEntityDataExist(Object entityInstance) {
        try {
            jdbcTemplate.queryForObject(
                    queryBuilderDML.buildFindObjectQuery(entityInstance),
                    resultSet -> EntityMapper.mapRow(resultSet, entityInstance.getClass())
            );
        } catch (RuntimeException e) {
            throw new RuntimeException(DATA_NOT_EXIST_MESSAGE + entityInstance.getClass().getSimpleName());
        }
    }
}
