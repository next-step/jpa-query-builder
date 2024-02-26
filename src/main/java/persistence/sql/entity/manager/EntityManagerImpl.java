package persistence.sql.entity.manager;

import jdbc.JdbcTemplate;
import persistence.sql.dml.query.builder.InsertQueryBuilder;
import persistence.sql.dml.query.builder.SelectQueryBuilder;
import persistence.sql.dml.repository.RepositoryMapper;
import persistence.sql.entity.EntityMappingTable;
import persistence.sql.entity.model.DomainType;

import java.util.Map;

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

        Map<DomainType, String> whereMap = Map.of(pkDomainType, id.toString());

        SelectQueryBuilder selectQueryBuilder = SelectQueryBuilder.of(entityMappingTable, whereMap);
        return jdbcTemplate.queryForObject(selectQueryBuilder.toSql(), repositoryMapper::mapper);
    }

    @Override
    public void persist(T entity) {
        InsertQueryBuilder insertQueryBuilder = InsertQueryBuilder.from(entity);
        jdbcTemplate.execute(insertQueryBuilder.toSql());
    }
}
