package persistence.entity;

import database.sql.Person;
import database.sql.dml.QueryBuilder;
import database.sql.util.EntityClassInspector;
import jdbc.JdbcTemplate;
import jdbc.RowMapper;

import java.lang.reflect.Field;
import java.util.Map;

public class EntityManagerImpl implements EntityManager {
    private static final RowMapper<Person> PERSON_ROW_MAPPER = resultSet -> new Person(
            resultSet.getLong("id"),
            resultSet.getString("nick_name"),
            resultSet.getInt("old"),
            resultSet.getString("email"));

    private final JdbcTemplate jdbcTemplate;

    public EntityManagerImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public <T> T find(Class<T> clazz, Long Id) {
        String query = QueryBuilder.getInstance().buildSelectOneQuery(clazz, Id);
        return (T) jdbcTemplate.queryForObject(query, PERSON_ROW_MAPPER); // 여기도 하드코딩
    }

    @Override
    public void persist(Object entity) {
        Map<String, Object> map = ((Person) entity).toMap(); // 하드코딩

        EntityClassInspector inspector = getEntityClassInspector(entity.getClass());

//        map.put("nick_name", name);
//        map.put("old", age);
//        map.put("email", email);

        String query = QueryBuilder.getInstance().buildInsertQuery(entity.getClass(), map);
        jdbcTemplate.execute(query);
    }

    @Override
    public void remove(Object entity) {
        long id;
        Class<?> clazz = entity.getClass();
        EntityClassInspector inspector = getEntityClassInspector(clazz);
        Field field = inspector.getPrimaryKeyField();
        field.setAccessible(true);
        try {
            id = (long) field.get(entity); // 여기는 너무 entity 깊이 들어감
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }

        String query = QueryBuilder.getInstance().buildDeleteQuery(clazz, id);
        jdbcTemplate.execute(query);
    }

    private static EntityClassInspector getEntityClassInspector(Class<?> clazz) {
        return new EntityClassInspector(clazz);
    }
}
