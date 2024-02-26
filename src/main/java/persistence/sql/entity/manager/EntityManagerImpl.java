package persistence.sql.entity.manager;

import jdbc.JdbcTemplate;
import persistence.sql.dml.query.builder.SelectQueryBuilder;
import persistence.sql.dml.repository.RepositoryMapper;
import persistence.sql.entity.EntityMappingTable;
import persistence.sql.entity.model.DomainType;

public class EntityManagerImpl<T> implements EntityManger<T> {

    private final JdbcTemplate jdbcTemplate;
    public EntityManagerImpl(final JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public T find(Class<T> clazz, Long id) {
        final EntityMappingTable entityMappingTable = EntityMappingTable.from(clazz);
        final RepositoryMapper<T> repositoryMapper = new RepositoryMapper<>(clazz);
        final DomainType pkDomainType = entityMappingTable.getPkDomainTypes();

        SelectQueryBuilder selectQueryBuilder = SelectQueryBuilder.from(entityMappingTable);
        return jdbcTemplate.queryForObject(selectQueryBuilder.toSql(), repositoryMapper::mapper);
    }



}
