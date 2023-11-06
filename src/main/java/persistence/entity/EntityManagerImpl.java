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
        this.rowMapper = rowMapper;;
        this.dataManipulationLanguageAssembler = dataManipulationLanguageAssembler;
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public <T> T find(Class<T> clazz, Long Id) {
        String sql = dataManipulationLanguageAssembler.generateSelectWithWhere(clazz, Id);
        List<?> query = jdbcTemplate.query(sql, rowMapper);
        return (T) query.get(0);
    }

    @Override
    public Object persist(Object entity) {
        return null;
    }

    @Override
    public void remove(Object entity) {

    }
}
