package persistence.entity;

import jakarta.persistence.Id;
import jdbc.JdbcTemplate;
import persistence.sql.ddl.EntityDefinitionBuilder;
import persistence.sql.ddl.EntityMetadata;
import persistence.sql.ddl.dialect.Dialect;
import persistence.sql.dml.EntityManipulationBuilder;

import java.lang.reflect.Field;
import java.util.Arrays;

public class SimpleEntityManager implements EntityManager {

    private final JdbcTemplate jdbcTemplate;
    private final Dialect dialect;

    public SimpleEntityManager(JdbcTemplate jdbcTemplate, Dialect dialect) {
        this.jdbcTemplate = jdbcTemplate;
        this.dialect = dialect;
    }

    @Override
    public <T> T find(Class<T> clazz, Long id) {
        EntityManipulationBuilder manipulationBuilder = createManipulationBuilder(clazz);
        return jdbcTemplate.queryForObject(manipulationBuilder.findById(id), resultSet -> {
            if (!resultSet.next()) {
                return null;
            }

            return manipulationBuilder.getEntity(resultSet);
        });
    }

    @Override
    public Object persist(Object entity) {
        return jdbcTemplate.executeAndReturnKey(createManipulationBuilder(entity.getClass()).insert(entity));
    }

    @Override
    public void remove(Object entity) {
        jdbcTemplate.execute(createManipulationBuilder(entity.getClass()).delete(getEntityId(entity)));
    }

    private <T> String getEntityId(T entity) {
        Field field = Arrays.stream(entity.getClass().getDeclaredFields())
                .filter(f -> f.isAnnotationPresent(Id.class))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("ID 필드가 없습니다."));

        field.setAccessible(true);

        try {
            Object value = field.get(entity);
            return String.valueOf(value);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    private <T>EntityManipulationBuilder createManipulationBuilder(Class<T> clazz) {
        return new EntityManipulationBuilder(EntityMetadata.of(clazz, dialect));
    }

    private <T> EntityDefinitionBuilder createDefinitionBuilder(Class<T> clazz) {
        return new EntityDefinitionBuilder(EntityMetadata.of(clazz, dialect));
    }

}
