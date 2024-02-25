package persistence.sql.dml.repository;

import jdbc.JdbcTemplate;
import persistence.sql.dml.exception.NotFoundIdException;
import persistence.sql.dml.query.builder.DeleteQueryBuilder;
import persistence.sql.dml.query.builder.InsertQueryBuilder;
import persistence.sql.dml.query.builder.SelectQueryBuilder;
import persistence.sql.entity.EntityMappingTable;
import persistence.sql.entity.model.DomainType;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public class RepositoryImpl<T> implements Repository<T> {

    private final JdbcTemplate jdbcTemplate;
    private final EntityMappingTable entityMappingTable;
    private RepositoryMapper<T> repositoryMapper;
    private final DomainType pkDomainType;


    public RepositoryImpl(final JdbcTemplate jdbcTemplate,
                          final Class<T> clazz) {
        this.jdbcTemplate = jdbcTemplate;
        this.entityMappingTable = EntityMappingTable.from(clazz);
        this.repositoryMapper = new RepositoryMapper<>(clazz);
        this.pkDomainType = entityMappingTable.getPkDomainTypes();
    }

    @Override
    public List<T> findAll() {
        SelectQueryBuilder selectQueryBuilder = SelectQueryBuilder.from(entityMappingTable);
        return jdbcTemplate.query(selectQueryBuilder.toSql(), repositoryMapper::mapper);
    }

    @Override
    public Optional<T> findById(Long id) {
        Map<DomainType, String> where = Map.of(pkDomainType, id.toString());

        SelectQueryBuilder selectQueryBuilder = SelectQueryBuilder.of(entityMappingTable, where);
        return Optional.ofNullable(jdbcTemplate.queryForObject(selectQueryBuilder.toSql(), repositoryMapper::mapper));
    }

    @Override
    public T save(T t) {
        InsertQueryBuilder insertQueryBuilder = InsertQueryBuilder.from(t);
        jdbcTemplate.execute(insertQueryBuilder.toSql());
        return t;
    }

    @Override
    public void deleteAll() {
        DeleteQueryBuilder deleteQueryBuilder = DeleteQueryBuilder.from(entityMappingTable.getTableName());

        jdbcTemplate.execute(deleteQueryBuilder.toSql());
    }

    @Override
    public void deleteById(Long id) {
        Map<DomainType, String> where = Map.of(pkDomainType, id.toString());

        DeleteQueryBuilder deleteQueryBuilder = DeleteQueryBuilder.of(entityMappingTable.getTableName(), where);

        jdbcTemplate.execute(deleteQueryBuilder.toSql());
    }
}
