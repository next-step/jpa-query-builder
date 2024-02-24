package persistence.sql.dml.repository;

import jdbc.JdbcTemplate;
import persistence.sql.dml.query.builder.DeleteQueryBuilder;
import persistence.sql.dml.query.builder.InsertQueryBuilder;
import persistence.sql.dml.query.builder.SelectQueryBuilder;
import persistence.sql.entity.model.DomainType;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public class RepositoryImpl<T> extends Repository<T> {

    public RepositoryImpl(JdbcTemplate jdbcTemplate, Class<T> clazz) {
        super(jdbcTemplate, clazz);
    }

    @Override
    public List<T> findAll() {
        SelectQueryBuilder selectQueryBuilder = SelectQueryBuilder.from(entityMappingTable);
        return super.jdbcTemplate.query(selectQueryBuilder.toSql(), this::mapper);
    }

    @Override
    Optional<T> findById(Long id) {
        Map<DomainType, String> where = Map.of(pkDomainType, id.toString());

        SelectQueryBuilder selectQueryBuilder = SelectQueryBuilder.of(entityMappingTable, where);
        return Optional.ofNullable(super.jdbcTemplate.queryForObject(selectQueryBuilder.toSql(), this::mapper));
    }

    @Override
    T save(T t) {
        InsertQueryBuilder insertQueryBuilder = InsertQueryBuilder.from(t);
        jdbcTemplate.execute(insertQueryBuilder.toSql());
        return t;
    }

    @Override
    void deleteAll() {
        DeleteQueryBuilder deleteQueryBuilder = DeleteQueryBuilder.from(entityMappingTable.getTableName());

        jdbcTemplate.execute(deleteQueryBuilder.toSql());
    }

    @Override
    void deleteById(Long id) {
        Map<DomainType, String> where = Map.of(pkDomainType, id.toString());

        DeleteQueryBuilder deleteQueryBuilder = DeleteQueryBuilder.of(entityMappingTable.getTableName(), where);

        jdbcTemplate.execute(deleteQueryBuilder.toSql());
    }
}
