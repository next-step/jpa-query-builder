package persistence.sql.dml.repository;

import jdbc.JdbcTemplate;
import persistence.sql.dml.exception.NotFoundIdException;
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
        DomainType domainType = entityMappingTable.getDomainTypeList()
                .stream()
                .filter(DomainType::isExistsId)
                .findFirst()
                .orElseThrow(NotFoundIdException::new);

        Map<DomainType, String> where = Map.of(domainType, id.toString());

        SelectQueryBuilder selectQueryBuilder = SelectQueryBuilder.of(entityMappingTable, where);
        return Optional.ofNullable(super.jdbcTemplate.queryForObject(selectQueryBuilder.toSql(), this::mapper));
    }
}
