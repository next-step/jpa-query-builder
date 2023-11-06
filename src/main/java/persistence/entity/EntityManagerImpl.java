package persistence.entity;

import java.util.List;
import jdbc.JdbcTemplate;
import jdbc.RowMapper;
import persistence.sql.dml.assembler.DataManipulationLanguageAssembler;

public class EntityManagerImpl implements EntityManager {
    private final RowMapper<?> rowMapper;
    private final DataManipulationLanguageAssembler dataManipulationLanguageAssembler;
    private final JdbcTemplate jdbcTemplate;

    public EntityManagerImpl(RowMapper<?> rowMapper, DataManipulationLanguageAssembler dataManipulationLanguageAssembler, JdbcTemplate jdbcTemplate) {
        this.rowMapper = rowMapper;
        this.dataManipulationLanguageAssembler = dataManipulationLanguageAssembler;
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public <T> T find(Class<T> clazz, Long Id) {
        String sql = dataManipulationLanguageAssembler.generateSelectWithWhere(clazz, Id);
        List<?> query = jdbcTemplate.query(sql, rowMapper);
        if (query.size() == 0) {
            return null;
        }
        if (query.size() > 2) {
            throw new IllegalStateException("Identifier is not unique");
        }
        return (T) query.get(0);
    }

    @Override
    public Object persist(Object entity) {
        String sql = dataManipulationLanguageAssembler.generateInsert(entity);
        jdbcTemplate.execute(sql);
        return entity;
    }

    @Override
    public void remove(Object entity) {
        String sql = dataManipulationLanguageAssembler.generateDeleteWithWhere(entity);
        jdbcTemplate.execute(sql);
    }
}
