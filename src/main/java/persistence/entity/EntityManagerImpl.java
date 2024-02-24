package persistence.entity;

import database.sql.Person;
import database.sql.dml.QueryBuilder;
import database.sql.util.EntityClassInspector;
import jdbc.JdbcTemplate;
import jdbc.RowMapper;

import java.util.Map;

public class EntityManagerImpl implements EntityManager {
    // XXX: 여기 코드를 최소한 줄여놓기
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
        EntityClassInspector inspector = new EntityClassInspector(entity);
        Map<String, Object> map = inspector.buildMap(entity);

        String query = QueryBuilder.getInstance().buildInsertQuery(entity.getClass(), map);
        jdbcTemplate.execute(query);
    }

    @Override
    public void remove(Object entity) {
        EntityClassInspector entityClassInspector = new EntityClassInspector(entity);
        long id = entityClassInspector.getPrimaryKeyValue(entity);

        QueryBuilder instance = QueryBuilder.getInstance();
        String query = instance.buildDeleteQuery(entity.getClass(), id);

        jdbcTemplate.execute(query);
    }
}
