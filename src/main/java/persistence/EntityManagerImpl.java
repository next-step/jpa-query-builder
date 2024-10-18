package persistence;

import builder.dml.DMLBuilder;
import builder.dml.DMLType;
import jdbc.EntityMapper;
import jdbc.JdbcTemplate;

import java.util.List;

public class EntityManagerImpl implements EntityManager {

    private final static String DATA_NOT_EXIST_MESSAGE = "데이터가 존재하지 않습니다. : ";

    private final JdbcTemplate jdbcTemplate;
    private final DMLBuilder dmlBuilder;

    public EntityManagerImpl(JdbcTemplate jdbcTemplate, DMLBuilder dmlBuilder) {
        this.jdbcTemplate = jdbcTemplate;
        this.dmlBuilder = dmlBuilder;
    }

    @Override
    public <T> T find(Class<T> clazz, Long id) {
        return jdbcTemplate.queryForObject(dmlBuilder.queryBuilder(DMLType.SELECT_BY_ID, clazz, id), resultSet -> EntityMapper.mapRow(resultSet, clazz));
    }

    @Override
    public <T> List<T> findAll(Class<T> clazz) {
        return jdbcTemplate.query(dmlBuilder.queryBuilder(DMLType.SELECT_ALL, clazz), resultSet -> EntityMapper.mapRow(resultSet, clazz));
    }

    @Override
    public void persist(Object entity) {
        jdbcTemplate.execute(dmlBuilder.queryBuilder(DMLType.INSERT, entity));
    }

    @Override
    public void update(Object entityInstance) {
        confirmEntityDataExist(entityInstance);
        jdbcTemplate.execute(dmlBuilder.queryBuilder(DMLType.UPDATE, entityInstance));
    }

    @Override
    public void remove(Object entityInstance) {
        jdbcTemplate.execute(dmlBuilder.queryBuilder(DMLType.DELETE, entityInstance));
    }

    //조회되는 데이터가 존재하는지 확인한다.
    private void confirmEntityDataExist(Object entityInstance) {
        try {
            jdbcTemplate.queryForObject(
                    dmlBuilder.queryBuilder(DMLType.SELECT_BY_ID, entityInstance),
                    resultSet -> EntityMapper.mapRow(resultSet, entityInstance.getClass())
            );
        } catch (RuntimeException e) {
            throw new RuntimeException(DATA_NOT_EXIST_MESSAGE + entityInstance.getClass().getSimpleName());
        }
    }
}
