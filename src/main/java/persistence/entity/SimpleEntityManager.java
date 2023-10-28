package persistence.entity;

import jdbc.JdbcTemplate;
import persistence.sql.ddl.EntityDefinitionBuilder;
import persistence.sql.ddl.dialect.Dialect;
import persistence.sql.dml.EntityManipulationBuilder;

public class SimpleEntityManager implements EntityManager {

    private final EntityDefinitionBuilder entityDefinitionBuilder;
    private final EntityManipulationBuilder entityManipulationBuilder;
    private final JdbcTemplate jdbcTemplate;

    public SimpleEntityManager(JdbcTemplate jdbcTemplate, Dialect dialect) {
        // TODO 여기서 패키지 입력 받아서 빌더들 쪽으로 넘기자.

        this.entityDefinitionBuilder = new EntityDefinitionBuilder();
        this.entityManipulationBuilder = new EntityManipulationBuilder();
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public <T> T find(Class<T> clazz, Long Id) {
        return null;
    }

    @Override
    public Object persist(Object entity) {
        return null;
    }

    @Override
    public void remove(Object entity) {

    }

}
