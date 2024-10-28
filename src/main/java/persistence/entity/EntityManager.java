package persistence.entity;

import jdbc.JdbcTemplate;
import persistence.dialect.H2Dialect;
import persistence.metadata.WhereCondition;
import persistence.sql.dml.builder.SelectQueryBuilder;
import persistence.sql.table.TableMetadataExtractor;

import java.sql.Connection;
import java.util.List;

public class EntityManager {
    private final JdbcTemplate jdbcTemplate;

    public EntityManager(Connection connection) {
        this.jdbcTemplate = new JdbcTemplate(connection);
    }

    public <T> T find(Class<T> clazz, Long id) {
        TableMetadataExtractor extractor = new TableMetadataExtractor(clazz);
        String sql = SelectQueryBuilder.builder(new H2Dialect())
                .select(extractor.columnNames())
                .from(extractor.tableName())
                .where(List.of(new WhereCondition("id", "=", id)))
                .build();
        return jdbcTemplate.queryForObject(sql, new EntityRowMapper<>(clazz));
    }


    public void persist(Object object) {

    }

    public void remove(Object object) {

    }
}
